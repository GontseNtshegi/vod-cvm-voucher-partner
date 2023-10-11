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
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPVouchersRepository;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link za.co.vodacom.cvm.domain.VPVouchers}.
 */
@RestController
@RequestMapping("/v2/api")
//@Profile("crud")
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
     * @param vPVouchers the vPVouchers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vPVouchers, or with status {@code 400 (Bad Request)} if the vPVouchers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vp-vouchers")
    public ResponseEntity<VPVouchers> createVPVouchers(@Valid @RequestBody VPVouchers vPVouchers) throws URISyntaxException {
        log.debug("REST request to save VPVouchers : {}", vPVouchers);
        if (vPVouchers.getId() != null) {
            throw new BadRequestAlertException("A new vPVouchers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VPVouchers result = vPVouchersService.save(vPVouchers);
        return ResponseEntity
            .created(new URI("/api/vp-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vp-vouchers/:id} : Updates an existing vPVouchers.
     *
     * @param id the id of the vPVouchers to save.
     * @param vPVouchers the vPVouchers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPVouchers,
     * or with status {@code 400 (Bad Request)} if the vPVouchers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vPVouchers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vp-vouchers/{id}")
    public ResponseEntity<VPVouchers> updateVPVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VPVouchers vPVouchers
    ) throws URISyntaxException {
        log.debug("REST request to update VPVouchers : {}, {}", id, vPVouchers);
        if (vPVouchers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /* if (!Objects.equals(id, vPVouchers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        VPVouchers result = vPVouchersService.save(vPVouchers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPVouchers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vp-vouchers/:id} : Partial updates given fields of an existing vPVouchers, field will ignore if it is null
     *
     * @param id the id of the vPVouchers to save.
     * @param vPVouchers the vPVouchers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vPVouchers,
     * or with status {@code 400 (Bad Request)} if the vPVouchers is not valid,
     * or with status {@code 404 (Not Found)} if the vPVouchers is not found,
     * or with status {@code 500 (Internal Server Error)} if the vPVouchers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vp-vouchers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<VPVouchers> partialUpdateVPVouchers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VPVouchers vPVouchers
    ) throws URISyntaxException {
        log.debug("REST request to partial update VPVouchers partially : {}, {}", id, vPVouchers);
        if (vPVouchers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        /*if (!Objects.equals(id, vPVouchers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vPVouchersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }*/

        Optional<VPVouchers> result = vPVouchersService.partialUpdate(vPVouchers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vPVouchers.getId().toString())
        );
    }

    /**
     * {@code GET  /vp-vouchers} : get all the vPVouchers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vPVouchers in body.
     */
    @GetMapping("/vp-vouchers")
    public List<VPVouchers> getAllVPVouchers() {
        log.debug("REST request to get all VPVouchers");
        return vPVouchersService.findAll();
    }

    /**
     * {@code GET  /vp-vouchers/:id} : get the "id" vPVouchers.
     *
     * @param id the id of the vPVouchers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vPVouchers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vp-vouchers/{id}")
    public ResponseEntity<VPVouchers> getVPVouchers(@PathVariable Long id) {
        log.debug("REST request to get VPVouchers : {}", id);
        Optional<VPVouchers> vPVouchers = vPVouchersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vPVouchers);
    }

    /**
     * {@code DELETE  /vp-vouchers/:id} : delete the "id" vPVouchers.
     *
     * @param id the id of the vPVouchers to delete.
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
