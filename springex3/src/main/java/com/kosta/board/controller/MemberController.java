package com.kosta.board.controller;

import com.kosta.board.dto.Member;
import com.kosta.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private HttpSession httpSession;

    // 로그인 화면 접속
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // 로그인 로직 구현
    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password){
        try{
            Member member = memberService.selectMember(id, password);
            httpSession.setAttribute("user", member);
            return "main";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member){
        try{
            memberService.insertMember(member);
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        httpSession.removeAttribute("user");
        return "main";
    }

    // TODO : 아이디 중복확인
    @PostMapping("/idcheck")
    @ResponseBody
    public String idCheck(@RequestParam String id){
        try{
            Boolean select = memberService.checkId(id);
            System.out.println(select);
            if(select) return String.valueOf(select);
            else return id;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
