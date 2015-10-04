package com.petcare.repository;

import com.petcare.domain.Lost;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Lost entity.
 */
public interface LostRepository extends JpaRepository<Lost,Long> {

    @Query("select lost from Lost lost where lost.have.login = ?#{principal.username}")
    List<Lost> findByHaveIsCurrentUser();

}
