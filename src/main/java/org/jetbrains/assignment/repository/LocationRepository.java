package org.jetbrains.assignment.repository;

import org.jetbrains.assignment.model.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import java.util.Optional;

public interface LocationRepository extends ListCrudRepository<Location, Long> {
    @Query(nativeQuery = true,
            value = "select * from location order by id desc limit 1"
    )
    Optional<Location> findLastLocation();
}
