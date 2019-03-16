package com.example.demo.mapper;

import com.example.demo.dto.CampRequestDto;
import com.example.demo.dto.CampResponseDto;
import com.example.demo.entity.Camp;
import org.mapstruct.Mapper;

@Mapper
public interface CampMapper {

    CampResponseDto convertCampToCampResponseDto(Camp camp);

    Camp convertRequestDtoToCamp(CampRequestDto dto);
}
