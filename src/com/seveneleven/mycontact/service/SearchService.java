// Provides contact search functionality by name, phone, email, and tag using Java Streams
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.Contact;
import com.seveneleven.mycontact.repository.ContactRepository;
import com.seveneleven.mycontact.session.SessionManager;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SearchService {

    private final ContactRepository contactRepository;

    public SearchService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> searchByName(String query) {
        String q = query.toLowerCase().trim();
        return getMyContacts().stream()
            .filter(c -> c.getDisplayName().toLowerCase().contains(q))
            .collect(Collectors.toList());
    }

    public List<Contact> searchByPhone(String query) {
        String q = query.trim();
        return getMyContacts().stream()
            .filter(c -> c.getPhones().stream()
                .anyMatch(p -> p.getNumber().contains(q)))
            .collect(Collectors.toList());
    }

    public List<Contact> searchByEmail(String query) {
        String q = query.toLowerCase().trim();
        return getMyContacts().stream()
            .filter(c -> c.getEmails().stream()
                .anyMatch(e -> e.getAddress().toLowerCase().contains(q)))
            .collect(Collectors.toList());
    }

    public List<Contact> searchByTag(String tagName) {
        return getMyContacts().stream()
            .filter(c -> c.hasTag(tagName))
            .collect(Collectors.toList());
    }

    private List<Contact> getMyContacts() {
        if (!SessionManager.isLoggedIn())
            throw new IllegalStateException("You must be logged in.");
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        return contactRepository.findByOwner(ownerId);
    }
}
