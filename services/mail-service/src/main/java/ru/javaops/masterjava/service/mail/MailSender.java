package ru.javaops.masterjava.service.mail;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import ru.javaops.masterjava.config.Configs;
import ru.javaops.masterjava.service.mail.dao.MailRecordDao;
import ru.javaops.masterjava.service.mail.model.MailRecord;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MailSender {
    static void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body, MailRecordDao dao) {
        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        List<String> toEmails = getEmails(to);
        List<String> ccEmails = getEmails(cc);
        MailRecord mailRecord = new MailRecord(null, toEmails.toString(), ccEmails.toString(), subject, body, null, null, null);
        Config mail = Configs.getConfig("mail.conf","mail");
        try {
            Email email = new SimpleEmail();
            email.setHostName(mail.getString("host"));
            email.setSmtpPort(mail.getInt("port"));
            email.setAuthenticator(new DefaultAuthenticator(mail.getString("username"), mail.getString("password")));
            email.setSSLOnConnect(mail.getBoolean("useSSL"));
            email.setStartTLSEnabled(mail.getBoolean("useTLS"));
            email.setFrom(mail.getString("username"), mail.getString("fromName"));
            email.setDebug(mail.getBoolean("debug"));
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(toEmails.toArray(new String[0]));
            email.addCc(ccEmails.toArray(new String[0]));
            email.send();
            mailRecord.setStatus("Success");
        }
        catch (EmailException e) {
            log.error(e.getMessage());
            mailRecord.setStatus("Error");
            mailRecord.setErrorMessage(e.getMessage());
        }
        mailRecord.setSentTime(LocalDateTime.now());
        dao.insertGeneratedId(mailRecord);
    }

    private static List<String> getEmails(List<Addressee> addressees) {
        if (addressees != null && !addressees.isEmpty()) {
            return addressees.stream()
                    .map((Addressee::getEmail))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
