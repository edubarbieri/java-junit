package br.com.edubarbieri.services;

import br.com.edubarbieri.entities.User;

public interface EmailService {
	void notifyOverdue(User user);
}
