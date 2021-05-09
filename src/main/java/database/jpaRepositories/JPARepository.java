package database.jpaRepositories;

import database.repositories.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class JPARepository<T> implements Repository<T> {
    EntityManagerFactory factory;
    EntityManager entityManager;

    protected void open() {
        if (factory == null && entityManager == null) {
            factory = Persistence.createEntityManagerFactory("default");
            entityManager = factory.createEntityManager();
        }
    }
    protected void close() {
        entityManager.close();
        factory.close();

        entityManager = null;
        factory = null;
    }

    @Override
    public void create(T entity) {
        open();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        close();
    }
}
