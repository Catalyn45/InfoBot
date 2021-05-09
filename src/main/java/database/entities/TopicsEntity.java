package database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "topics", schema = "public", catalog = "Subscriptions")
@NamedQueries({
        @NamedQuery(name = "topics.findById",
                query = "SELECT topics FROM TopicsEntity topics WHERE topics.id=:id"),
        @NamedQuery(name = "topics.findByName",
                query = "SELECT topics FROM TopicsEntity topics WHERE topics.name=:name"),
})
public class TopicsEntity {
    private int id;
    private String name;

    public TopicsEntity(String name) {
        this.name = name;
    }

    public TopicsEntity() { }

    @Id
    @SequenceGenerator(name = "topics_seq_gen", sequenceName = "topics_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "topics_seq_gen")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicsEntity that = (TopicsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TopicsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
