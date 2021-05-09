package database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "link_topics", schema = "public", catalog = "Subscriptions")
@IdClass(LinkTopicsEntityPK.class)
public class LinkTopicsEntity {
    private int topicId;
    private int linkId;

    public LinkTopicsEntity(int topicId, int linkId) {
        this.topicId = topicId;
        this.linkId = linkId;
    }

    public LinkTopicsEntity() {
    }

    @Id
    @Column(name = "topicid")
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Id
    @Column(name = "linkid")
    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkTopicsEntity that = (LinkTopicsEntity) o;
        return topicId == that.topicId && linkId == that.linkId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, linkId);
    }

    @Override
    public String toString() {
        return "LinkTopicsEntity{" +
                "topicId=" + topicId +
                ", linkId=" + linkId +
                '}';
    }
}
