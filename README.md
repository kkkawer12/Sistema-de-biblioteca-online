# Biblioteca Online

Sistema de biblioteca online gratuita com foco em livros de saúde alimentar, psicologia e bem-estar.

## Funcionalidades

- Cadastro e login de usuários usando CPF e RG
- Pesquisa e visualização do catálogo de livros
- Empréstimo de livros online com envio por e-mail
- Controle automático do tempo de leitura
- Sistema de avaliação com notas e comentários
- Exibição de feedbacks de outros usuários

## Requisitos

- Java 11 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior

## Configuração

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/biblioteca-online.git
cd biblioteca-online
```

2. Configure o banco de dados:
- Crie um banco de dados MySQL
- Execute o script SQL em `database/schema.sql`

3. Configure as credenciais do banco de dados:
- Abra o arquivo `src/main/resources/database.properties`
- Atualize as configurações de conexão com o banco de dados

4. Configure o envio de e-mails:
- Abra o arquivo `src/main/java/util/EmailUtil.java`
- Atualize as configurações de SMTP e credenciais de e-mail

5. Compile o projeto:
```bash
mvn clean install
```

6. Execute o sistema:
```bash
java -jar target/biblioteca-online-1.0-SNAPSHOT.jar
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   ├── dao/         # Classes de acesso ao banco de dados
│   │   ├── model/       # Classes de modelo
│   │   ├── util/        # Classes utilitárias
│   │   └── view/        # Interface gráfica
│   └── resources/       # Arquivos de configuração
└── test/               # Testes unitários
```

## Banco de Dados

O sistema utiliza as seguintes tabelas:

- `usuarios`: Armazena informações dos usuários
- `livros`: Catálogo de livros disponíveis
- `emprestimos`: Registro de empréstimos
- `avaliacoes`: Avaliações e comentários dos usuários

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Crie um Pull Request

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes. 