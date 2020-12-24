package com.earnings.loader.controller;

import java.util.List;

import com.earnings.loader.model.Earning;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.earnings.loader.helper.DataHelper;
import com.earnings.loader.helper.ResponseMessage;
import com.earnings.loader.service.DataService;

@CrossOrigin("*")
@Controller
@RequestMapping("/earnings/loader/csv")
@Api(value = "Data microservice", description = "API for getting and saving data")
public class DataController {

	@Autowired
	DataService dataService;
	
	@PostMapping("/load")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message;
		
		if (DataHelper.hasCSVFormat(file)) {
			try {
				dataService.save(file);
				
				message = "File uploaded successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}			
		}
		
		message = "Please choose a csv file";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
	
	@GetMapping("/earnings")
	public ResponseEntity<List<Earning>> getAllEarnings() {
		try {
			List<Earning> earnings = dataService.getAllEarnings();
			
			if (earnings.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(earnings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		String filename = "earnings.csv";
		InputStreamResource file = new InputStreamResource(dataService.load());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv"))
				.body(file);
	}
}
