package org.email.generator.Service;

import java.util.Optional;

import org.email.generator.Model.EmailHistory;
import org.email.generator.Repository.ManageHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
	
	@Autowired
	private ManageHistory manageHistory;
	
	 public void saveEmail(String original, String reply, String tone, String language) {
	        EmailHistory emailHistory = new EmailHistory();
	        emailHistory.setOriginalEmail(original);
	        emailHistory.setReplyEmail(reply);
	        emailHistory.setTone(tone);
	        emailHistory.setLanguage(language);

	        manageHistory.save(emailHistory);
	    }
	
	public Optional<EmailHistory> getById(long id)
	{
		return manageHistory.findById(id);
	}
}
