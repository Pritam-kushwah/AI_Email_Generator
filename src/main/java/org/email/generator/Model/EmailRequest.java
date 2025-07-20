package org.email.generator.Model;

import lombok.Data;

@Data
public class EmailRequest {
	private String emailContent;
	private String tone;
	private String language;
}
