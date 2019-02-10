package cc.orangejuice.srs.gateway.service.mapper;

import cc.orangejuice.srs.gateway.domain.*;
import cc.orangejuice.srs.gateway.service.dto.ModuleResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ModuleResult and its DTO ModuleResultDTO.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface ModuleResultMapper extends EntityMapper<ModuleResultDTO, ModuleResult> {

    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.name", target = "moduleName")
    ModuleResultDTO toDto(ModuleResult moduleResult);

    @Mapping(source = "moduleId", target = "module")
    ModuleResult toEntity(ModuleResultDTO moduleResultDTO);

    default ModuleResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModuleResult moduleResult = new ModuleResult();
        moduleResult.setId(id);
        return moduleResult;
    }
}
