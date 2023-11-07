package com.kosta.api2.controller;

import com.kosta.api2.service.BuisnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BuisnessController {

    @Autowired
    private BuisnessService buisnessService;

    @GetMapping("/main")
    public String main(){
        return "result";
    }

    @PostMapping("/main")
    public String request(@RequestParam String buisnessNum, Model model){

        try{
            String result = buisnessService.buisnessExist(buisnessNum);
            System.out.println(result);
            model.addAttribute("result", result);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
            System.out.println(e.getMessage());
        }

        return "result";
    }
}
