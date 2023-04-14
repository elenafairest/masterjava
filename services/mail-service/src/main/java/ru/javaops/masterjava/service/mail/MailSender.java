package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import ru.javaops.masterjava.ExceptionType;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.service.mail.persist.MailCase;
import ru.javaops.masterjava.service.mail.persist.MailCaseDao;
import ru.javaops.web.WebStateException;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;
import java.util.Set;

@Slf4j
public class MailSender {
    private static final MailCaseDao MAIL_CASE_DAO = DBIProvider.getDao(MailCaseDao.class);

    static MailResult sendTo(Addressee to, String subject, String body,
                             @XmlMimeType("application/octet-stream") DataHandler dataHandler, String fileName) throws WebStateException {
        val state = sendToGroup(ImmutableSet.of(to), ImmutableSet.of(), subject, body, dataHandler, fileName);
        return new MailResult(to.getEmail(), state);
    }

    static String sendToGroup(Set<Addressee> to, Set<Addressee> cc, String subject, String body,
                              @XmlMimeType("application/octet-stream") DataHandler dataHandler, String fileName) throws WebStateException {
        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        String state = MailResult.OK;
        try {
            //val email = MailConfig.createHtmlEmail();
            val email = MailConfig.createMultiPartEmail();
            email.setSubject(subject);
            //email.setHtmlMsg(body);
            email.setMsg(body);
            EmailAttachment attachment = new EmailAttachment();
            /*attachment.setPath("C:/Users/Acer/Documents/DB.txt");
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("My attachment");
            attachment.setName("Attachment");
            email.attach(attachment);*/

            email.attach(dataHandler.getDataSource(), fileName, "My attachment");

            for (Addressee addressee : to) {
                email.addTo(addressee.getEmail(), addressee.getName());
            }
            for (Addressee addressee : cc) {
                email.addCc(addressee.getEmail(), addressee.getName());
            }

            //  https://yandex.ru/blog/company/66296
            email.setHeaders(ImmutableMap.of("List-Unsubscribe", "<mailto:masterjava@javaops.ru?subject=Unsubscribe&body=Unsubscribe>"));

            email.send();
        } catch (EmailException e) {
            log.error(e.getMessage(), e);
            state = e.getMessage();
        }
        try {
            MAIL_CASE_DAO.insert(MailCase.of(to, cc, subject, state));
        } catch (Exception e) {
            log.error("Mail history saving exception", e);
            throw new WebStateException(e, ExceptionType.DATA_BASE);
        }
        log.info("Sent with state: " + state);
        return state;
    }
}
