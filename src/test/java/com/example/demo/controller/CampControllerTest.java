package com.example.demo.controller;

import com.example.demo.dto.CampResponseDto;
import com.example.demo.dto.Result;
import com.example.demo.entity.Camp;
import com.example.demo.service.CampService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CampControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CampService campService;

    @InjectMocks
    private CampController campController;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(campController).build();
    }

    @Test
    public void shouldGetAllCampsWhenCallGetCampList() throws Exception{
        List<Camp> camps = new LinkedList<>();
        camps.add(Camp.builder().id(2L).title("test2").description("desc2").build());
        camps.add(Camp.builder().id(1L).title("test1").description("desc1").build());
        when(campService.getCampList()).thenReturn(camps);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/camps"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Result<List<CampResponseDto>> campsResult = mapper.readValue(responseContent, new TypeReference<Result<List<CampResponseDto>>>() {});
        Assert.assertEquals(2, campsResult.getData().size());
        Assert.assertEquals(2, campsResult.getData().get(0).getId().longValue());
        Assert.assertEquals("test2", campsResult.getData().get(0).getTitle());
        Assert.assertEquals("desc2", campsResult.getData().get(0).getDescription());
        Assert.assertEquals(1, campsResult.getData().get(1).getId().longValue());
        Assert.assertEquals("test1", campsResult.getData().get(1).getTitle());
        Assert.assertEquals("desc1", campsResult.getData().get(1).getDescription());
        Assert.assertEquals(null, campsResult.getError());
    }
}