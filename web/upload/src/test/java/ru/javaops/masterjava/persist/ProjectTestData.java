package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

public class ProjectTestData {
    public static Project topJava;
    public static Project masterJava;

    public static List<Project> projects;

    public static void init() {
        topJava = new Project("topjava", "Topjava");
        masterJava = new Project("masterjava", "Masterjava");
        projects = ImmutableList.of(masterJava, topJava);
    }

    public static void setUp() {
        ProjectDao dao = DBIProvider.getDao(ProjectDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> projects.forEach(dao::insert));
    }
}
