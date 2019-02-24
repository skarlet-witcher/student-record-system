package cc.orangejuice.srs.programme.service.mapper;

import cc.orangejuice.srs.programme.domain.*;
import cc.orangejuice.srs.programme.service.dto.ProgrammeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Programme and its DTO ProgrammeDTO.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface ProgrammeMapper extends EntityMapper<ProgrammeDTO, Programme> {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    ProgrammeDTO toDto(Programme programme);

    @Mapping(source = "departmentId", target = "department")
    Programme toEntity(ProgrammeDTO programmeDTO);

    default Programme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Programme programme = new Programme();
        programme.setId(id);
        return programme;
    }
}
