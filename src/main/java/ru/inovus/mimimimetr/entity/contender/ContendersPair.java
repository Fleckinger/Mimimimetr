package ru.inovus.mimimimetr.entity.contender;

import ru.inovus.mimimimetr.entity.Vote;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ContendersPair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Contender firstContender;
    @OneToOne
    private Contender secondContender;
    @OneToOne
    private Vote vote;

    public ContendersPair() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contender getFirstContender() {
        return firstContender;
    }

    public void setFirstContender(Contender firstContender) {
        this.firstContender = firstContender;
    }

    public Contender getSecondContender() {
        return secondContender;
    }

    public void setSecondContender(Contender secondContender) {
        this.secondContender = secondContender;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContendersPair that = (ContendersPair) o;
        return Objects.equals(id, that.id) && Objects.equals(firstContender, that.firstContender) && Objects.equals(secondContender, that.secondContender) && Objects.equals(vote, that.vote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstContender, secondContender, vote);
    }
}
