package database.jpaRepositories;

import database.repositories.LinkTopicsRepository;
import database.repositories.LinksRepository;
import database.repositories.RepositoryFactory;
import database.repositories.TopicsRepository;

public class JPAFactory implements RepositoryFactory {
    private final LinksRepository linksRepository;
    private final TopicsRepository topicsRepository;
    private final LinkTopicsRepository linkTopicsRepository;

    private static JPAFactory instance = null;

    public static RepositoryFactory getInstance() {
        if(instance == null)
            instance = new JPAFactory();

        return instance;
    }

    private JPAFactory() {
        linksRepository = new JPALinks();
        topicsRepository = new JPATopics();
        linkTopicsRepository = new JPALinkTopics();
    }

    @Override
    public LinksRepository createLinksRepository() {
        return linksRepository;
    }

    @Override
    public TopicsRepository createTopicsRepository() {
        return topicsRepository;
    }

    @Override
    public LinkTopicsRepository createLinkTopicsRepository() {
        return linkTopicsRepository;
    }
}
