package com.lavgorush.graduation_project.voting.repository;

import com.lavgorush.graduation_project.voting.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(int id);
}
