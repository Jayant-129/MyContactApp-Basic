// Represents a personal contact with first name, last name, and birthday (UC4 - create only, no setters)
package com.seveneleven.mycontact.model;

import java.time.LocalDate;
import java.util.UUID;

public class PersonContact extends Contact {

    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public PersonContact(UUID ownerId, String firstName, String lastName) {
        super(ownerId);
        if (firstName == null || firstName.trim().isEmpty())
            throw new IllegalArgumentException("First name cannot be empty.");
        this.firstName = firstName.trim();
        this.lastName  = (lastName == null) ? "" : lastName.trim();
    }

    @Override
    public String getDisplayName() {
        return firstName + (lastName.isEmpty() ? "" : " " + lastName);
    }

    public String    getFirstName() { return firstName; }
    public String    getLastName()  { return lastName; }
    public LocalDate getBirthday()  { return birthday; }
}
