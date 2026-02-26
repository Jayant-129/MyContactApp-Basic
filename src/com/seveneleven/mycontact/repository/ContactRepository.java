// In-memory repository for contacts - UC7 adds hardDelete() and filters soft-deleted contacts in findByOwner
package com.seveneleven.mycontact.repository;

import com.seveneleven.mycontact.model.Contact;

import java.util.*;
import java.util.stream.Collectors;

public class ContactRepository {

    private final Map<UUID, Contact> store = new HashMap<>();

    public void save(Contact contact) {
        store.put(contact.getContactId(), contact);
    }

    public Optional<Contact> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    // UC7 — excludes soft-deleted contacts from normal listing
    public List<Contact> findByOwner(UUID ownerId) {
        return store.values().stream()
            .filter(c -> c.getOwnerId().equals(ownerId) && !c.isDeleted())
            .collect(Collectors.toList());
    }

    // UC7 — permanently removes a contact from storage (hard delete)
    public void hardDelete(UUID id) {
        store.remove(id);
    }

    public List<Contact> findAll() {
        return new ArrayList<>(store.values());
    }
}
