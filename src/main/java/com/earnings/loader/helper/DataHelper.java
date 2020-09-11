package com.earnings.loader.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.earnings.loader.model.Earning;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

public class DataHelper {
	public static String TYPE = "text/csv";
	
	public static boolean hasCSVFormat(MultipartFile file) {
		
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		
		return true;
	}
	
	public static List<Earning> csvToEarnings(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			
			List<Earning> earnings = new ArrayList<Earning>();
			
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			
			for (CSVRecord csvRecord : csvRecords) {
				Earning earning = null;
				try {
					earning = new Earning(
							csvRecord.get("AnnouncementTime"),
							csvRecord.get("Company"),
							csvRecord.get("Exchange"),
							csvRecord.get("Symbol"),
							csvRecord.get("Currency"),
							csvRecord.get("Term"),
							Float.parseFloat(csvRecord.get("PreviousClose"))
					);
				} catch (Exception e) {
					String error = "Exception found while processing, ignoring " + e;
					continue;
				}
				earnings.add(earning);
			}
			
			return earnings;
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
		}
	}
	
	public static ByteArrayInputStream earningsToCSV(List<Earning> earnings) {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (Earning earning : earnings) {
				List<String> data = Arrays.asList(
						String.valueOf(earning.getId()),
						earning.getAnnouncementTime(),
						earning.getCompany(),
						earning.getExchange(),
						earning.getSymbol(),
						earning.getCurrency(),
						earning.getTerm(),
						String.valueOf(earning.getPreviousClose())
				);
				
				csvPrinter.printRecord(data);
			}
			
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException("Failed to import data to CSV file: " + e.getMessage());
		}
	}
	
}
