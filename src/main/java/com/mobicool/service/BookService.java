package com.mobicool.service;

import java.util.List;
import com.mobicool.repository.BookRepo;

import org.springframework.stereotype.Service;

import com.mobicool.dto.BookDTO;
import com.mobicool.entity.Book;

@Service
public class BookService {

	private final BookRepo bookRepo;

	BookService(BookRepo bookRepo) {
		this.bookRepo = bookRepo;
	}

	public List<Book> getAllBooks() {

		return bookRepo.findAll();

	}

	public Book getBookByID(Long id) {

		return bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book Not Found"));
	}

	public Book addBook(BookDTO bookDTO) {
		Book book = new Book();
		book.setAuthor(bookDTO.getAuthor());
		book.setIsbm(bookDTO.getIsbm());
		book.setTitle(bookDTO.getTitle());
		book.setIsAvailable(bookDTO.getIsAvailable());	
		book.setQuantity(bookDTO.getQuantity());

		return bookRepo.save(book);
	}

	public Book updateBook(BookDTO bookDTO, Long bookId) {
		Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Found"));
		book.setAuthor(bookDTO.getAuthor());
		book.setIsbm(bookDTO.getIsbm());
		book.setTitle(bookDTO.getTitle());
		book.setIsAvailable(bookDTO.getIsAvailable());
		book.setQuantity(bookDTO.getQuantity());

		return bookRepo.save(book);
	}

	public String deleteBookById(Long BookId) {

		Book book = bookRepo.findById(BookId).orElseThrow(() -> new RuntimeException("Book Not Found"));
		bookRepo.delete(book);
		return "deleted";
	}

}
