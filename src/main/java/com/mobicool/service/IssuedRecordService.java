package com.mobicool.service;

import com.mobicool.repository.BookRepo;
import com.mobicool.repository.IssuedBookRepo;
import com.mobicool.repository.UserRepo;

import java.time.LocalDate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mobicool.entity.Book;
import com.mobicool.entity.IssuedBook;
import com.mobicool.entity.User;

@Service
public class IssuedRecordService {

	private final BookRepo bookRepo;
	private final IssuedBookRepo issuedBookRepo;
	private final UserRepo userRepo;

	IssuedRecordService(BookRepo bookRepo, IssuedBookRepo issuedBookRepo, UserRepo userRepo) {
		this.bookRepo = bookRepo;
		this.issuedBookRepo = issuedBookRepo;
		this.userRepo = userRepo;
	}

	public IssuedBook issueTheBook(Long bookId) {

		Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

		if (book.getQuantity() <= 0 || !book.getIsAvailable()) {
			throw new RuntimeException("Book is not available");
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("There is no user with this username"));

		IssuedBook issuedBook = new IssuedBook();
		issuedBook.setBook(book);
		issuedBook.setUser(user);
		issuedBook.setIsReturned(false);
		issuedBook.setIssuedDate(LocalDate.now());
		issuedBook.setDueDate(LocalDate.now().plusDays(30));

		book.setQuantity(book.getQuantity() - 1);
		if (book.getQuantity() == 0) {
			book.setIsAvailable(false);
		}

		return issuedBookRepo.save(issuedBook);

	}

	public IssuedBook returnTheBook(Long issueRecordId) {
		IssuedBook issuedBook = issuedBookRepo.findById(issueRecordId)
				.orElseThrow(() -> new RuntimeException("wrong issueRecord id"));

		if (issuedBook.getIsReturned()) {
			throw new RuntimeException("Book is already Returned ");
		}

		Book book = issuedBook.getBook();
		book.setQuantity(book.getQuantity() + 1);
		book.setIsAvailable(true);
		bookRepo.save(book);

		issuedBook.setReturnDate(LocalDate.now());
		issuedBook.setIsReturned(true);

		return issuedBookRepo.save(issuedBook);
	}

}
