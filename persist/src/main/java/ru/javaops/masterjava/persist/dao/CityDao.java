package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class CityDao implements AbstractDao {
    public City insert(City city) {
        if (city.isNew()) {
            int id = insertGeneratedId(city);
            city.setId(id);
        } else {
            insertWitId(city);
        }
        return city;
    }

    @SqlUpdate("INSERT INTO city (name, city_id) VALUES (:name, :cityId) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean City city);

    @SqlUpdate("INSERT INTO city (id, name, city_id) VALUES (:id, :name, :cityId) ON CONFLICT DO NOTHING")
    abstract void insertWitId(@BindBean City city);

    @SqlQuery("SELECT * FROM city ORDER BY city_id LIMIT :it")
    public abstract List<City> getWithLimit(@Bind int limit);

    @SqlUpdate("TRUNCATE city CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SElECT * from city WHERE city_id = :cityId")
    public abstract City getById(@Bind("cityId") String cityId);
}
