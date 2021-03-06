package com.lavgorush.graduation_project.voting.repository;

import com.lavgorush.graduation_project.voting.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(int id);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId and v.dateOfVote = :date")
    Optional<Vote> getByUserIdAndDate(int userId, LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.id = :id AND v.user.id = :userId")
    Optional<Vote> getByIdAndUserId(int id,int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId")
    List<Vote> getAllByUserId(int userId);
}
