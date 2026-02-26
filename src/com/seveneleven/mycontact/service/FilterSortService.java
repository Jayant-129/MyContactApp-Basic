// Provides filtering and sorting operations on the user's contact list using Streams and Comparators
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.Contact;
import com.seveneleven.mycontact.repository.ContactRepository;
import com.seveneleven.mycontact.session.SessionManager;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FilterSortService {

    private final ContactRepository contactRepository;

    public FilterSortService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // --- Filters ---

    public List<Contact> filterByTag(String tagName) {
        return getMyContacts().stream()
            .filter(c -> c.hasTag(tagName))
            .collect(Collectors.toList());
    }

    public List<Contact> filterByDateRange(LocalDateTime from, LocalDateTime to) {
        return getMyContacts().stream()
            .filter(c -> !c.getCreatedAt().isBefore(from) && !c.getCreatedAt().isAfter(to))
            .collect(Collectors.toList());
    }

    public List<Contact> filterFrequentlyContacted(int minCount) {
        return getMyContacts().stream()
            .filter(c -> c.getContactCount() >= minCount)
            .collect(Collectors.toList());
    }

    // --- Sorts ---

    public List<Contact> sortByName(boolean ascending) {
        Comparator<Contact> cmp = Comparator.comparing(c -> c.getDisplayName().toLowerCase());
        return getMyContacts().stream()
            .sorted(ascending ? cmp : cmp.reversed())
            .collect(Collectors.toList());
    }

    public List<Contact> sortByDateAdded(boolean ascending) {
        Comparator<Contact> cmp = Comparator.comparing(Contact::getCreatedAt);
        return getMyContacts().stream()
            .sorted(ascending ? cmp : cmp.reversed())
            .collect(Collectors.toList());
    }

    public List<Contact> sortByFrequency(boolean ascending) {
        Comparator<Contact> cmp = Comparator.comparingInt(Contact::getContactCount);
        return getMyContacts().stream()
            .sorted(ascending ? cmp : cmp.reversed())
            .collect(Collectors.toList());
    }

    private List<Contact> getMyContacts() {
        if (!SessionManager.isLoggedIn())
            throw new IllegalStateException("You must be logged in.");
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        return contactRepository.findByOwner(ownerId);
    }
}
