package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao implements AbstractDao {

    public Project insert(Project project) {
        if (project.isNew()) {
            int id = insertGeneratedId(project);
            project.setId(id);
        } else {
            insertWitId(project);
        }
        return project;
    }

    @SqlUpdate("INSERT INTO project (name, description) VALUES (:name, :description) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Project project);

    @SqlUpdate("INSERT INTO users (id, full_name, email, flag) VALUES (:id, :fullName, :email, CAST(:flag AS USER_FLAG)) ")
    abstract void insertWitId(@BindBean Project project);

    @SqlUpdate("TRUNCATE project CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM project ORDER BY name LIMIT :it")
    public abstract List<Project> getWithLimit(@Bind int limit);
}
