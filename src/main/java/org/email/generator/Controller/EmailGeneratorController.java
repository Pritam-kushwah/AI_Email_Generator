package org.email.generator.Controller;

import org.email.generator.Model.EmailRequest;
import org.email.generator.Service.EmailGeneratorService;
import org.email.generator.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class EmailGeneratorController {

	@Autowired
	private EmailGeneratorService emailService;
	@Autowired
	private HistoryService hs;
	
	@PostMapping("/generate")
	public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest)
	{
		String response = emailService.generateEmailReply(emailRequest);
		if(response==null&&response.isEmpty()) return ResponseEntity.ok("Oops something is wrong !..");
		hs.saveEmail(
			    emailRequest.getEmailContent(),
			    response,
			    emailRequest.getTone(),
			    emailRequest.getLanguage()
			);
		return ResponseEntity.ok(response);
	}
}
