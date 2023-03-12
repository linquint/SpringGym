package com.ku.gymcrud.gymcrud.repositories;

import com.ku.gymcrud.gymcrud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Client, Integer> {
}
