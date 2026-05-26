package com.mobicool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobicool.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

}
