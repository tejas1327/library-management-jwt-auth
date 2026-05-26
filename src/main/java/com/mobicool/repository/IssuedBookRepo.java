package com.mobicool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobicool.entity.IssuedBook;

@Repository
public interface IssuedBookRepo extends JpaRepository<IssuedBook, Long> {

}
