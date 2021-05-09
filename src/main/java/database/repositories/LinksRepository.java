package database.repositories;

import database.entities.LinksEntity;

import java.util.List;

public interface LinksRepository extends Repository<LinksEntity> {
    LinksEntity findById(int id);
    LinksEntity findByUrl(String url);
    List<LinksEntity> findByTopic(String topicName);
}
