package delivery.repository;

import delivery.model.Courier;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCourierRepository implements CourierRepository {
    private final Map<Long, Courier> couriers = new LinkedHashMap<>();

    @Override
    public void save(Courier courier) {
        couriers.put(courier.getId(), courier);
    }

    @Override
    public Optional<Courier> findById(long id) {
        return Optional.ofNullable(couriers.get(id));
    }

    @Override
    public List<Courier> findAll() {
        return List.copyOf(couriers.values());
    }

    @Override
    public List<Courier> findAvailable() {
        return couriers.values().stream().filter(Courier::isAvailable).toList();
    }
}