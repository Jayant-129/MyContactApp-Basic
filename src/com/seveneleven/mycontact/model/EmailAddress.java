// Immutable value object representing an email address with a label (Personal, Work)
package com.seveneleven.mycontact.model;

import com.seveneleven.mycontact.util.InputValidator;

public class EmailAddress {

    private final String address;
    private final String label;

    public EmailAddress(String address, String label) {
        if (!InputValidator.isValidEmail(address))
            throw new IllegalArgumentException("Invalid email address: " + address);
        this.address = address.trim();
        this.label   = (label == null || label.trim().isEmpty()) ? "Personal" : label.trim();
    }

    public String getAddress() { return address; }
    public String getLabel()   { return label; }

    @Override
    public String toString() {
        return label + ": " + address;
    }
}
