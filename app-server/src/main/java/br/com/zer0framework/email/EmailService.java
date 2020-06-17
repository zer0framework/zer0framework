package br.com.zer0framework.email;

import java.net.URI;

import br.com.zer0framework.utils.security.SecurityUtil;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class EmailService {

    private static ExchangeService service;

    static {
        try {
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
            service.setUrl(new URI("https://webexchange.t-systems.com.br/EWS/Exchange.asmx"));
            /**
             * credenciais da maquina
             */
            ExchangeCredentials credentials = new WebCredentials("f0fp1103", "Rvp@624857gh");
            service.setCredentials(credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public EmailService(){}

    public static void sendEmail(String subject, String recipient, String token) {
        try {
            EmailMessage message = new EmailMessage(service);
            message.setSubject(subject);
            String urlToken = SecurityUtil.encryptor(token);
            
            message.setBody(new MessageBody("http://127.0.0.1:5500/reset/reset.html?key=" + urlToken));
            message.getToRecipients().add(recipient);
            message.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
