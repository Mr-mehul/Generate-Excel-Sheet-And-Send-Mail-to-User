package com.example.demo.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.email.AwsSesExample;

@Controller
public class TypeController {

	@Autowired
	private TypeService typeService;

	@Autowired
	AwsSesExample awsSesExample;

	// Generate Excel Sheet And Download
	@GetMapping("/type/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<TypeDate> listTypes = typeService.findAll();

		TypeExcelExporter excelExporter = new TypeExcelExporter(listTypes);

		excelExporter.export1();
	}

	// Generate Excel Sheet And Send it to Email
	@GetMapping("/type/export/excel/sendMail")
	public void generateExcelAndSendMail(HttpServletResponse response) throws IOException {

		// Find All Data
		List<TypeDate> listTypes = typeService.findAll();

		TypeExcelExporter excelExporter = new TypeExcelExporter(listTypes);

		// Generate Excel And get outputStream
		ByteArrayOutputStream outputStream = excelExporter.export1();

		// Send Email
		awsSesExample.sendEmailWithAttachment(outputStream);

	}
	
	
}
