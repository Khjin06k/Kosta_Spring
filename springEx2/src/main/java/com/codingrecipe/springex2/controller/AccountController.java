package com.codingrecipe.springex2.controller;

import com.codingrecipe.springex2.dto.Account;
import com.codingrecipe.springex2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AccountController {
    // accountService는 빈 객체를 1개만 생성
    // Autowired (객체를 주입)하려고 보니까 생성된 아이가 있따면 생성
    // 만약 하나가 아니라면 이름이 동일한 것을 선택
    // 이름을 찾기 못할 경우 에러 발생
    // Controller의 경우에는 @Controller 애너테이션에 의해서 객체를 생성해주기 때문에 .xml에 따로 설정하지 않아도 사용이 가능
    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    // @RequestMapping(value = "/", method = RequestMethod.GET)
    // 위 애너테이션 중 하나 선택해서 작성
    public String main(){
        return "main";
    }

    // header.jsp에서 각 nav에 연결되는 href와 value 값이 동일해야 함
    // return 값은 view를 말하는 것이기 때문에 wiews에 있는 jsp 파일의 이름과 동일해야 함
    @GetMapping("/makeaccount")
    public String makeAccount(){
        return "makeaccount";
    }

    @RequestMapping(value = "/makeaccount", method = RequestMethod.POST)
    public String makeAccount(@ModelAttribute Account acc, Model model){
        try{
            accountService.makeAccount(acc);
            model.addAttribute("acc", acc);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("err", "에러 발생");
            return "error";
        }
    }

    @GetMapping("/deposit")
    public String deposit(){
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String id, @RequestParam Integer balance, Model model){
        try{
            // 계좌 조회
            Account acc = accountService.selectAccount(id);

            // 출금 로직 진행 및 param 생성
            acc.setBalance(acc.getBalance()+balance);

            // 금액 업데이트 진행
            accountService.updateAccountBalance(acc);
            model.addAttribute("acc", acc);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("err", "에러 발생");
            return "error";
        }
    }

    /* ModelAndView : Model과 View를 동시에 넘겨주는 것으로 아래와 같이 다르게 작성할 수 있음

    @PostMapping("/deposit")
    public ModelAndView deposit(@RequestParam String id, @RequestParam Integer balance){
        ModelAndView mav = new ModelAndView();
        try{
            // 계좌 조회
            Account acc = accountService.selectAccount(id);

            // 출금 로직 진행 및 param 생성
            acc.setBalance(acc.getBalance()+balance);
            // acc.deposit(balance);로 짜여있는 메서드 사용해도 됨
            Map<String, Object> param = new HashMap<>();
            param.put("id", id);
            param.put("balance", acc.getBalance());

            // 금액 업데이트 진행
            accountService.updateAccountBalance(param);

            // ModelAndView를 이용해서 넘겨줄 수도 있음
            mav.addObject("acc", acc);
            mav.setViewName("accountinfo");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            mav.addObject("err", "에러 발생");
            mav.setViewName("error");
        }
    }*/

    @GetMapping("/withdraw")
    public String withdraw(){
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String id, @RequestParam Integer balance, Model model){
        try{
            // 계좌 조회
            Account acc = accountService.selectAccount(id);

            // 금액 업데이트
            accountService.updateAccountBalance(acc);

            model.addAttribute("acc", acc);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("err", "에러 발생");
            return "error";
        }
    }

    @GetMapping("/accountinfo")
    public String accountInfo(){
        return "accountinfoform";
    }

    @PostMapping("/accountinfo")
    public String accounInfo(@RequestParam("id") String id, Model model){
        try{
            System.out.println(id);
            Account acc = accountService.selectAccount(id);
            model.addAttribute("acc", acc);
            return "accountinfo";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("err", "에러 발생");
            return "error";
        }
    }

    @GetMapping("/allaccountinfo")
    public String allAccountInfo(Model model){
        try{
            List<Account> accs = accountService.selectAccountList();
            model.addAttribute("accs", accs);
            return "allaccountinfo";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            model.addAttribute("err", "에러 발생");
            return "error";
        }

    }
}
