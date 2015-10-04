package com.petcare.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.petcare.domain.Sponsor;
import com.petcare.repository.SponsorRepository;
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
 * REST controller for managing Sponsor.
 */
@RestController
@RequestMapping("/api")
public class SponsorResource {

    private final Logger log = LoggerFactory.getLogger(SponsorResource.class);

    @Inject
    private SponsorRepository sponsorRepository;

    /**
     * POST  /sponsors -> Create a new sponsor.
     */
    @RequestMapping(value = "/sponsors",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sponsor> create(@Valid @RequestBody Sponsor sponsor) throws URISyntaxException {
        log.debug("REST request to save Sponsor : {}", sponsor);
        if (sponsor.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sponsor cannot already have an ID").body(null);
        }
        Sponsor result = sponsorRepository.save(sponsor);
        return ResponseEntity.created(new URI("/api/sponsors/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("sponsor", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /sponsors -> Updates an existing sponsor.
     */
    @RequestMapping(value = "/sponsors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sponsor> update(@Valid @RequestBody Sponsor sponsor) throws URISyntaxException {
        log.debug("REST request to update Sponsor : {}", sponsor);
        if (sponsor.getId() == null) {
            return create(sponsor);
        }
        Sponsor result = sponsorRepository.save(sponsor);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("sponsor", sponsor.getId().toString()))
                .body(result);
    }

    /**
     * GET  /sponsors -> get all the sponsors.
     */
    @RequestMapping(value = "/sponsors",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Sponsor> getAll() {
        log.debug("REST request to get all Sponsors");
        return sponsorRepository.findAll();
    }

    /**
     * GET  /sponsors/:id -> get the "id" sponsor.
     */
    @RequestMapping(value = "/sponsors/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Sponsor> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Sponsor : {}", id);
        Sponsor sponsor = sponsorRepository.findOne(id);
        if (sponsor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sponsor, HttpStatus.OK);
    }

    /**
     * DELETE  /sponsors/:id -> delete the "id" sponsor.
     */
    @RequestMapping(value = "/sponsors/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Sponsor : {}", id);
        sponsorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sponsor", id.toString())).build();
    }
}
