package database.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class LinkTopicsEntityPK implements Serializable {
    private int topicId;
    private int linkId;

    @Column(name = "topicid")
    @Id
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Column(name = "linkid")
    @Id
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
        LinkTopicsEntityPK that = (LinkTopicsEntityPK) o;
        return topicId == that.topicId && linkId == that.linkId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, linkId);
    }
}
