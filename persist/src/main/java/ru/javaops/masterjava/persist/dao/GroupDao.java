package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao implements AbstractDao {
    public Group insert(Group group) {
        if (group.isNew()) {
            int id = insertGeneratedId(group);
            group.setId(id);
        } else {
            insertWitId(group);
        }
        return group;
    }

    @SqlUpdate("INSERT INTO groups (name, type, project_id) VALUES (:name, CAST(:type AS GROUP_TYPE), :projectId) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Group group);

    @SqlUpdate("INSERT INTO groups (id, name, type, project_id) VALUES (:id, :name, CAST(:type AS GROUP_TYPE), :projectId) ")
    abstract void insertWitId(@BindBean Group group);

    @SqlUpdate("TRUNCATE groups CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM groups ORDER BY name LIMIT :it")
    public abstract List<Group> getWithLimit(@Bind int limit);

}
