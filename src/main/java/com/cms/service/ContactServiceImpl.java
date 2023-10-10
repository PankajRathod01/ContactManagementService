/**
 * 
 */
package com.cms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cms.dto.ContactDTO;
import com.cms.dto.ContactResponse;
import com.cms.exceptions.BadRequestException;
import com.cms.exceptions.ResourceNotFoundException;
import com.cms.model.Contact;
import com.cms.repo.ContactRepository;
import com.cms.repo.ContactSpecifications;
import com.cms.service.utils.ErrorMessagesConstant;

/**
 * @author pankaj rathod
 *
 */
@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepo;

	@Override
	public ContactDTO craeteContact(ContactDTO contactRequest) {
		if (contactRequest == null || contactRequest.getFirstName() == null || contactRequest.getLastName() == null
				|| contactRequest.getEmail() == null || contactRequest.getPhoneNumber() == null) {
			throw new BadRequestException(ErrorMessagesConstant.BAD_REQUEST_MSG);
		}
		if(contactRepo.existsByPhoneNumber(contactRequest.getPhoneNumber())) {
			throw new BadRequestException(ErrorMessagesConstant.BAD_REQUEST_CONATCT_ALLREADY_EXIST);
		}
		
		Contact contact = convertToContactEntity(contactRequest);

		Contact createdContact = contactRepo.save(contact);
		

		return convertEntityToDto(createdContact);
	}

	@Override
	public ContactDTO updateContact(Long id, ContactDTO contactRequest) {
		Optional<Contact> contactOptional = contactRepo.findById(id);
		if (!contactOptional.isPresent()) {
			throw new ResourceNotFoundException(ErrorMessagesConstant.RESOURCE_NOT_FOUND_MSG + id);
		}
		Contact contact = contactOptional.get();
		updateContact(contactRequest, contact);
		Contact updatedcontact = contactRepo.save(contact);
		return convertEntityToDto(updatedcontact);

	}

	/**
	 * @param contactRequest
	 * @param contact
	 */
	private void updateContact(ContactDTO contactRequest, Contact contact) {
		if (contactRequest.getEmail() != null) {
			contact.setEmail(contactRequest.getEmail());
		}

		if (contactRequest.getFirstName() != null) {
			contact.setFirstName(contactRequest.getFirstName());
		}
		if (contactRequest.getLastName() != null) {
			contact.setLastName(contactRequest.getLastName());
		}

		if (contactRequest.getPhoneNumber() != null) {
			contact.setPhoneNumber(contactRequest.getPhoneNumber());
		}
	}

	@Override
	public ContactDTO getContact(Long id) {
		Optional<Contact> contactOptional = contactRepo.findById(id);
		if (!contactOptional.isPresent()) {
			throw new ResourceNotFoundException(ErrorMessagesConstant.RESOURCE_NOT_FOUND_MSG + id);
		}
		return convertEntityToDto(contactOptional.get());
	}

	@Override
	public void deleteContact(Long id) {
		contactRepo.deleteById(id);
	}

	@Override
	public ContactResponse getContacts(String firstName, String lastName, String email) {
		ContactResponse resp = new ContactResponse();
		Specification<Contact> contactSpec = ContactSpecifications.searchContacts(firstName, lastName, email);

		List<Contact> listContact = contactRepo.findAll(contactSpec);
		List<ContactDTO> contactsList= listContact.stream().map(this::convertEntityToDto).collect(Collectors.toList());
		resp.setContacts(contactsList);
		return resp;
	}

	/**
	 * @param contactDto
	 * @return
	 */
	private Contact convertToContactEntity(ContactDTO contactDto) {
		Contact contact = new Contact();
		contact.setEmail(contactDto.getEmail());
		contact.setPhoneNumber(contactDto.getPhoneNumber());
		contact.setFirstName(contactDto.getFirstName());
		contact.setLastName(contactDto.getLastName());
		return contact;
	}

	/**
	 * @param contact
	 * @return
	 */
	private ContactDTO convertEntityToDto(Contact contact) {
		ContactDTO contactDto = new ContactDTO();
		contactDto.setEmail(contact.getEmail());
		contactDto.setPhoneNumber(contact.getPhoneNumber());
		contactDto.setFirstName(contact.getFirstName());
		contactDto.setLastName(contact.getLastName());
		contactDto.setId(contact.getId());
		return contactDto;
	}
}
