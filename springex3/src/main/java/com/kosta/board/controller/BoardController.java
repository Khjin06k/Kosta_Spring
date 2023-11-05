package com.kosta.board.controller;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.Member;
import com.kosta.board.dto.PageInfo;
import com.kosta.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private HttpSession session;

    // TODO : 게시글 조회 관련
    // Integer는 null로 대체가 가능하지만, int는 null이 불가능.
    // required는 반드시 필요한지 여부(null이어도 받아올지), defaultValue는 null일 경우 설정할 기본값
    // 게시글 전체 조회 (pagination까지 포함)
    @GetMapping("/boardlist")
    public ModelAndView boardList(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page){
       ModelAndView modelAndView = new ModelAndView();
       try{
           PageInfo pageInfo = new PageInfo();
           pageInfo.setCurPage(page);
           List<Board> boardList = boardService.boardListByPage(pageInfo);
           modelAndView.addObject("pageInfo", pageInfo);
           modelAndView.addObject("boardList", boardList);
           modelAndView.setViewName("boardlist");
       }catch (Exception e){
           e.printStackTrace();
           System.out.println(e.getMessage());
           System.out.println(e.getCause());
           modelAndView.setViewName("error");
       }

       return modelAndView;
    }

    // 게시글 1개 조회
    // 파라미터 값으로 게시글 넘버를 불러와 조회 후 디테일 페이지로 넘겨줌
    @GetMapping("/boarddetail")
    public String detail(@RequestParam Integer num, Model model){
        try{
            Board board = boardService.selectBoard(num);
            Member user = (Member) session.getAttribute("user");
            Boolean select = boardService.selectBoardLike(user.getId(), num);
            model.addAttribute("select", select);
            model.addAttribute("board", board);
            return "detailform";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }

    // TODO : 게시글 작성 관련
    // 글쓰기 페이지 불러오기
    @GetMapping("/boardwrite")
    public String boardwrite(){
        return "writeform";
    }

    // 게시판 작성 (현재 board가 null이 들어가는 상황)
    // 파일은 따로 가져와 주어야 하며, file의 url은 수동으로 넣어주어야 함
    @PostMapping("/boardwrite")
    public String boardwrite(@ModelAttribute Board board, @RequestParam("file") MultipartFile file, Model model){
        try{
            Board writeboard = boardService.insertBoard(board, file);
            model.addAttribute("board", writeboard);
            return "detailform";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }

    // TODO : 이미지 뷰
    @RequestMapping(value="/image/{num}")
    public void imageView(@PathVariable Integer num, HttpServletResponse response) {
        try {
            boardService.fileView(num, response.getOutputStream());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // TODO : 게시글 수정 관련
    // 게시글 수정 페이지로 이동
    @GetMapping("/boardmodify")
    public String boardmodify(@RequestParam Integer num, Model model){
        try{
            Board board = boardService.selectBoard(num);
            model.addAttribute("board", board);
            return "modifyform";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }

    // 게시글 수정 진행
    @PostMapping("/boardmodify")
    public String boardmodify(@ModelAttribute Board board, @RequestParam MultipartFile file, Model model){
        try{
            model.addAttribute("board", boardService.updateBoard(board, file));
            return "detailform";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }


    // TODO : 게시글 삭제 로직 구현
    @GetMapping("/boarddelete/{num}/{page}")
    public String boardDelete(@PathVariable Integer num, @PathVariable Integer page, Model model){
        try{
            boardService.deleteBoard(num);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurPage(page);
            List<Board> boardList = boardService.boardListByPage(pageInfo);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("boardList", boardList);
            return "boardlist";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }

    // TODO : 좋아요
    @PostMapping("/like")
    @ResponseBody // 리턴으로 주는 것이 뷰가 아닌 데이터
    public String boardLike(@RequestParam Integer num){
        Member user = (Member)session.getAttribute("user");
        try{
            if(user==null) throw new Exception("로그인하세요");
            Boolean select = boardService.selectBoardLike(user.getId(), num);
            System.out.println(select);
            return String.valueOf(select);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/boardsearch")
    public String boardList(@RequestParam String type, @RequestParam String keyword,
                            @RequestParam Integer page, Model model){

        try{
            // 검색에 대한 정보
            System.out.println(type);
            System.out.println(keyword);
            Map<String, Object> param = new HashMap<>();
            param.put("type", type);
            param.put("keyword", keyword);

            // 페이지에 대한 정보
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurPage(page);
            List<Board> boardList = boardService.searchBoardList(pageInfo, param);
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("boardList", boardList);
            return "boardlist";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return "error";
        }
    }
}
