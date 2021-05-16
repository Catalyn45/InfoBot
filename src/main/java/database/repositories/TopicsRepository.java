package database.repositories;

import database.entities.TopicsEntity;

import java.util.List;

public interface TopicsRepository extends Repository<TopicsEntity> {
    TopicsEntity findById(int id);
    TopicsEntity findByName(String name);
    List<TopicsEntity> all();
}
