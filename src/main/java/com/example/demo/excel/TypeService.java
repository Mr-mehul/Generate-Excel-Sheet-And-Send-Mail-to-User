package com.example.demo.excel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TypeService {

	@Autowired
	TypeDao daoType;

	public List<TypeDate> findAll() {
		return daoType.findAll();
	}
	
}
