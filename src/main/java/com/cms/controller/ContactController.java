/**
 * 
 */
package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.ContactDTO;
import com.cms.dto.ContactResponse;
import com.cms.service.ContactService;

/**
 * @author pankaj rathod
 *
 */

@RestController
@RequestMapping("/v1/contact")
public class ContactController {

	@Autowired
	private ContactService contactService;

	@PostMapping
	public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactRequest) {

		ContactDTO contactDto = contactService.craeteContact(contactRequest);

		return new ResponseEntity<ContactDTO>(contactDto, HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactRequest) {

		ContactDTO contactDto = contactService.updateContact(id, contactRequest);

		return new ResponseEntity<ContactDTO>(contactDto, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<ContactDTO> getContact(@PathVariable Long id) {

		ContactDTO contactDto = contactService.getContact(id);

		return new ResponseEntity<ContactDTO>(contactDto, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<ContactResponse> getContacts(@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "email", required = false) String email) {
		
		ContactResponse resp=contactService.getContacts(firstName, lastName, email);

		return new ResponseEntity<>(resp, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable Long id) {

		contactService.deleteContact(id);

		return ResponseEntity.accepted().build();

	}

}
