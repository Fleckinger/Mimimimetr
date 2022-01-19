package ru.inovus.mimimimetr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.entity.contender.Contender;

import java.util.List;

@Repository
public interface ContenderRepository extends JpaRepository<Contender, Long> {

    @Query("SELECT c FROM Contender c WHERE c.id = (" +
            "SELECT vt.contender.id FROM Vote vt WHERE vt.user.id <> :#{#user.getId()}) "
            )
    Page<Contender> getContenderWithoutVoteFromUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT c FROM Contender c ORDER BY c.votes.size DESC")
    List<Contender> findTopByVotes(Pageable pageable);

}
