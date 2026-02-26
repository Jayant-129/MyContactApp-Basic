// Immutable value object representing a phone number with a label (Home, Work, Mobile)
package com.seveneleven.mycontact.model;

import com.seveneleven.mycontact.util.InputValidator;

public class PhoneNumber {

    private final String number;
    private final String label;

    public PhoneNumber(String number, String label) {
        if (!InputValidator.isValidPhone(number))
            throw new IllegalArgumentException("Invalid phone number: " + number);
        this.number = number.trim();
        this.label  = (label == null || label.trim().isEmpty()) ? "Mobile" : label.trim();
    }

    public String getNumber() { return number; }
    public String getLabel()  { return label; }

    @Override
    public String toString() {
        return label + ": " + number;
    }
}
