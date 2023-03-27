package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;

import java.util.List;

public class GroupTestData {
    public static Group topJava06;
    public static Group topJava07;
    public static Group topJava08;
    public static Group masterJava01;
    public static List<Group> groups;

    public static void init() {
        ProjectTestData.init();
        ProjectTestData.setUp();
        topJava06 = new Group("topjava06", GroupType.FINISHED, ProjectTestData.topJava.getId());
        topJava07 = new Group("topjava07", GroupType.FINISHED, ProjectTestData.topJava.getId());
        topJava08 = new Group("topjava08", GroupType.CURRENT, ProjectTestData.topJava.getId());
        masterJava01 = new Group("masterjava01", GroupType.CURRENT, ProjectTestData.masterJava.getId());
        groups = ImmutableList.of(masterJava01, topJava06, topJava07, topJava08);
    }

    public static void setUp() {
        GroupDao dao = DBIProvider.getDao(GroupDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> groups.forEach(dao::insert));
    }
}
