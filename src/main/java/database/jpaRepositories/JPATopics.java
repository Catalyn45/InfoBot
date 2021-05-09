package database.jpaRepositories;

import database.entities.LinksEntity;
import database.entities.TopicsEntity;
import database.repositories.TopicsRepository;

import javax.persistence.EntityManager;

public class JPATopics extends JPARepository<TopicsEntity> implements TopicsRepository {
    @Override
    public TopicsEntity findById(int id) {
        open();
        TopicsEntity result = entityManager.createNamedQuery("topics.findById", TopicsEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        close();

        return result;
    }

    @Override
    public TopicsEntity findByName(String name) {
        open();
        TopicsEntity result = entityManager.createNamedQuery("topics.findByName", TopicsEntity.class)
                .setParameter("name", name)
                .getSingleResult();
        close();

        return result;
    }
}
