package cc.orangejuice.srs.module.service.mapper;

import cc.orangejuice.srs.module.domain.Module;
import cc.orangejuice.srs.module.service.dto.ModuleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Module and its DTO ModuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ModuleMapper extends EntityMapper<ModuleDTO, Module> {



    default Module fromId(Long id) {
        if (id == null) {
            return null;
        }
        Module module = new Module();
        module.setId(id);
        return module;
    }
}
