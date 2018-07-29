package ba.infostudio.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import ba.infostudio.com.domain.CoFiles;

import ba.infostudio.com.repository.CoFilesRepository;
import ba.infostudio.com.web.rest.errors.BadRequestAlertException;
import ba.infostudio.com.web.rest.util.HeaderUtil;
import ba.infostudio.com.service.dto.CoFilesDTO;
import ba.infostudio.com.service.mapper.CoFilesMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CoFiles.
 */
@RestController
@RequestMapping("/api")
public class CoFilesResource {

    private final Logger log = LoggerFactory.getLogger(CoFilesResource.class);

    private static final String ENTITY_NAME = "coFiles";

    private final CoFilesRepository coFilesRepository;

    private final CoFilesMapper coFilesMapper;

    public CoFilesResource(CoFilesRepository coFilesRepository, CoFilesMapper coFilesMapper) {
        this.coFilesRepository = coFilesRepository;
        this.coFilesMapper = coFilesMapper;
    }

    /**
     * POST  /co-files : Create a new coFiles.
     *
     * @param coFilesDTO the coFilesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coFilesDTO, or with status 400 (Bad Request) if the coFiles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/co-files")
    @Timed
    public ResponseEntity<CoFilesDTO> createCoFiles(@Valid @RequestBody CoFilesDTO coFilesDTO) throws URISyntaxException {
        log.debug("REST request to save CoFiles : {}", coFilesDTO);
        if (coFilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new coFiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoFiles coFiles = coFilesMapper.toEntity(coFilesDTO);
        coFiles = coFilesRepository.save(coFiles);
        CoFilesDTO result = coFilesMapper.toDto(coFiles);
        return ResponseEntity.created(new URI("/api/co-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /co-files : Updates an existing coFiles.
     *
     * @param coFilesDTO the coFilesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coFilesDTO,
     * or with status 400 (Bad Request) if the coFilesDTO is not valid,
     * or with status 500 (Internal Server Error) if the coFilesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/co-files")
    @Timed
    public ResponseEntity<CoFilesDTO> updateCoFiles(@Valid @RequestBody CoFilesDTO coFilesDTO) throws URISyntaxException {
        log.debug("REST request to update CoFiles : {}", coFilesDTO);
        if (coFilesDTO.getId() == null) {
            return createCoFiles(coFilesDTO);
        }
        CoFiles coFiles = coFilesMapper.toEntity(coFilesDTO);
        coFiles = coFilesRepository.save(coFiles);
        CoFilesDTO result = coFilesMapper.toDto(coFiles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coFilesDTO.getId().toString()))
            .body(result);
    }


    /**
     * GET  /co-announcements/valid : get valid the coAnnouncements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coAnnouncements in body
     */
    @GetMapping("/co-files/valid")
    @Timed
    public ResponseEntity<List<CoFilesDTO>> getAllValidCoFiles() {
        log.debug("REST request to get a page of CoAnnouncements");
        List<CoFiles> files = coFilesRepository.findAllByValidToGreaterThanEqualAndValidFromLessThanEqual(LocalDate.now(), LocalDate.now());
        List<CoFilesDTO> filesDTO = coFilesMapper.toDto(files);
        return new ResponseEntity<>(filesDTO, null, HttpStatus.OK);
    }

    /**
     * GET  /co-files : get all the coFiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of coFiles in body
     */
    @GetMapping("/co-files")
    @Timed
    public List<CoFilesDTO> getAllCoFiles() {
        log.debug("REST request to get all CoFiles");
        List<CoFiles> coFiles = coFilesRepository.findAll();
        return coFilesMapper.toDto(coFiles);
        }

    /**
     * GET  /co-files/:id : get the "id" coFiles.
     *
     * @param id the id of the coFilesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coFilesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/co-files/{id}")
    @Timed
    public ResponseEntity<CoFilesDTO> getCoFiles(@PathVariable Long id) {
        log.debug("REST request to get CoFiles : {}", id);
        CoFiles coFiles = coFilesRepository.findOne(id);
        CoFilesDTO coFilesDTO = coFilesMapper.toDto(coFiles);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coFilesDTO));
    }

    /**
     * DELETE  /co-files/:id : delete the "id" coFiles.
     *
     * @param id the id of the coFilesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/co-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoFiles(@PathVariable Long id) {
        log.debug("REST request to delete CoFiles : {}", id);
        coFilesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
