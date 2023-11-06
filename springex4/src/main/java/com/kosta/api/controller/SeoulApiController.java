package com.kosta.api.controller;

import com.kosta.api.dto.AnimalClinic;
import com.kosta.api.dto.PageInfo;
import com.kosta.api.service.SeoulApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class SeoulApiController {

    @Autowired
    private SeoulApiService seoulApiService;

    @GetMapping(value={"/clinic/{page}", "/clinic"})
    public String animalClinicList(@PathVariable(required = false) Integer page, Model model){
        if(page==null) page=1;

        try{
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurPage(page);
            List<AnimalClinic> acList = seoulApiService.animalClinicList(pageInfo);
            model.addAttribute("acList", acList);
            model.addAttribute("pageInfo", pageInfo);
            return "animalclinic";
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }
}
