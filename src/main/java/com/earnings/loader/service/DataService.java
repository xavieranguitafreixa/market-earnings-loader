package com.earnings.loader.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.earnings.loader.model.Earning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.earnings.loader.helper.DataHelper;
import com.earnings.loader.repository.EarningRepository;

@Service
public class DataService {

	@Autowired
	EarningRepository repository;
	
	public void save(MultipartFile file) throws Exception {
		try {
			List<Earning> earnings = DataHelper.csvToEarnings(file.getInputStream());
			repository.saveAll(earnings);
		} catch (IOException e) {
			throw new RuntimeException("Failed to store csv data: " + e.getMessage());
		} catch (Exception ex) {
			throw new Exception("Failed to store csv data: " + ex.getMessage());
		}
	}
	
	public ByteArrayInputStream load() {
		List<Earning> earnings = repository.findAll();
		
		ByteArrayInputStream in = DataHelper.earningsToCSV(earnings);
		return in;
	}
	
	public List<Earning> getAllEarnings() {
		return repository.findAll();
	}
}
