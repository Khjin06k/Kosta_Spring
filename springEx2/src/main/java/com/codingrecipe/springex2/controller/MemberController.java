package com.codingrecipe.springex2.controller;

import com.codingrecipe.springex2.dto.Member;
import com.codingrecipe.springex2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private HttpSession session; // 실행되는 시점에 이미 생성이 되어 있기 때문에 Autowired를 사용해서 쓸 수 있는 것

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, Model model){
        try{
            Member member = memberService.selectMember(id);
            System.out.println(member.getPassword());
            if(member == null || !member.getPassword().equals(password)){
                model.addAttribute("err", "로그인 실패");
                return "error";
            }
            member.setPassword(""); // 세션에 넣기 전에 비밀번호는 삭제
            session.setAttribute("member", member);
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
    public String join(@ModelAttribute Member member, Model model){
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
    public String logout(){
        session.removeAttribute("member");
        return "main";
    }
}
