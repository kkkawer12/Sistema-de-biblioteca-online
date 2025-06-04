package util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import model.Livro;
import model.Usuario;
import java.io.File;

public class EmailUtil {
    private static final String EMAIL_FROM = "biblioteca@sistema.com";
    private static final String EMAIL_PASSWORD = "sua_senha_aqui";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public static void enviarLivroPorEmail(Usuario usuario, Livro livro) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario.getEmail()));
        message.setSubject("Seu livro: " + livro.getTitulo());

        // Criando o corpo do e-mail
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Olá " + usuario.getNome() + ",\n\n" +
                              "Segue em anexo o livro '" + livro.getTitulo() + "' que você solicitou.\n\n" +
                              "Lembre-se que você tem acesso ao livro até a data de devolução.\n\n" +
                              "Boa leitura!\n\n" +
                              "Atenciosamente,\n" +
                              "Equipe da Biblioteca");

        // Anexando o arquivo do livro
        BodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(new FileDataSource(new File(livro.getArquivoPath()))));
        attachmentPart.setFileName(livro.getTitulo() + ".pdf");

        // Combinando as partes
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

        // Enviando o e-mail
        Transport.send(message);
    }
} 