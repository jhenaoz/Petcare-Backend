package com.petcare.repository;

import com.petcare.domain.Sponsor;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sponsor entity.
 */
public interface SponsorRepository extends JpaRepository<Sponsor,Long> {

    @Query("select sponsor from Sponsor sponsor where sponsor.have.login = ?#{principal.username}")
    List<Sponsor> findByHaveIsCurrentUser();

}
