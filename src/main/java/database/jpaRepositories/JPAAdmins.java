package database.jpaRepositories;
import database.entities.AdminsEntity;
import database.repositories.AdminsRepository;

import javax.persistence.NoResultException;

public class JPAAdmins extends JPARepository<AdminsEntity> implements AdminsRepository {
    @Override
    public AdminsEntity findByName(String name) {
        open();
        AdminsEntity admin;
        try {
            admin = entityManager.createNamedQuery("admins.findByName", AdminsEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e){
            admin = null;
        }

        close();

        return admin;
    }
}
