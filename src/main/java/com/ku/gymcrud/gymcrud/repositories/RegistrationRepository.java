package com.ku.gymcrud.gymcrud.repositories;

import com.ku.gymcrud.gymcrud.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
}
