package com.example.demo.controller;

import com.example.demo.dto.CampRequestDto;
import com.example.demo.dto.CampResponseDto;
import com.example.demo.dto.Result;
import com.example.demo.entity.Camp;
import com.example.demo.mapper.CampMapper;
import com.example.demo.service.CampService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@Api(tags = "CampController", description = "camp operation")
public class CampController {

    @Autowired
    private CampService campService;

    private CampMapper campMapper = Mappers.getMapper(CampMapper.class);


    @RequestMapping(path = {"camps"}, method = RequestMethod.GET)
    @ApiOperation(value="getCamps")
    public Result<List<CampResponseDto>> getCampList() {
        List<CampResponseDto> campDTOs;
        Result<List<CampResponseDto>> result = new Result<>();
        List<Camp> camps = campService.getCampList();
        campDTOs = camps.stream().map(campMapper::convertCampToCampResponseDto).collect(Collectors.toList());
        result.setData(campDTOs);
        return result;
    }

    @RequestMapping(path = {"camp"}, method = RequestMethod.POST)
    @ApiOperation(value="addCamp")
    public Result<CampResponseDto> createCamp(@RequestBody @Valid CampRequestDto requestDto, HttpServletResponse response) {
        Result<CampResponseDto> result = new Result<>();
        Camp camp = campMapper.convertRequestDtoToCamp(requestDto);
        Camp addResultCamp = campService.createCamp(camp);
        CampResponseDto campResponseDto = campMapper.convertCampToCampResponseDto(addResultCamp);
        result.setData(campResponseDto);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return result;
    }

    @RequestMapping(path = {"camp/{id}"}, method = RequestMethod.DELETE)
    @ApiOperation(value="deleteCamp")
    public Result<String> deleteCamp(@PathVariable long id){
        Result<String> result = new Result<>();
        campService.deleteCamp(id);
        result.setData("SUCCESS");
        return result;
    }
}
