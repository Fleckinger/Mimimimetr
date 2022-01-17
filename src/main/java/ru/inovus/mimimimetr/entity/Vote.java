package ru.inovus.mimimimetr.entity;

import ru.inovus.mimimimetr.entity.contender.Contender;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vote_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "contender_id")
    private Contender contender;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Vote() {
    }

    public Vote(Integer id, Contender contender, User user) {
        this.id = id;
        this.contender = contender;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contender getContender() {
        return contender;
    }

    public void setContender(Contender contender) {
        this.contender = contender;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vote)) {
            return false;
        }
        Vote vote = (Vote) other;
        return Objects.equals(id, vote.id)
                && Objects.equals(contender, vote.contender)
                && Objects.equals(user, vote.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contender, user);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", contender=" + contender +
                ", user=" + user +
                '}';
    }
}
