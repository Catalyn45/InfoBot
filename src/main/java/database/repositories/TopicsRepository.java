package database.repositories;

import database.entities.TopicsEntity;

public interface TopicsRepository extends Repository<TopicsEntity> {
    TopicsEntity findById(int id);
    TopicsEntity findByName(String name);
}
