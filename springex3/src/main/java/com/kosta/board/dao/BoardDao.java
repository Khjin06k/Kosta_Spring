package com.kosta.board.dao;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;

import java.io.File;
import java.util.List;
import java.util.Map;


public interface BoardDao {
	List<Board> selectBoardList(Integer row) throws Exception;
	Integer selectBoardCount() throws Exception;
	Board selectBoard(Integer num) throws Exception;

	void insertBoard(Board board) throws Exception;
	void updateBoard(Board board) throws Exception;
	void deleteBoard(Integer num) throws Exception;

	void insertFile(FileVo fileVo) throws Exception;
	FileVo selectFile(Integer num) throws Exception;
	void deleteFile(String num) throws Exception;

	Integer selectBoardLike(Map<String, Object> param) throws Exception;
	void insertBoardLike(Map<String, Object> param) throws Exception;
	void deleteBoardLike(Map<String, Object> param) throws Exception;

	Integer searchBoardCount(Map<String, Object> param) throws Exception;
	List<Board> searchBoardList(Map<String,Object> param) throws Exception;
	void updateBoardViewCount(Integer num) throws Exception;
	Integer selectLikeCount(Integer num) throws Exception;
	void plusBoardLikeCount(Integer num) throws Exception;
	void minusBoardLikeCount(Integer num) throws Exception;



}
