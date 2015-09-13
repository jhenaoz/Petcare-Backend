package com.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petcare.domain.Adoption;

public interface AdoptionRepository extends JpaRepository<Adoption, Long>{
	
}
