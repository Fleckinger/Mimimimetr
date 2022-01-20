package ru.inovus.mimimimetr.entity.contender;

import ru.inovus.mimimimetr.entity.Vote;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "contender")
public class Contender {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contender_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "contender", fetch = FetchType.EAGER)
    private List<Vote> votes;

    @Column()
    private ContenderType type;

    public Contender() {
    }

    public Contender(Long id, String name, String image, List<Vote> votes, ContenderType type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.votes = votes;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public ContenderType getType() {
        return type;
    }

    public void setType(ContenderType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Contender)) {
            return false;
        }
        Contender contender = (Contender) other;
        return Objects.equals(id, contender.id)
                && Objects.equals(name, contender.name)
                && Objects.equals(image, contender.image)
                && Objects.equals(votes, contender.votes)
                && type == contender.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, votes, type);
    }

    @Override
    public String toString() {
        return "Contender{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                '}';
    }
}
