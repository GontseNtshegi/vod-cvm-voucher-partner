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
import za.co.vodacom.cvm.repository.VPCampaignVouchersRepository;
import za.co.vodacom.cvm.service.VPCampaignVouchersService;
import za.co.vodacom.cvm.service.dto.VPCampaignVouchersDTO;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPCampaignVouchers}.
 */
@RestController
@RequestMapping("/v2/api")
//@Profile("crud")
public class VPCampaignVouchersResource {

    private final Logger log = LoggerFactory.getLogger(VPCampaignVouchersResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpCampaignVouchers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPCampaignVouchersService vPCampaignVouchersService;

    private final VPCampaignVouchersRepository vPCampaignVouchersRepository;

    public VPCampaignVouchersResource(
        VPCampaignVouchersService vPCampaignVouchersService,
        VPCampaignVouchersRepository vPCampaignVouchersRepository
    ) {
        this.vPCampaignVouchersService = vPCampaignVouchersService;
        this.vPCampaignVouchersRepository = vPCampaignVouchersRepository;
    }

    /**
     * {@code POST  /vp-campaign-vouchers} : Create a new vPCampaignVouchers.
     *
     * @param vPCampaignVouchersDTO the vPCampaignVouchersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPCampaignVouchersDTO, or with status {@code 400 (Bad Request)} if the vPCampaignVouchers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-campaign-vouchers")
    public ResponseEntity<VPCampaignVouchersDTO> createVPCampaignVouchers(@Valid @RequestBody VPCampaignVouchersDTO vPCampaignVouchersDTO)
        throws URISyntaxException {
        log.debug("REST request to save VPCampaignVouchers : {}", vPCampaignVouchersDTO);
        if (vPCampaignVouchersDTO.getId() != null) {
            throw new BadRequestAlertException("A new vPCampaignVouchers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPCampaignVouchersDTO result = vPCampaignVouchersService.save(vPCampaignVouchersDTO);
        return ResponseEntity
            .created(new URI("/api/vp-campaign-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-campaign-vouchers/:id} : Updates an existing vPCampaignVouchers.
     *
     * @param id the id of the vPCampaignVouchersDTO to save.
     * @param vPCampaignVouchersDTO the vPCampaignVouchersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaignVouchersDTO,
     * or with status {@code 400 (Bad Request)} if the vPCampaignVouchersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaignVouchersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-campaign-vouchers/{id}")
    public ResponseEntity<VPCampaignVouchersDTO> updateVPCampaignVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPCampaignVouchersDTO vPCampaignVouchersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VPCampaignVouchers : {}, {}", id, vPCampaignVouchersDTO);
        if (vPCampaignVouchersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPCampaignVouchersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        VPCampaignVouchersDTO result = vPCampaignVouchersService.save(vPCampaignVouchersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaignVouchersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-campaign-vouchers/:id} : Partial updates given fields of an existing vPCampaignVouchers, field will ignore if it is null
     *
     * @param id the id of the vPCampaignVouchersDTO to save.
     * @param vPCampaignVouchersDTO the vPCampaignVouchersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPCampaignVouchersDTO,
     * or with status {@code 400 (Bad Request)} if the vPCampaignVouchersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vPCampaignVouchersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPCampaignVouchersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-campaign-vouchers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VPCampaignVouchersDTO> partialUpdateVPCampaignVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPCampaignVouchersDTO vPCampaignVouchersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPCampaignVouchers partially : {}, {}", id, vPCampaignVouchersDTO);
        if (vPCampaignVouchersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPCampaignVouchersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPCampaignVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        Optional<VPCampaignVouchersDTO> result = vPCampaignVouchersService.partialUpdate(vPCampaignVouchersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPCampaignVouchersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-campaign-vouchers} : get all the vPCampaignVouchers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPCampaignVouchers in body.
     */
    @GetMapping("/vp-campaign-vouchers")
    public List<VPCampaignVouchersDTO> getAllVPCampaignVouchers() {
        log.debug("REST request to get all VPCampaignVouchers");
        return vPCampaignVouchersService.findAll();
    }

    /**
     * {@code GET  /vp-campaign-vouchers/:id} : get the "id" vPCampaignVouchers.
     *
     * @param id the id of the vPCampaignVouchersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPCampaignVouchersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-campaign-vouchers/{id}")
    public ResponseEntity<VPCampaignVouchersDTO> getVPCampaignVouchers(@PathVariable Long id) {
        log.debug("REST request to get VPCampaignVouchers : {}", id);
        Optional<VPCampaignVouchersDTO> vPCampaignVouchersDTO = vPCampaignVouchersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPCampaignVouchersDTO);
    }

    /**
     * {@code DELETE  /vp-campaign-vouchers/:id} : delete the "id" vPCampaignVouchers.
     *
     * @param id the id of the vPCampaignVouchersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-campaign-vouchers/{id}")
    public ResponseEntity<Void> deleteVPCampaignVouchers(@PathVariable Long id) {
        log.debug("REST request to delete VPCampaignVouchers : {}", id);
        vPCampaignVouchersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
