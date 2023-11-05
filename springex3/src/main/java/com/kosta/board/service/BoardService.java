package com.kosta.board.service;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface BoardService {

    List<Board> boardListByPage(PageInfo pageInfo) throws Exception;
    Board selectBoard(Integer num) throws Exception;
    Board insertBoard(Board board, MultipartFile file) throws Exception;
    void fileView(Integer num, OutputStream out) throws Exception;

    Board updateBoard(Board board, MultipartFile file) throws Exception;
    void deleteBoard(Integer num) throws Exception;

    // 사용자가 글을 선택했는지 여부
    Boolean isboardList(String userId, Integer boardNum) throws Exception;
    // 사용자가 좋아요를 눌렀을 경우 처리하고 선택 여부 가져오기
    Boolean selectBoardLike(String userId, Integer boardNum) throws Exception;

    // 검색기능
    List<Board> searchBoardList(PageInfo pageInfo, Map<String, Object> param) throws Exception;


}
