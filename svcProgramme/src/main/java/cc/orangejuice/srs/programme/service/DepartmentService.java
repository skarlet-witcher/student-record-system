package cc.orangejuice.srs.programme.service;

import cc.orangejuice.srs.programme.domain.Department;
import cc.orangejuice.srs.programme.repository.DepartmentRepository;
import cc.orangejuice.srs.programme.service.dto.DepartmentDTO;
import cc.orangejuice.srs.programme.service.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Department.
 */
@Service
@Transactional
public class DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Save a department.
     *
     * @param departmentDTO the entity to save
     * @return the persisted entity
     */
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        log.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    /**
     * Get all the departments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        return departmentRepository.findAll(pageable)
            .map(departmentMapper::toDto);
    }


    /**
     * Get one department by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id)
            .map(departmentMapper::toDto);
    }

    /**
     * Delete the department by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);        departmentRepository.deleteById(id);
    }
}
