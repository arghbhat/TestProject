package com.team.alert.customexception;

import java.util.ArrayList;
import java.util.List;

public class DevelopersMissedAlertException extends Exception{

	
	private static final long serialVersionUID = 1L;
	
	private List<String> messages;

	public DevelopersMissedAlertException() {
		super("Failed to send alert for developers");
		messages=new ArrayList<>();
	}

	public List<String> getMessages() {
		return messages;
	}
	
	
}
