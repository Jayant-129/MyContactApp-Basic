// Provides business logic for contacts - UC7 adds softDeleteContact() and hardDeleteContact()
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.*;
import com.seveneleven.mycontact.repository.ContactRepository;
import com.seveneleven.mycontact.session.SessionManager;

import java.time.LocalDate;
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

    public void displayContact(UUID contactId) {
        System.out.println(getOwned(contactId));
    }

    public void editPersonName(UUID contactId, String firstName, String lastName) {
        Contact c = getOwned(contactId);
        if (!(c instanceof PersonContact))
            throw new IllegalArgumentException("Not a person contact.");
        PersonContact p = (PersonContact) c;
        p.setFirstName(firstName);
        p.setLastName(lastName);
        contactRepository.save(p);
        System.out.println("Name updated: " + p.getDisplayName());
    }

    public void editPersonBirthday(UUID contactId, LocalDate birthday) {
        Contact c = getOwned(contactId);
        if (!(c instanceof PersonContact))
            throw new IllegalArgumentException("Not a person contact.");
        ((PersonContact) c).setBirthday(birthday);
        contactRepository.save(c);
        System.out.println("Birthday updated.");
    }

    public void editOrgName(UUID contactId, String companyName) {
        Contact c = getOwned(contactId);
        if (!(c instanceof OrganizationContact))
            throw new IllegalArgumentException("Not an organisation contact.");
        ((OrganizationContact) c).setCompanyName(companyName);
        contactRepository.save(c);
        System.out.println("Company name updated: " + companyName);
    }

    public void editOrgWebsite(UUID contactId, String website) {
        Contact c = getOwned(contactId);
        if (!(c instanceof OrganizationContact))
            throw new IllegalArgumentException("Not an organisation contact.");
        ((OrganizationContact) c).setWebsite(website);
        contactRepository.save(c);
        System.out.println("Website updated: " + website);
    }

    public void removePhone(UUID contactId, String number) {
        Contact c = getOwned(contactId);
        c.removePhone(number);
        contactRepository.save(c);
        System.out.println("Phone removed.");
    }

    public void removeEmail(UUID contactId, String address) {
        Contact c = getOwned(contactId);
        c.removeEmail(address);
        contactRepository.save(c);
        System.out.println("Email removed.");
    }

    // UC7 — marks contact as deleted (hidden from listings) without removing from storage
    public void softDeleteContact(UUID contactId) {
        Contact c = getOwned(contactId);
        c.softDelete();
        contactRepository.save(c);
        System.out.println("Contact soft-deleted: " + c.getDisplayName());
    }

    // UC7 — permanently removes the contact record from storage
    public void hardDeleteContact(UUID contactId) {
        getOwned(contactId);
        contactRepository.hardDelete(contactId);
        System.out.println("Contact permanently deleted.");
    }

    public void recordContactInteraction(UUID contactId) {
        Contact c = getOwned(contactId);
        c.incrementContactCount();
        contactRepository.save(c);
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
