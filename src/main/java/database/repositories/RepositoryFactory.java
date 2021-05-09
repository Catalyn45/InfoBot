package database.repositories;

public interface RepositoryFactory {
    LinksRepository createLinksRepository();
    TopicsRepository createTopicsRepository();
    LinkTopicsRepository createLinkTopicsRepository();
}
