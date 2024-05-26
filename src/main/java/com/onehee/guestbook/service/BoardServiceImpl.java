package com.onehee.guestbook.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onehee.guestbook.config.SizeConstant;
import com.onehee.guestbook.model.BoardDTO;
import com.onehee.guestbook.model.dao.BoardDao;
import com.onehee.guestbook.model.mapper.BoardMapper;
import com.onehee.guestbook.util.PageNavigation;

@Service
public class BoardServiceImpl implements BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	private BoardMapper boardMapper;
	private final BoardDao boardDao;
	
	@Autowired
	private BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public int writeArticle(BoardDTO boardDTO) throws Exception {				
		if(boardDTO.getAuthor() == null) {
			boardDTO.setAuthor("익명");
			Date date = new Date();
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			// 날짜 형식 지정
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	        
	        // Calendar 객체의 날짜와 시간을 지정된 형식으로 포맷팅
	        String formattedDate = dateFormat.format(calender.getTime());			
			logger.info("unkown author create article.. {}", formattedDate);
		}		
		return boardMapper.insertArticle(boardDTO);
		// return boardDao.insertArticle(boardDTO);
	}

	@Override
	public List<BoardDTO> readBoardList(Map<String, Object> map, boolean isMobile) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : String.valueOf(map.get("pgno")));
		int listSize = isMobile ? SizeConstant.MOB_LIST_SIZE : SizeConstant.LIST_SIZE;
		int start = pgNo * listSize - listSize;
		param.put("pgNo", start);
		param.put("size", listSize);
		List<BoardDTO> dbBoardList = boardMapper.selectBoardList(param);
		
		// 시스템의 로컬 시간대 가져오기
		// 한국 시간대(KST) 가져오기
//        ZoneId kstZone = ZoneId.of("Asia/Seoul");

        List<BoardDTO> kBoardList = new ArrayList<>();
        for (BoardDTO boardDto : dbBoardList) {
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(boardDto.getCreateAt());
        	calendar.add(Calendar.HOUR, -9);
//            // Date를 LocalDateTime으로 변환
//            LocalDateTime localDateTime = boardDto.getCreateAt().toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
//            // UTC에서 한국 시간대로 변환
//            ZonedDateTime kstZonedDateTime = localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(kstZone);
//            // ZonedDateTime을 Date로 변환
//            Date kstDate = Date.from(kstZonedDateTime.toInstant());
//            boardDto.setCreateAt(kstDate);
        	boardDto.setCreateAt(calendar.getTime());
            kBoardList.add(boardDto);
        }
		return kBoardList;
	}
	
	// 페이징을 위한 페이지 객체 리턴 메서드
	@Override
	public PageNavigation getPageNav(Map<String, Object> params, boolean isMobile) throws Exception{		
		int page = 1;
		try {
			page = Integer.parseInt(params.get("pgno").toString());			
		}catch (Exception e) {
			e.printStackTrace();
		}
		int listSize = isMobile ? SizeConstant.MOB_LIST_SIZE : SizeConstant.LIST_SIZE;
		boolean isStart = (page == 1);
		params.put("size", listSize);
		int maxCnt = boardMapper.getNavCnt(params);					
		logger.info("max size => {}, listSize => {}", maxCnt, listSize);
		boolean isEnd = (page == maxCnt);		
		
		int[] navUnits = createNavUnit(page, maxCnt);
		int navSize = navUnits.length;		
		int pageSize = listSize;			
		PageNavigation pageNavigation = new PageNavigation(
			isStart, isEnd, page, maxCnt, navUnits,  navSize, pageSize
		);
		return pageNavigation;
	}		
	
	private int[] createNavUnit(int page, int maxCnt) {
		 int gap = (maxCnt - page) + 1;
		 int sIndex = page;
		 int eIndex = Math.min(page + SizeConstant.NAVIGATION_SIZE, maxCnt + 1);		 
		 if(SizeConstant.NAVIGATION_SIZE >= gap) {						
			int fowradGap = SizeConstant.NAVIGATION_SIZE - gap;
			int cfIndex = (page - fowradGap);
			if(cfIndex > 0) {
				sIndex = cfIndex;
			}		
						
//			System.out.println("page = " +page + ", fgap : " 
//			+ fowradGap +", cfIndex : "+cfIndex 
//			+ ", sIndex : " + sIndex + ", eIndex : "+eIndex);
		 }
		 
		 if(maxCnt < SizeConstant.NAVIGATION_SIZE) {
			 sIndex = 1;
		 }
		 
		 int length = eIndex - sIndex;
		 int[] navUnits = new int[length];
		 for(int i = 0; i < length ; i ++) {
			 navUnits[i] = sIndex + i;
		 }				
		return navUnits;
	}
	

	@Override
	public BoardDTO getBoardInfo(int idx) throws Exception {
		return boardMapper.getBoardInfo(idx);
		// return boardDao.getBoardInfo(idx);
	}

	@Override
	public void editBoard(BoardDTO boardDTO) throws Exception {
		// boardDao.updateBoard(boardDTO);
		boardMapper.updateBoard(boardDTO);
	}

	@Override
	public void removeBoard(int idx) throws Exception {
		//boardDao.deleteBoard(idx);		
		boardMapper.deleteBoard(idx);
	}
}
