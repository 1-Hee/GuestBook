package com.onehee.guestbook.model;

import java.util.List;

import com.onehee.guestbook.util.PageNavigation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contents {
	private List<BoardDTO> boardList;
	private PageNavigation pgNav;
	private String keyword;
}
