// Provides business logic for creating contacts and managing phones, emails (UC4 - create only)
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.*;
import com.seveneleven.mycontact.repository.ContactRepository;
import com.seveneleven.mycontact.session.SessionManager;

import java.util.List;
import java.util.UUID;

public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public PersonContact createPerson(String firstName, String lastName) {
        requireSession();
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        PersonContact c = new PersonContact(ownerId, firstName, lastName);
        contactRepository.save(c);
        System.out.println("Person contact created: " + c.getDisplayName());
        return c;
    }

    public OrganizationContact createOrganization(String companyName) {
        requireSession();
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        OrganizationContact c = new OrganizationContact(ownerId, companyName);
        contactRepository.save(c);
        System.out.println("Organisation contact created: " + c.getDisplayName());
        return c;
    }

    public void addPhone(UUID contactId, String number, String label) {
        Contact c = getOwned(contactId);
        c.addPhone(new PhoneNumber(number, label));
        contactRepository.save(c);
        System.out.println("Phone added to " + c.getDisplayName());
    }

    public void addEmail(UUID contactId, String address, String label) {
        Contact c = getOwned(contactId);
        c.addEmail(new EmailAddress(address, label));
        contactRepository.save(c);
        System.out.println("Email added to " + c.getDisplayName());
    }

    public List<Contact> listMyContacts() {
        requireSession();
        return contactRepository.findByOwner(SessionManager.getCurrentUser().getUserId());
    }

    public Contact getOwned(UUID contactId) {
        requireSession();
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        Contact c = contactRepository.findById(contactId)
            .orElseThrow(() -> new IllegalArgumentException("Contact not found: " + contactId));
        if (!c.getOwnerId().equals(ownerId))
            throw new IllegalStateException("Access denied to this contact.");
        return c;
    }

    private void requireSession() {
        if (!SessionManager.isLoggedIn())
            throw new IllegalStateException("You must be logged in.");
    }
}
