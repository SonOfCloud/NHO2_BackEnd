package com.example.demo;

import com.example.demo.entity.Camp;
import com.example.demo.jpa.CampRepository;
import com.example.demo.service.CampService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CampServiceTest {

    @Mock
    private CampRepository campRepository;

    private CampService campService;


    @Test
    public void ShouldReturnAllCampsWhenGetCampList() {
        campService = new CampService(campRepository);
        List<Camp> camps = new LinkedList<>();
        camps.add(Camp.builder().build());
        camps.add(Camp.builder().build());
        when(campRepository.findAll(new Sort(Sort.Direction.DESC, "updatedAt"))).thenReturn(camps);
        List<Camp> resultCamps = campService.getCampList();
        Assert.assertEquals(2, resultCamps.size());
    }

    @Test
    public void TestAdd(){
        campService = new CampService(campRepository);
        campService.createCamp(Camp.builder().build());
        verify(campRepository, times(1)).save(any());
    }
}
