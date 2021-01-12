package com.lavgorush.graduation_project.votingsystem.repository;

import com.lavgorush.graduation_project.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
