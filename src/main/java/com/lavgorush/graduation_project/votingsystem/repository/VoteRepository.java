package com.lavgorush.graduation_project.votingsystem.repository;

import com.lavgorush.graduation_project.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
