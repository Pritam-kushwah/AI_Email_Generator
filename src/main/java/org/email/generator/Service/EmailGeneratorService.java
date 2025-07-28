package org.email.generator.Service;

import java.util.Map;

import org.email.generator.Model.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.*;

//import lombok.Value;

@Service
public class EmailGeneratorService {
	
	private final WebClient webClient;
	
	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	
	public EmailGeneratorService(WebClient.Builder webClientBuilder)
	{
		this.webClient=webClientBuilder.build();
	}
	
	public String generateEmailReply(EmailRequest emailRequest)
	{
		// Build the prompt
		String prompt = buildPrompt(emailRequest);
		
		// Craft a request
		Map<String, Object> requestBody = Map.of(
				"contents", new Object[] {
						Map.of("parts",new Object[] {
								Map.of("text",prompt)
						})
				}
		);
		
		// Do request and get response
		try {
		    String response = webClient.post()
		            .uri(geminiApiUrl + "?key=" + geminiApiKey)
		            .header("Content-Type", "application/json")
		            .bodyValue(requestBody)
		            .retrieve()
		            .bodyToMono(String.class)
		            .block();

		    return extractResponseContent(response);
		} catch (WebClientRequestException e) {
		    return "Internet connection error. Please check your network.";
		} catch (WebClientResponseException e) {
		    return "API Error: " + e.getStatusCode() + " - " + e.getStatusText();
		} catch (Exception e) {
		    return "Unexpected error: " + e.getMessage();
		}

	}

	private String extractResponseContent(String response) {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper=new ObjectMapper();
			JsonNode rootNode=mapper.readTree(response);
			return rootNode.path("candidates")
					.get(0)
					.path("content")
					.path("parts")
					.get(0)
					.path("text")
					.asText();
		}
		catch(Exception e)
		{
			return "Something wrong !";
		}
		
	}

	private String buildPrompt(EmailRequest emailRequest)
	{
		StringBuilder prompt = new StringBuilder();
		prompt.append("Generate a email reply for the following email content."
				+ "Please don't generate subject line ");
		if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty())
		{
			prompt.append("Use a ").append(emailRequest.getTone()).append(" tone.If input email contains"
					+ " any informal, offensive, or unprofessional language than reply 'Sorry! containt is unprofessional'"
					+ " other wise generate reply of email");
		}
		if(emailRequest.getLanguage()!=null && !emailRequest.getLanguage().isEmpty())
		{
			prompt.append(" Give response in ").append(emailRequest.getLanguage()).append(" language");
		}
		prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
		System.out.println(prompt);
		return prompt.toString();
	}
}
