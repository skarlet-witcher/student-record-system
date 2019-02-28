package cc.orangejuice.srs.programme.service.mapper;

import cc.orangejuice.srs.programme.domain.*;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDictDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProgrammePropDict and its DTO ProgrammePropDictDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProgrammePropDictMapper extends EntityMapper<ProgrammePropDictDTO, ProgrammePropDict> {



    default ProgrammePropDict fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProgrammePropDict programmePropDict = new ProgrammePropDict();
        programmePropDict.setId(id);
        return programmePropDict;
    }


}
