package com.onehee.guestbook.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageNavigation {
	private boolean isStart;
	private boolean isEnd;
	private int currentPage;
	private int totalNavCnt;
	private int[] navUnits;
	private int navSize;
	private int pageSize;
}
