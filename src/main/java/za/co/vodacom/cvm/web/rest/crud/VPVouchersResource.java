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
import za.co.vodacom.cvm.repository.VPVouchersRepository;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.service.dto.VPVouchersDTO;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPVouchers}.
 */
@RestController
@RequestMapping("/api")
public class VPVouchersResource {

    private final Logger log = LoggerFactory.getLogger(VPVouchersResource.class);

    private static final String ENTITY_NAME = "voucherpartnerVpVouchers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VPVouchersService vPVouchersService;

    private final VPVouchersRepository vPVouchersRepository;

    public VPVouchersResource(VPVouchersService vPVouchersService, VPVouchersRepository vPVouchersRepository) {
        this.vPVouchersService = vPVouchersService;
        this.vPVouchersRepository = vPVouchersRepository;
    }

    /**
     * {@code POST  /vp-vouchers} : Create a new vPVouchers.
     *
     * @param vPVouchersDTO the vPVouchersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPVouchersDTO, or with status {@code 400 (Bad Request)} if the vPVouchers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-vouchers")
    public ResponseEntity<VPVouchersDTO> createVPVouchers(@Valid @RequestBody VPVouchersDTO vPVouchersDTO) throws URISyntaxException {
        log.debug("REST request to save VPVouchers : {}", vPVouchersDTO);
        if (vPVouchersDTO.getId() != null) {
            throw new BadRequestAlertException("A new vPVouchers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPVouchersDTO result = vPVouchersService.save(vPVouchersDTO);
        return ResponseEntity
            .created(new URI("/api/vp-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-vouchers/:id} : Updates an existing vPVouchers.
     *
     * @param id the id of the vPVouchersDTO to save.
     * @param vPVouchersDTO the vPVouchersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPVouchersDTO,
     * or with status {@code 400 (Bad Request)} if the vPVouchersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPVouchersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-vouchers/{id}")
    public ResponseEntity<VPVouchersDTO> updateVPVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPVouchersDTO vPVouchersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VPVouchers : {}, {}", id, vPVouchersDTO);
        if (vPVouchersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPVouchersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        VPVouchersDTO result = vPVouchersService.save(vPVouchersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPVouchersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-vouchers/:id} : Partial updates given fields of an existing vPVouchers, field will ignore if it is null
     *
     * @param id the id of the vPVouchersDTO to save.
     * @param vPVouchersDTO the vPVouchersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPVouchersDTO,
     * or with status {@code 400 (Bad Request)} if the vPVouchersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vPVouchersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPVouchersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-vouchers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VPVouchersDTO> partialUpdateVPVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPVouchersDTO vPVouchersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPVouchers partially : {}, {}", id, vPVouchersDTO);
        if (vPVouchersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPVouchersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        Optional<VPVouchersDTO> result = vPVouchersService.partialUpdate(vPVouchersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPVouchersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-vouchers} : get all the vPVouchers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPVouchers in body.
     */
    @GetMapping("/vp-vouchers")
    public List<VPVouchersDTO> getAllVPVouchers() {
        log.debug("REST request to get all VPVouchers");
        return vPVouchersService.findAll();
    }

    /**
     * {@code GET  /vp-vouchers/:id} : get the "id" vPVouchers.
     *
     * @param id the id of the vPVouchersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPVouchersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-vouchers/{id}")
    public ResponseEntity<VPVouchersDTO> getVPVouchers(@PathVariable Long id) {
        log.debug("REST request to get VPVouchers : {}", id);
        Optional<VPVouchersDTO> vPVouchersDTO = vPVouchersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPVouchersDTO);
    }

    /**
     * {@code DELETE  /vp-vouchers/:id} : delete the "id" vPVouchers.
     *
     * @param id the id of the vPVouchersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vp-vouchers/{id}")
    public ResponseEntity<Void> deleteVPVouchers(@PathVariable Long id) {
        log.debug("REST request to delete VPVouchers : {}", id);
        vPVouchersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
