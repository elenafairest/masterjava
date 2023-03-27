package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity {
    private @NonNull String name;
    @Column("city_id")
    private @NonNull String cityId;

    public City(Integer id, String name, String cityId) {
        this(name, cityId);
        this.id = id;
    }
}
