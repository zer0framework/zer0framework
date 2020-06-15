package br.com.zer0framework.email;

import br.com.zer0framework.utils.security.SecurityUtil;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

import java.net.URI;

public class EmailService {

    private static ExchangeService service;

    static {
        try {
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
            service.setUrl(new URI("https://webexchange.t-systems.com.br/EWS/Exchange.asmx"));
            /**
             * credenciais da maquina
             */
            ExchangeCredentials credentials = new WebCredentials("", "");
            service.setCredentials(credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public EmailService(){}

    public static void sendEmail(String subject, String recipient) {
        try {
            EmailMessage message = new EmailMessage(service);
            message.setSubject(subject);
            String key = SecurityUtil.generateResetPasswordKey(recipient);

            message.setBody(new MessageBody("<a href=\"http://localhost:8080/paginaComformularioDeResetDeSenha?key="+key+"\">Go to reset password page</a>"));
            message.getToRecipients().add(recipient);
            message.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
