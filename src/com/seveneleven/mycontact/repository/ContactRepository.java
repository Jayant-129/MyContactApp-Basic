// In-memory repository for storing and retrieving Contact objects (UC4 - no hardDelete, no deleted filter)
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

    public List<Contact> findByOwner(UUID ownerId) {
        return store.values().stream()
            .filter(c -> c.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
    }

    public List<Contact> findAll() {
        return new ArrayList<>(store.values());
    }
}
