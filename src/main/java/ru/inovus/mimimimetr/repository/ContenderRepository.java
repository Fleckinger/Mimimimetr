package ru.inovus.mimimimetr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.inovus.mimimimetr.entity.contender.Contender;

import java.util.List;

@Repository
public interface ContenderRepository extends JpaRepository<Contender, Long> {

    @Query("SELECT c FROM Contender c ORDER BY c.votes.size DESC")
    List<Contender> findTopByVotes(Pageable pageable);

    @Query("SELECT cont FROM Contender cont ORDER BY RAND()")
    List<Contender> findAllInRandomOrder();
}
