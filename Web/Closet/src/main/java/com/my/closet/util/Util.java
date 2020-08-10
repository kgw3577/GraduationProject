package com.my.closet.util;

import com.my.closet.board.vo.BoardVO;
import com.my.closet.social.vo.ExpandedFeedVO;
import com.my.closet.social.vo.FeedVO;
import com.my.closet.social.vo.FollowVO;

public class Util {
	

	public static BoardVO setPageFilter(BoardVO boardFilter, String page, String pageSize) {
		// 페이지 필터 생성
		if (page != null && pageSize != null) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			boardFilter.setPageStart(pageInt * pageSizeInt);
			boardFilter.setPageSize(pageSizeInt);
		}
		return boardFilter;
	}
	
	public static FollowVO setPageFilter(FollowVO followFilter, String page, String pageSize) {
		// 페이지 필터 생성
		if (page != null && pageSize != null) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			followFilter.setPageStart(pageInt * pageSizeInt);
			followFilter.setPageSize(pageSizeInt);
		}
		return followFilter;
	}
	
	public static FeedVO setPageFilter(FeedVO feedFilter, String page, String pageSize) {
		// 페이지 필터 생성
		if (page != null && pageSize != null) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			feedFilter.setPageStart(pageInt * pageSizeInt);
			feedFilter.setPageSize(pageSizeInt);
		}
		return feedFilter;
	}
	
	public static ExpandedFeedVO setPageFilter(ExpandedFeedVO feedFilter, String page, String pageSize) {
		// 페이지 필터 생성
		if (page != null && pageSize != null) {
			int pageInt = Integer.parseInt(page);
			int pageSizeInt = Integer.parseInt(pageSize);
			feedFilter.setPageStart(pageInt * pageSizeInt);
			feedFilter.setPageSize(pageSizeInt);
		}
		return feedFilter;
	}
	
}
