package com.petcare.repository;

import com.petcare.domain.Adoption;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Adoption entity.
 */
public interface AdoptionRepository extends JpaRepository<Adoption,Long> {

    @Query("select adoption from Adoption adoption where adoption.have.login = ?#{principal.username}")
    List<Adoption> findByHaveIsCurrentUser();

}
