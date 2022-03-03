package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AwsController {

	@Autowired
	AwsSesExample awsSesExample;
	
	@RequestMapping("/")
	public void sendMail() {
		awsSesExample.sendEmailWithAttachmentFromLocalStorage();
	}
	
}
