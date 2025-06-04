package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConfig {
    private static BasicDataSource dataSource;
    
    static {
        try {
            Properties props = new Properties();
            InputStream is = DatabaseConfig.class.getClassLoader()
                    .getResourceAsStream("database.properties");
            
            if (is != null) {
                props.load(is);
                
                dataSource = new BasicDataSource();
                dataSource.setDriverClassName(props.getProperty("db.driver"));
                dataSource.setUrl(props.getProperty("db.url"));
                dataSource.setUsername(props.getProperty("db.username"));
                dataSource.setPassword(props.getProperty("db.password"));
                
                // Configurações do pool de conexões
                dataSource.setInitialSize(Integer.parseInt(props.getProperty("db.pool.initialSize")));
                dataSource.setMinIdle(Integer.parseInt(props.getProperty("db.pool.minIdle")));
                dataSource.setMaxIdle(Integer.parseInt(props.getProperty("db.pool.maxIdle")));
                dataSource.setMaxTotal(Integer.parseInt(props.getProperty("db.pool.maxActive")));
                dataSource.setMaxWaitMillis(Long.parseLong(props.getProperty("db.pool.maxWait")));
                
            } else {
                throw new RuntimeException("Arquivo database.properties não encontrado!");
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar configurações do banco de dados", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    private DatabaseConfig() {
        // Construtor privado para evitar instanciação
    }
} 