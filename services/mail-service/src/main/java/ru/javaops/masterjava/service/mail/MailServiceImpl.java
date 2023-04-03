package ru.javaops.masterjava.service.mail;

import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.DBITestProvider;
import ru.javaops.masterjava.service.mail.dao.MailRecordDao;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "ru.javaops.masterjava.service.mail.MailService")
public class MailServiceImpl implements MailService {
    private static MailRecordDao dao;

    static {
        DBITestProvider.initDBI();
        dao = DBIProvider.getDao(MailRecordDao.class);
    }

    public void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        MailSender.sendMail(to, cc, subject, body, dao);
    }
}