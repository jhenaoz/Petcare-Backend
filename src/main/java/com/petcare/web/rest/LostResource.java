package com.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.petcare.domain.Lost;
import com.petcare.repository.LostRepository;
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
 * REST controller for managing Lost.
 */
@RestController
@RequestMapping("/api")
public class LostResource {

    private final Logger log = LoggerFactory.getLogger(LostResource.class);

    @Inject
    private LostRepository lostRepository;

    /**
     * POST  /losts -> Create a new lost.
     */
    @RequestMapping(value = "/losts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lost> create(@Valid @RequestBody Lost lost) throws URISyntaxException {
        log.debug("REST request to save Lost : {}", lost);
        if (lost.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new lost cannot already have an ID").body(null);
        }
        Lost result = lostRepository.save(lost);
        return ResponseEntity.created(new URI("/api/losts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("lost", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /losts -> Updates an existing lost.
     */
    @RequestMapping(value = "/losts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lost> update(@Valid @RequestBody Lost lost) throws URISyntaxException {
        log.debug("REST request to update Lost : {}", lost);
        if (lost.getId() == null) {
            return create(lost);
        }
        Lost result = lostRepository.save(lost);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("lost", lost.getId().toString()))
                .body(result);
    }

    /**
     * GET  /losts -> get all the losts.
     */
    @RequestMapping(value = "/losts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Lost> getAll() {
        log.debug("REST request to get all Losts");
        return lostRepository.findAll();
    }

    /**
     * GET  /losts/:id -> get the "id" lost.
     */
    @RequestMapping(value = "/losts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Lost> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Lost : {}", id);
        Lost lost = lostRepository.findOne(id);
        if (lost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lost, HttpStatus.OK);
    }

    /**
     * DELETE  /losts/:id -> delete the "id" lost.
     */
    @RequestMapping(value = "/losts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Lost : {}", id);
        lostRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lost", id.toString())).build();
    }
}
