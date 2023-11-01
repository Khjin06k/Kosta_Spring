package com.codingrecipe.springex2.controller;

import com.codingrecipe.springex2.dto.Member;
import com.codingrecipe.springex2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpSession session, Model model){
        try{
            Member member = memberService.selectMember(id);
            System.out.println(member.getPassword());
            if(member == null || !member.getPassword().equals(password)){
                model.addAttribute("err", "로그인 실패");
                return "error";
            }
            session.setAttribute("id", id);
            return "main";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("err", "에러 발생");
            return "error";
        }
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/join")
    public String join(@RequestParam String id, @RequestParam String name,
                       @RequestParam String password, @RequestParam String email,
                       @RequestParam String address, Model model){
        Member member = new Member(id, name, password, email, address);
        try{
            memberService.insertMember(member);
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("err", "에러 발생");
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("id");
        return "main";
    }
}
