/**
 * 
 */
package com.cms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cms.model.Contact;

/**
 * @author pankaj rathod
 *
 */
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact>{

}
