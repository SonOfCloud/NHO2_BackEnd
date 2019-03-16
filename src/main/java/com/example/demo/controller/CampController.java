package com.example.demo.controller;

import com.example.demo.dto.CampRequestDto;
import com.example.demo.dto.CampResponseDto;
import com.example.demo.dto.Result;
import com.example.demo.entity.Camp;
import com.example.demo.mapper.CampMapper;
import com.example.demo.service.CampService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CampController {

    @Autowired
    private CampService campService;

    @RequestMapping(path = {"camps"})
    public Result<List<CampResponseDto>> getCampList() {
        List<CampResponseDto> campDTOs;
        Result<List<CampResponseDto>> result = new Result<>();
        try {
            List<Camp> camps = campService.getCampList();
            CampMapper campMapper = Mappers.getMapper(CampMapper.class);
            campDTOs = camps.stream().map(campMapper::convertCampResponseDto).collect(Collectors.toList());
            result.setData(campDTOs);
        } catch (Exception e) {
            result.setError(e.getMessage());
        }
        return result;
    }

    @RequestMapping(path = {"camp"}, method = RequestMethod.POST)
    public Result<Object> createCamp(@RequestBody CampRequestDto requestDto, HttpServletResponse response) {
        Result<Object> result = new Result<>();
        CampMapper campMapper = Mappers.getMapper(CampMapper.class);
        Camp camp = campMapper.convertRequestDtoToCamp(requestDto);
        try {
            campService.createCamp(camp);
        } catch (Exception e) {
            result.setError(e.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
        return result;
    }
}
