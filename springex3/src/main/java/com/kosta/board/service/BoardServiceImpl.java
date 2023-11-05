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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String dir = "/Users/gmlwls/Desktop/kosta/Spring/springex3/src/main/webapp/WEB-INF/upload/";

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
            String dir = "/Users/gmlwls/Desktop/kosta/Spring/springex3/src/main/webapp/WEB-INF/upload/";

            // 파일 삭제 관련 로직
            // 현재는 File 테이블과 Board 묶여있지 않아 삭제 후 추가하거나, 추가하고 삭제해도 상관이 없지만, 만약 두개가 연결되어 있다면 삭제 후 업데이트가 이루어져야 에러가 나지 않음
            // 즉, 파일 정보를 DB에 추가한 다음, 파일을 업로드 폴더에 업로드, 기존 파일 번호 삭제를 위해 변수에 저장 후 업데이트 진행, 기존 파일을 삭제 >> 이래사 FK 관련 에러가 나지 않음
            if(board.getFileurl() != null || !board.getFileurl().isEmpty()){
                String deleteFileNum = boardDao.selectBoard(board.getNum()).getFileurl();
                File deletefile = new File(dir+deleteFileNum);
                if(deletefile.exists()) deletefile.delete();
                boardDao.deleteFile(deleteFileNum); // DB에서 기존 파일 삭제
            }

            // 파일의 경우 테이블을 따로 만들어서 관리
            FileVo fileVo = new FileVo();
            fileVo.setDirectory(dir);
            fileVo.setName(file.getOriginalFilename());
            fileVo.setSize(file.getSize());
            fileVo.setContenttype(file.getContentType());
            fileVo.setData(file.getBytes());

            // 서버에 파일 정보 저장
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
        Board board = boardDao.selectBoard(num);
        if(board != null) {
            boardDao.deleteFile(board.getFileurl());
            boardDao.deleteBoard(num);
        }
    }

    @Override
    public Boolean isboardList(String userId, Integer boardNum) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("id", userId);
        param.put("num", boardNum);
        Integer likeNum = boardDao.selectBoardLike(param);
        return likeNum != null;
    }

    @Override
    public Boolean selectBoardLike(String userId, Integer boardNum) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("id", userId);
        param.put("num", boardNum);
        Integer likeNum = boardDao.selectBoardLike(param);

        if(likeNum ==null){
            boardDao.insertBoardLike(param);
            boardDao.plusBoardLikeCount(boardNum);
            return true;
        }else{
            boardDao.deleteBoardLike(param);
            boardDao.minusBoardLikeCount(boardNum);
            return false;
        }
    }

    @Override
    public List<Board> searchBoardList(PageInfo pageInfo, Map<String, Object> param) throws Exception {
        int boardCount = boardDao.searchBoardCount(param);
        int allPage = (int)Math.ceil((double)boardCount/10);
        int startPage = (pageInfo.getCurPage()-1)/10*10+1;
        int endPage = Math.min(startPage+10-1, allPage);
        pageInfo.setAllPage(allPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        if(pageInfo.getCurPage()>allPage) pageInfo.setCurPage(allPage);

        int row = (pageInfo.getCurPage()-1)*10+1;
        param.put("row", row-1);
        return boardDao.searchBoardList(param);
    }
}
