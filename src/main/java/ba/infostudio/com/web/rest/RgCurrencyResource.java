package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.service.RgCurrencyService;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.service.dto.RgCurrencyDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RgCurrency.
 */
@RestController
@RequestMapping("/api")
public class RgCurrencyResource {

    private final Logger log = LoggerFactory.getLogger(RgCurrencyResource.class);

    private static final String ENTITY_NAME = "rgCurrency";

    private final RgCurrencyService rgCurrencyService;

    public RgCurrencyResource(RgCurrencyService rgCurrencyService) {
        this.rgCurrencyService = rgCurrencyService;
    }

    /**
     * POST  /rg-currencies : Create a new rgCurrency.
     *
     * @param rgCurrencyDTO the rgCurrencyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rgCurrencyDTO, or with status 400 (Bad Request) if the rgCurrency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rg-currencies")
    @Timed
    public ResponseEntity<RgCurrencyDTO> createRgCurrency(@RequestBody RgCurrencyDTO rgCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to save RgCurrency : {}", rgCurrencyDTO);
        if (rgCurrencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new rgCurrency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RgCurrencyDTO result = rgCurrencyService.save(rgCurrencyDTO);
        return ResponseEntity.created(new URI("/api/rg-currencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rg-currencies : Updates an existing rgCurrency.
     *
     * @param rgCurrencyDTO the rgCurrencyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rgCurrencyDTO,
     * or with status 400 (Bad Request) if the rgCurrencyDTO is not valid,
     * or with status 500 (Internal Server Error) if the rgCurrencyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rg-currencies")
    @Timed
    public ResponseEntity<RgCurrencyDTO> updateRgCurrency(@RequestBody RgCurrencyDTO rgCurrencyDTO) throws URISyntaxException {
        log.debug("REST request to update RgCurrency : {}", rgCurrencyDTO);
        if (rgCurrencyDTO.getId() == null) {
            return createRgCurrency(rgCurrencyDTO);
        }
        RgCurrencyDTO result = rgCurrencyService.save(rgCurrencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rgCurrencyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rg-currencies : get all the rgCurrencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rgCurrencies in body
     */
    @GetMapping("/rg-currencies")
    @Timed
    public List<RgCurrencyDTO> getAllRgCurrencies() {
        log.debug("REST request to get all RgCurrencies");
        return rgCurrencyService.findAll();
        }

    /**
     * GET  /rg-currencies/:id : get the "id" rgCurrency.
     *
     * @param id the id of the rgCurrencyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rgCurrencyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rg-currencies/{id}")
    @Timed
    public ResponseEntity<RgCurrencyDTO> getRgCurrency(@PathVariable Long id) {
        log.debug("REST request to get RgCurrency : {}", id);
        RgCurrencyDTO rgCurrencyDTO = rgCurrencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rgCurrencyDTO));
    }

    /**
     * DELETE  /rg-currencies/:id : delete the "id" rgCurrency.
     *
     * @param id the id of the rgCurrencyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rg-currencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteRgCurrency(@PathVariable Long id) {
        log.debug("REST request to delete RgCurrency : {}", id);
        rgCurrencyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
