package com.kosta.board.service;

import com.kosta.board.dao.BoardDao;
import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;
import com.kosta.board.dto.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    @Autowired
    private BoardDao boardDao;

    // 게시글 전체 조회 로직
    @Override
    public List<Board> boardListByPage(PageInfo pageInfo) throws Exception {
        int boardCount = boardDao.selectBoardCount();
        int allPage = (int)Math.ceil((double)boardCount/10);
        int startPage = (pageInfo.getCurPage()-1)/10*10+1;
        int endPage = Math.min(startPage+10-1, allPage);
        pageInfo.setAllPage(allPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        if(pageInfo.getCurPage()>allPage) pageInfo.setCurPage(allPage);

        int row = (pageInfo.getCurPage()-1)*10+1;
        return boardDao.selectBoardList(row-1);
    }

    // 게시글 1개 조회 로직
    @Override
    public Board selectBoard(Integer num) throws Exception {
        return boardDao.selectBoard(num);
    }

    // 게시글 등록 로직
    @Override
    public Board insertBoard(Board board, MultipartFile file) throws Exception {
        // file이 null일 때 file.isEmpty()에서 에러가 나기 때문에 if문의 조건 순서를 바꿔주면 에러가 남
        if(file != null && !file.isEmpty()){
            // 이미지를 업로드 할 경로
            String dir = "/Users/gmlwls/Desktop/kosta/Spring/springex3/src/main/resources/upload/";

            System.out.println(file.getName());
            // 파일의 경우 테이블을 따로 만들어서 관리
            // 파일을 업로드하고
            FileVo fileVo = new FileVo();
            fileVo.setDirectory(dir);
            fileVo.setName(file.getOriginalFilename());
            fileVo.setSize(file.getSize());
            fileVo.setContenttype(file.getContentType());
            fileVo.setData(file.getBytes());

            boardDao.insertFile(fileVo);
            Integer num = fileVo.getNum();

            // 파일 업로드
            File uploadFile = new File(dir+num);
            file.transferTo(uploadFile); // transferTo를 하면 경로와 파일 명으로 파일을 업로드
            board.setFileurl(num+"");
        }
        boardDao.insertBoard(board);
        return boardDao.selectBoard(board.getNum());
    }

    // 게시글 이미지 뷰어 로직
    @Override
    public void fileView(Integer num, OutputStream out) throws Exception {
        try {
            FileVo fileVo = boardDao.selectFile(num);
            //FileCopyUtils.copy(fileVo.getData(), out);

            FileInputStream fis = new FileInputStream(fileVo.getDirectory()+num);
            FileCopyUtils.copy(fis, out);

            out.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // 게시글 수정 로직
    @Override
    public Board updateBoard(Board board, MultipartFile file) throws Exception {
        if(file != null && !file.isEmpty()){
            // 이미지를 업로드 할 경로
            String dir = "/Users/gmlwls/Desktop/kosta/Spring/springex3/src/main/resources/upload/";

            System.out.println(file.getName());
            // 파일의 경우 테이블을 따로 만들어서 관리
            // 파일을 업로드하고
            FileVo fileVo = new FileVo();
            fileVo.setDirectory(dir);
            fileVo.setName(file.getOriginalFilename());
            fileVo.setSize(file.getSize());
            fileVo.setContenttype(file.getContentType());
            fileVo.setData(file.getBytes());

            boardDao.insertFile(fileVo);
            Integer num = fileVo.getNum();

            // 파일 업로드
            File uploadFile = new File(dir+num);
            file.transferTo(uploadFile); // transferTo를 하면 경로와 파일 명으로 파일을 업로드
            board.setFileurl(num+"");
        }
        boardDao.updateBoard(board);
        return boardDao.selectBoard(board.getNum());
    }

    // 게시글 삭제 로직
    @Override
    public void deleteBoard(Integer num) throws Exception {
        boardDao.deleteBoard(num);
    }
}
