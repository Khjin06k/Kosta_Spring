package com.kosta.board.dao;

import java.util.List;
import java.util.Map;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<Board> selectBoardList(Integer row) throws Exception {
		return sqlSessionTemplate.selectList("mapper.board.selectBoardList", row);
	}


	@Override
	public Integer selectBoardCount() throws Exception {
		return sqlSessionTemplate.selectOne("mapper.board.selectBoardCount");
	}


	@Override
	public Board selectBoard(Integer num) throws Exception {
		return sqlSessionTemplate.selectOne("mapper.board.selectBoard",num);
	}

	@Override
	public void insertBoard(Board board) throws Exception {
		sqlSessionTemplate.insert("mapper.board.insertBoard", board);
	}

	@Override
	public void updateBoard(Board board) throws Exception {
		sqlSessionTemplate.update("mapper.board.updateBoard", board);
	}


	@Override
	public void deleteBoard(Integer num) throws Exception {
		sqlSessionTemplate.delete("mapper.board.deleteBoard", num);
	}

	@Override
	public Integer selectBoardLike(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("mapper.boardlike.selectBoardLike", param);
	}


	@Override
	public void insertBoardLike(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.insert("mapper.boardlike.insertBoardLike", param);
	}


	@Override
	public void deleteBoardLike(Map<String, Object> param) throws Exception {
		sqlSessionTemplate.delete("mapper.boardlike.deleteBoardLike", param);
	}


	@Override
	public List<Board> searchBoardList(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectList("mapper.board.searchBoardList", param);
	}

	@Override
	public Integer searchBoardCount(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.selectOne("mapper.board.searchBoardCount", param);
	}


	@Override
	public void updateBoardViewCount(Integer num) throws Exception {
		sqlSessionTemplate.update("mapper.board.updateBoardViewCount", num);
	}


	@Override
	public void plusBoardLikeCount(Integer num) throws Exception {
		sqlSessionTemplate.update("mapper.board.plusBoardLikeCount", num);
	}


	@Override
	public void minusBoardLikeCount(Integer num) throws Exception {
		sqlSessionTemplate.update("mapper.board.minusBoardLikeCount", num);
	}


	@Override
	public Integer selectLikeCount(Integer num) throws Exception {
		return sqlSessionTemplate.selectOne("mapper.board.selectLikeCount", num);
	}

	@Override
	public void insertFile(FileVo fileVo) throws Exception {
		sqlSessionTemplate.insert("mapper.board.insertFile", fileVo);
	}

	@Override
	public FileVo selectFile(Integer num) throws Exception {
		return sqlSessionTemplate.selectOne("mapper.board.selectFile", num);
	}

	@Override
	public void deleteFile(String num) throws Exception {
		sqlSessionTemplate.delete("mapper.board.deleteFile", num);
	}


}
