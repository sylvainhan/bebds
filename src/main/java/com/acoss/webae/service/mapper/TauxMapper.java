package com.acoss.webae.service.mapper;

import com.acoss.webae.domain.*;
import com.acoss.webae.service.dto.TauxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Taux and its DTO TauxDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TauxMapper extends EntityMapper<TauxDTO, Taux> {



    default Taux fromId(Long id) {
        if (id == null) {
            return null;
        }
        Taux taux = new Taux();
        taux.setId(id);
        return taux;
    }
}
