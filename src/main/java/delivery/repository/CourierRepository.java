package delivery.repository;

import delivery.model.Courier;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    void save(Courier courier);

    Optional<Courier> findById(long id);

    List<Courier> findAll();

    List<Courier> findAvailable();
}