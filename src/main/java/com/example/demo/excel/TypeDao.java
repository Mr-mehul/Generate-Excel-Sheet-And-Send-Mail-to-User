package com.example.demo.excel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends JpaRepository<TypeDate, Long> {

}
