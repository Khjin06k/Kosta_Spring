package com.kosta.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class MainController {

    // local.info를 사용할 때 사용
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/")
    public String main(Locale locale, Model model){
        logger.debug("메인 접속"); // 로그 생길 때
        return "main";
    }
}
