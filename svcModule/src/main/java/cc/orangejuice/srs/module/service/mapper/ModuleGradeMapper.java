package cc.orangejuice.srs.module.service.mapper;

import cc.orangejuice.srs.module.domain.*;
import cc.orangejuice.srs.module.service.dto.ModuleGradeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModuleGrade and its DTO ModuleGradeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModuleGradeMapper extends EntityMapper<ModuleGradeDTO, ModuleGrade> {



    default ModuleGrade fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModuleGrade moduleGrade = new ModuleGrade();
        moduleGrade.setId(id);
        return moduleGrade;
    }
}
