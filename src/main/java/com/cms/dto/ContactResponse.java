package com.cms.dto;

import java.util.List;

public class ContactResponse {

	List<ContactDTO> contacts;

	public List<ContactDTO> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}

}
