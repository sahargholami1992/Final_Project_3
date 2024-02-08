package com.example.final_project_3.mapper;

import com.example.final_project_3.entity.Offer;
import com.example.final_project_3.dto.OfferResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface OffersMapper {
    OffersMapper INSTANCE = Mappers.getMapper(OffersMapper.class);
    Collection<OfferResponse> offersToDto(Collection<Offer> offers);
}
