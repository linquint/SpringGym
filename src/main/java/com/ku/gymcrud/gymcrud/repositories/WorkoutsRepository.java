package com.ku.gymcrud.gymcrud.repositories;

import com.ku.gymcrud.gymcrud.entities.Workouts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutsRepository extends JpaRepository<Workouts, Integer> {
}
