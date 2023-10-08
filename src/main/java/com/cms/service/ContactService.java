/**
 * 
 */
package com.cms.service;

import java.util.List;

import com.cms.dto.ContactDTO;
import com.cms.dto.ContactResponse;

/**
 * @author pankaj rathod
 *
 */
public interface ContactService {
	
	public ContactDTO craeteContact(ContactDTO contactRequest);
	
	public ContactDTO updateContact(Long id ,ContactDTO contactRequest);
	
	public ContactDTO getContact(Long id);
	
	public  void deleteContact(Long id);
	
	public ContactResponse getContacts(String firstName,String lastName,String email);

}
