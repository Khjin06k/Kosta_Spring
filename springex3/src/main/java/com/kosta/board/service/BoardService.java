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

}
