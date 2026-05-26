package com.mobicool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
	private String title;
	private String author;
	private String isbm;
	private Integer quantity;
	private Boolean isAvailable;
}
