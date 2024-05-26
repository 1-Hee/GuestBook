package com.onehee.guestbook.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	private int idx;
	private String title;
	private String author;
	private String content;
	private Date createAt;
}
