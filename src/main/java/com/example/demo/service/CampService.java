package com.example.demo.service;

import com.example.demo.entity.Camp;
import com.example.demo.jpa.CampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CampService {

    private CampRepository campRepository;

    @Autowired
    public CampService(CampRepository campRepository) {
        this.campRepository = campRepository;
    }

    public List<Camp> getCampList(){
        Sort sort = new Sort(Sort.Direction.DESC, "updatedAt", "id");
        List<Camp> camps = campRepository.findAll(sort);
        return camps;
    }

    public Camp createCamp(Camp camp) {
        return campRepository.save(camp);
    }

    public void deleteCamp(long id) {
        campRepository.deleteById(id);
    }
}
