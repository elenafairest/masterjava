package ru.javaops.masterjava.service.mail.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;

import ru.javaops.masterjava.persist.dao.AbstractDao;
import ru.javaops.masterjava.service.mail.model.*;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class MailRecordDao implements AbstractDao {
    @SqlUpdate("TRUNCATE email CASCADE ")
    public abstract void clean();

    @SqlUpdate("INSERT INTO email (to_list, cc_list, subject, body, status, error_message, sent_time)  " +
            "VALUES (:toList, :ccList, :subject, :body, :status, :errorMessage, :sentTime) ")
    @GetGeneratedKeys
    public abstract int insertGeneratedId(@BindBean MailRecord mailRecord);
}
