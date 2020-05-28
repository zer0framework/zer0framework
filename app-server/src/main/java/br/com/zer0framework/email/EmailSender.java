package br.com.zer0framework.email;

import br.com.zer0framework.utils.security.SecurityUtil;
import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

//TODO precisa de um smtp server
public class EmailSender {

	private static final String SMTP_SERVER = "smtp server ";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String EMAIL_FROM = "From@gmail.com";
	private static final String EMAIL_SUBJECT = "PasswordReset";

	public static void sendResetPasswordEmail(String emailTo) {

		Properties prop = System.getProperties();
		prop.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(prop, null);
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(EMAIL_FROM));

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false));

			msg.setSubject(EMAIL_SUBJECT);

			String key = SecurityUtil.generateResetPasswordKey(emailTo);

			// HTML email
			msg.setDataHandler(new DataHandler(
					new HTMLDataSource("<a href=\"http://localhost:8080/paginaComformularioDeResetDeSenha?key=" + key
							+ "/\">Go to reset password page</a>")));

			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

			// connect
			t.connect(SMTP_SERVER, USERNAME, PASSWORD);

			// send
			t.sendMessage(msg, msg.getAllRecipients());

			t.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	static class HTMLDataSource implements DataSource {

		private String html;

		public HTMLDataSource(String htmlString) {
			html = htmlString;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			if (html == null)
				throw new IOException("html message is null!");
			return new ByteArrayInputStream(html.getBytes());
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			throw new IOException("This DataHandler cannot write HTML");
		}

		@Override
		public String getContentType() {
			return "text/html";
		}

		@Override
		public String getName() {
			return "HTMLDataSource";
		}
	}
}