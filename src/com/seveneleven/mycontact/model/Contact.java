// Abstract base class representing a contact with phones, emails, tags, and owner info (UC4 - create only)
package com.seveneleven.mycontact.model;

import java.time.LocalDateTime;
import java.util.*;

public abstract class Contact {

    private final UUID contactId;
    private final UUID ownerId;
    private List<PhoneNumber> phones;
    private List<EmailAddress> emails;
    private Set<Tag> tags;
    private final LocalDateTime createdAt;
    private int contactCount;

    public Contact(UUID ownerId) {
        this.contactId    = UUID.randomUUID();
        this.ownerId      = ownerId;
        this.phones       = new ArrayList<>();
        this.emails       = new ArrayList<>();
        this.tags         = new HashSet<>();
        this.createdAt    = LocalDateTime.now();
        this.contactCount = 0;
    }

    public abstract String getDisplayName();

    public void addPhone(PhoneNumber phone)   { phones.add(phone); }
    public void removePhone(String number)    { phones.removeIf(p -> p.getNumber().equals(number)); }
    public void addEmail(EmailAddress email)  { emails.add(email); }
    public void removeEmail(String address)   { emails.removeIf(e -> e.getAddress().equalsIgnoreCase(address)); }
    public void addTag(Tag tag)               { tags.add(tag); }
    public void removeTag(Tag tag)            { tags.remove(tag); }
    public boolean hasTag(String tagName)     { return tags.stream().anyMatch(t -> t.getName().equalsIgnoreCase(tagName)); }

    public void incrementContactCount()       { contactCount++; }
    public int  getContactCount()             { return contactCount; }

    public UUID             getContactId()  { return contactId; }
    public UUID             getOwnerId()    { return ownerId; }
    public List<PhoneNumber> getPhones()    { return Collections.unmodifiableList(phones); }
    public List<EmailAddress> getEmails()   { return Collections.unmodifiableList(emails); }
    public Set<Tag>          getTags()      { return Collections.unmodifiableSet(tags); }
    public LocalDateTime     getCreatedAt() { return createdAt; }
}
