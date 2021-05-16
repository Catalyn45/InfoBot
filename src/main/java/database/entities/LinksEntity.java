package database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "links", schema = "public", catalog = "Subscriptions")
@NamedQueries({
        @NamedQuery(name = "links.findById",
                query = "SELECT links FROM LinksEntity links WHERE links.id=:id"),
        @NamedQuery(name = "links.findByUrl",
                query = "SELECT links FROM LinksEntity links WHERE links.url=:url"),
        @NamedQuery(name = "links.findByTopic",
                query = "SELECT links FROM LinksEntity links" +
                " JOIN LinkTopicsEntity linkTopics ON links.id = linkTopics.linkId" +
                " JOIN TopicsEntity topics ON linkTopics.topicId = topics.id" +
                " WHERE topics.name =: name"),
})
public class LinksEntity {
    private int id;
    private String url;

    public LinksEntity(String url) {
        this.url = url;
    }

    public LinksEntity() { }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinksEntity that = (LinksEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }

    @Override
    public String toString() {
        return "LinksEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
