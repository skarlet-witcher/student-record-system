package cc.orangejuice.srs.programme.service.mapper;

import cc.orangejuice.srs.programme.domain.*;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProgrammeProp and its DTO ProgrammePropDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProgrammePropMapper extends EntityMapper<ProgrammePropDTO, ProgrammeProp> {



    default ProgrammeProp fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProgrammeProp programmeProp = new ProgrammeProp();
        programmeProp.setId(id);
        return programmeProp;
    }
}
