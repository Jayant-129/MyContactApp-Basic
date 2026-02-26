// Represents an organization contact with company name and website (UC4 - create only, no setters)
package com.seveneleven.mycontact.model;

import java.util.UUID;

public class OrganizationContact extends Contact {

    private String companyName;
    private String website;

    public OrganizationContact(UUID ownerId, String companyName) {
        super(ownerId);
        if (companyName == null || companyName.trim().isEmpty())
            throw new IllegalArgumentException("Company name cannot be empty.");
        this.companyName = companyName.trim();
    }

    @Override
    public String getDisplayName() { return companyName; }

    public String getCompanyName() { return companyName; }
    public String getWebsite()     { return website; }
}
