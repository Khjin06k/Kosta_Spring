package com.kosta.board.controller;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.PageInfo;
import com.kosta.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

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
    @GetMapping("/boarddelete")
    public String boardDelete(@RequestParam Integer num, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, Model model){
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

}
