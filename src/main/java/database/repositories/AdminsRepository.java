package database.repositories;

import database.entities.AdminsEntity;

public interface AdminsRepository extends Repository<AdminsEntity> {
    AdminsEntity findByName(String name);
}
