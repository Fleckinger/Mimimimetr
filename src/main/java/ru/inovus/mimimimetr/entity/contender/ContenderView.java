package ru.inovus.mimimimetr.entity.contender;

import ru.inovus.mimimimetr.entity.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ContenderView {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "contender_id")
    @ManyToOne
    private Contender contender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contender getContender() {
        return contender;
    }

    public void setContender(Contender contender) {
        this.contender = contender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContenderView view = (ContenderView) o;
        return Objects.equals(id, view.id) && Objects.equals(user, view.user) && Objects.equals(contender, view.contender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, contender);
    }

    @Override
    public String toString() {
        return "ContenderView{" +
                "id=" + id +
                ", user=" + user +
                ", contender=" + contender +
                '}';
    }
}
