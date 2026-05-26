package com.mobicool.controller;

import com.mobicool.service.IssuedRecordService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobicool.entity.IssuedBook;

@RestController
@RequestMapping("/issuerecords")
public class IssuedRecordController {

	private final IssuedRecordService issuedRecordService;

	IssuedRecordController(IssuedRecordService issuedRecordService) {
		this.issuedRecordService = issuedRecordService;
	}

	@PostMapping("issuethebook/{bookid}")
	public ResponseEntity<IssuedBook> issueTheBook(@PathVariable Long bookId) {
		return new ResponseEntity<IssuedBook>(issuedRecordService.issueTheBook(bookId), HttpStatus.CREATED);
	}

	@PostMapping("/returnThebook/{issueRecordId}")
	public ResponseEntity<IssuedBook> returnTheBook(@PathVariable Long issueRecordId) {
		return new ResponseEntity<IssuedBook>(issuedRecordService.returnTheBook(issueRecordId), HttpStatus.OK);
	}
}
