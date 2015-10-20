package com.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.petcare.domain.Adoption;
import com.petcare.repository.AdoptionRepository;
import com.petcare.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Adoption.
 */
@RestController
@RequestMapping("/api")
public class AdoptionResource {

    private final Logger log = LoggerFactory.getLogger(AdoptionResource.class);

    @Inject
    private AdoptionRepository adoptionRepository;

    /**
     * POST  /adoptions -> Create a new adoption.
     */
    @RequestMapping(value = "/adoptions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adoption> create(@Valid @RequestBody Adoption adoption) throws URISyntaxException {
        log.debug("REST request to save Adoption : {}", adoption);
        if (adoption.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new adoption cannot already have an ID").body(null);
        }
        Adoption result = adoptionRepository.save(adoption);
        return ResponseEntity.created(new URI("/api/adoptions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("adoption", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /adoptions -> Updates an existing adoption.
     */
    @RequestMapping(value = "/adoptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adoption> update(@Valid @RequestBody Adoption adoption) throws URISyntaxException {
        log.debug("REST request to update Adoption : {}", adoption);
        if (adoption.getId() == null) {
            return create(adoption);
        }
        Adoption result = adoptionRepository.save(adoption);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("adoption", adoption.getId().toString()))
                .body(result);
    }

    /**
     * GET  /adoptions -> get all the adoptions.
     */
    @RequestMapping(value = "/adoptions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Adoption> getAll() {
        log.debug("REST request to get all Adoptions");
        return adoptionRepository.findAll();
    }

    /**
     * GET  /adoptions/:id -> get the "id" of the user.
     */
    @RequestMapping(value = "/adoptions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Adoption> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Adoption : {}", id);
        Adoption adoption = adoptionRepository.findOne(id);
        if (adoption == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(adoption, HttpStatus.OK);
    }

    /**
     * DELETE  /adoptions/:id -> delete the "id" adoption.
     */
    @RequestMapping(value = "/adoptions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Adoption : {}", id);
        adoptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("adoption", id.toString())).build();
    }
}
