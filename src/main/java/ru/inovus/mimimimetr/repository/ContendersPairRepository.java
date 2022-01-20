package ru.inovus.mimimimetr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.inovus.mimimimetr.entity.contender.ContendersPair;

import java.util.Optional;

@Repository
public interface ContendersPairRepository extends JpaRepository<ContendersPair, Long> {

    @Query("SELECT pair FROM ContendersPair pair " +
            "WHERE (pair.firstContender.id = :firstContenderId " +
            "AND pair.secondContender.id = :secondContenderId " +
            "AND pair.vote.user.id = :userId) " +
            "OR (pair.firstContender.id = :secondContenderId) " +
            "AND pair.secondContender.id = :firstContenderId " +
            "AND pair.vote.user.id = :userId")
    Optional<ContendersPair> findPairVotedByUser(Long firstContenderId, Long secondContenderId, Long userId);
}
