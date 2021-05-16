package database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admins", schema = "public", catalog = "Subscriptions")
@NamedQueries({
        @NamedQuery(name = "admins.findByName",
                query = "SELECT admins FROM AdminsEntity admins WHERE admins.user=:name")
})
public class AdminsEntity {
    private Long id;
    private String user;
    private String password;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user", nullable = false, length = 30)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminsEntity that = (AdminsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, id, password);
    }
}
