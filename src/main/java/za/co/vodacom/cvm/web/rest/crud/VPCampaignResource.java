package za.co.vodacom.cvm.web.rest.crud;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import za.co.vodacom.cvm.repository.VPCampaignRepository;
import za.co.vodacom.cvm.service.VPCampaignService;
import za.co.vodacom.cvm.service.dto.VPCampaignDTO;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPCampaign}.
 */
@RestController
@RequestMapping("/v2/api")
//@Profile("crud")
public class VPCampaignResource {

    private final Logger log = LoggerFactory.getLogger(VPCampaignResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpCampaign";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPCampaignService vPCampaignService;

    private final VPCampaignRepository vPCampaignRepository;

    public VPCampaignResource(VPCampaignService vPCampaignService, VPCampaignRepository vPCampaignRepository) {
        this.vPCampaignService = vPCampaignService;
        this.vPCampaignRepository = vPCampaignRepository;
    }

    /**
     * {@code POST  /vp-campaigns} : Create a new vPCampaign.
     *
     * @param vPCampaignDTO the vPCampaignDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPCampaignDTO, or with status {@code 400 (Bad Request)} if the vPCampaign has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-campaigns")
    public ResponseEntity<VPCampaignDTO> createVPCampaign(@Valid @RequestBody VPCampaignDTO vPCampaignDTO) throws URISyntaxException {
        log.debug("REST request to save VPCampaign : {}", vPCampaignDTO);
        if (vPCampaignDTO.getId() != null) {
            throw new BadRequestAlertException("A new vPCampaign cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPCampaignDTO result = vPCampaignService.save(vPCampaignDTO);
        return ResponseEntity
            .created(new URI("/api/vp-campaigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-campaigns/:id} : Updates an existing vPCampaign.
     *
     * @param id the id of the vPCampaignDTO to save.
     * @param vPCampaignDTO the vPCampaignDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaignDTO,
     * or with status {@code 400 (Bad Request)} if the vPCampaignDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaignDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-campaigns/{id}")
    public ResponseEntity<VPCampaignDTO> updateVPCampaign(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPCampaignDTO vPCampaignDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VPCampaign : {}, {}", id, vPCampaignDTO);
        if (vPCampaignDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPCampaignDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        VPCampaignDTO result = vPCampaignService.save(vPCampaignDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaignDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-campaigns/:id} : Partial updates given fields of an existing vPCampaign, field will ignore if it is null
     *
     * @param id the id of the vPCampaignDTO to save.
     * @param vPCampaignDTO the vPCampaignDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaignDTO,
     * or with status {@code 400 (Bad Request)} if the vPCampaignDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vPCampaignDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaignDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-campaigns/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VPCampaignDTO> partialUpdateVPCampaign(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPCampaignDTO vPCampaignDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPCampaign partially : {}, {}", id, vPCampaignDTO);
        if (vPCampaignDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPCampaignDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        Optional<VPCampaignDTO> result = vPCampaignService.partialUpdate(vPCampaignDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaignDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-campaigns} : get all the vPCampaigns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPCampaigns in body.
     */
    @GetMapping("/vp-campaigns")
    public List<VPCampaignDTO> getAllVPCampaigns() {
        log.debug("REST request to get all VPCampaigns");
        return vPCampaignService.findAll();
    }

    /**
     * {@code GET  /vp-campaigns/:id} : get the "id" vPCampaign.
     *
     * @param id the id of the vPCampaignDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPCampaignDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-campaigns/{id}")
    public ResponseEntity<VPCampaignDTO> getVPCampaign(@PathVariable Long id) {
        log.debug("REST request to get VPCampaign : {}", id);
        Optional<VPCampaignDTO> vPCampaignDTO = vPCampaignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPCampaignDTO);
    }

    /**
     * {@code DELETE  /vp-campaigns/:id} : delete the "id" vPCampaign.
     *
     * @param id the id of the vPCampaignDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-campaigns/{id}")
    public ResponseEntity<Void> deleteVPCampaign(@PathVariable Long id) {
        log.debug("REST request to delete VPCampaign : {}", id);
        vPCampaignService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
