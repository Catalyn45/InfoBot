package database.jpaRepositories;

import database.entities.LinksEntity;
import database.repositories.LinksRepository;

import java.util.List;

public class JPALinks extends JPARepository<LinksEntity> implements LinksRepository {
    @Override
    public LinksEntity findById(int id) {
        open();
        LinksEntity result = entityManager.createNamedQuery("links.findById", LinksEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        close();

        return result;
    }

    @Override
    public LinksEntity findByUrl(String url) {
        open();
        LinksEntity result = entityManager.createNamedQuery("links.findByUrl", LinksEntity.class)
                .setParameter("url", url)
                .getSingleResult();
        close();

        return result;
    }

    @Override
    public List<LinksEntity> findByTopic(String topicName) {
        open();
        List<LinksEntity> results = entityManager.createNamedQuery("links.findByTopic", LinksEntity.class)
                .setParameter("name", topicName)
                .getResultList();
        close();

        return results;
    }
}
