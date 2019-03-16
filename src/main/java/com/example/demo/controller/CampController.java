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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@Api(tags = "CampController", description = "add camp or query camps")
public class CampController {

    @Autowired
    private CampService campService;

    private CampMapper campMapper = Mappers.getMapper(CampMapper.class);

    @RequestMapping(path = {"camps"}, method = RequestMethod.GET)
    @ApiOperation(value="getCamps")
    public Result<List<CampResponseDto>> getCampList() {
        List<CampResponseDto> campDTOs;
        Result<List<CampResponseDto>> result = new Result<>();
        try {
            List<Camp> camps = campService.getCampList();
            campDTOs = camps.stream().map(campMapper::convertCampResponseDto).collect(Collectors.toList());
            result.setData(campDTOs);
        } catch (Exception e) {
            result.setError(e.getMessage());
        }
        return result;
    }

    @RequestMapping(path = {"camp"}, method = RequestMethod.POST)
    @ApiOperation(value="addCamp")
    public Result<Object> createCamp(@RequestBody CampRequestDto requestDto, HttpServletResponse response) {
        Result<Object> result = new Result<>();
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
