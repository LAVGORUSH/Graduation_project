package com.lavgorush.graduation_project.votingsystem.repository;

import com.lavgorush.graduation_project.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {

}
