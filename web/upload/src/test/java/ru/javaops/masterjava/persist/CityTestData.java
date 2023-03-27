package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

public class CityTestData {
    public static City piter;
    public static City moscow;
    public static City kiev;
    public static City minsk;
    public static List<City> cities;

    public static void init() {
        piter = new City("Санкт-Петербург", "spb");
        moscow = new City("Москва", "mow");
        kiev = new City("Киев","kiv");
        minsk = new City("Минск", "mnsk");
        cities = ImmutableList.of(kiev, minsk, moscow, piter);
    }

    public static void setUp() {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> cities.forEach(dao::insert));
    }
}
