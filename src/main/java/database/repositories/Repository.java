package database.repositories;

public  interface Repository<T> {
    public abstract void create(T entity);
}
