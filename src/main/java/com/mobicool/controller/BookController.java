package com.mobicool.controller;

import java.util.List;
import com.mobicool.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobicool.dto.BookDTO;
import com.mobicool.entity.Book;

@RestController
@RequestMapping("/api/book")
public class BookController {

	private final BookService bookService;

	BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Book>> getAllBooks() {
		return new ResponseEntity<List<Book>>(bookService.getAllBooks(), HttpStatus.OK);

	}

	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
		return new ResponseEntity<Book>(bookService.getBookByID(bookId), HttpStatus.OK);

	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO) {
		return new ResponseEntity<Book>(bookService.addBook(bookDTO), HttpStatus.CREATED);

	}

	@PutMapping("/{bookId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateBook(@RequestBody BookDTO bookDTO, @PathVariable Long bookId) {
		return new ResponseEntity<Book>(bookService.updateBook(bookDTO, bookId), HttpStatus.CREATED);

	}

	@DeleteMapping("/{BookId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteBook(@PathVariable Long BookId) {
		return new ResponseEntity<String>(bookService.deleteBookById(BookId), HttpStatus.OK);
	}

}
