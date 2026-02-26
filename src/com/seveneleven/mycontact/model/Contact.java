// Abstract base class for contacts - UC7 adds boolean deleted flag, softDelete() and restore() for lifecycle management
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

    // UC7 — lifecycle fields for soft-delete support
    private boolean deleted;
    private LocalDateTime updatedAt;

    public Contact(UUID ownerId) {
        this.contactId  = UUID.randomUUID();
        this.ownerId    = ownerId;
        this.phones     = new ArrayList<>();
        this.emails     = new ArrayList<>();
        this.tags       = new HashSet<>();
        this.createdAt  = LocalDateTime.now();
        this.updatedAt  = LocalDateTime.now();
        this.deleted    = false;
        this.contactCount = 0;
    }

    public abstract String getDisplayName();

    public void addPhone(PhoneNumber phone)   { phones.add(phone);  touch(); }
    public void removePhone(String number)    { phones.removeIf(p -> p.getNumber().equals(number)); touch(); }
    public void addEmail(EmailAddress email)  { emails.add(email);  touch(); }
    public void removeEmail(String address)   { emails.removeIf(e -> e.getAddress().equalsIgnoreCase(address)); touch(); }
    public void addTag(Tag tag)               { tags.add(tag);      touch(); }
    public void removeTag(Tag tag)            { tags.remove(tag);   touch(); }
    public boolean hasTag(String tagName)     { return tags.stream().anyMatch(t -> t.getName().equalsIgnoreCase(tagName)); }

    public void incrementContactCount()       { contactCount++; }
    public int  getContactCount()             { return contactCount; }

    // UC7 — soft-delete marks contact as deleted without removing from storage
    public void softDelete()  { this.deleted = true;  touch(); }
    public void restore()     { this.deleted = false; touch(); }
    public boolean isDeleted(){ return deleted; }

    private void touch()      { this.updatedAt = LocalDateTime.now(); }

    public UUID              getContactId()  { return contactId; }
    public UUID              getOwnerId()    { return ownerId; }
    public List<PhoneNumber> getPhones()     { return Collections.unmodifiableList(phones); }
    public List<EmailAddress> getEmails()    { return Collections.unmodifiableList(emails); }
    public Set<Tag>          getTags()       { return Collections.unmodifiableSet(tags); }
    public LocalDateTime     getCreatedAt()  { return createdAt; }
    public LocalDateTime     getUpdatedAt()  { return updatedAt; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Contact : ").append(getDisplayName()).append("\n");
        sb.append("  ID    : ").append(contactId).append("\n");
        if (!phones.isEmpty()) {
            sb.append("  Phones: ");
            phones.forEach(p -> sb.append(p).append("  "));
            sb.append("\n");
        }
        if (!emails.isEmpty()) {
            sb.append("  Emails: ");
            emails.forEach(e -> sb.append(e).append("  "));
            sb.append("\n");
        }
        if (!tags.isEmpty()) {
            sb.append("  Tags  : ");
            tags.forEach(t -> sb.append(t).append(" "));
            sb.append("\n");
        }
        sb.append("  Added : ").append(createdAt).append("\n");
        sb.append("  Status: ").append(deleted ? "DELETED" : "Active").append("\n");
        return sb.toString();
    }
}
