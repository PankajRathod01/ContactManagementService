/**
 * 
 */
package com.cms.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.cms.model.Contact;

import jakarta.persistence.criteria.Predicate;

/**
 * @author Admin
 *
 */
public class ContactSpecifications {
	
	public static Specification<Contact> searchContacts(String firstName,String lastName,String email){
		Specification<Contact> contactSpecification = (root,query,criteriabuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if(firstName != null && !firstName.isEmpty()) {
				predicates.add(criteriabuilder.like(root.get("firstName"),"%" + firstName + "%"));
			}
			
			if(lastName != null && !lastName.isEmpty()) {
				predicates.add(criteriabuilder.like(root.get("lastName"),"%" + lastName + "%"));
			}
			
			if(email != null && !email.isEmpty()) {
				predicates.add(criteriabuilder.like(root.get("email"),"%" + email + "%"));
			}
			return criteriabuilder.and(predicates.toArray(new Predicate[0]));
		};
		return contactSpecification;
	}

}
