// Console menu for creating, viewing, editing, deleting, searching, filtering, and sorting contacts
package com.seveneleven.mycontact.app;

import com.seveneleven.mycontact.model.Contact;
import com.seveneleven.mycontact.model.OrganizationContact;
import com.seveneleven.mycontact.model.PersonContact;
import com.seveneleven.mycontact.service.ContactService;
import com.seveneleven.mycontact.service.FilterSortService;
import com.seveneleven.mycontact.service.SearchService;
import com.seveneleven.mycontact.util.ConsoleHelper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

public class ContactMenu {

    private final ContactService contactService;
    private final SearchService searchService;
    private final FilterSortService filterSortService;

    public ContactMenu(ContactService contactService, SearchService searchService,
                       FilterSortService filterSortService) {
        this.contactService    = contactService;
        this.searchService     = searchService;
        this.filterSortService = filterSortService;
    }

    public void show() {
        while (true) {
            ConsoleHelper.printHeader("Contacts");
            System.out.println("  1.  List all contacts");
            System.out.println("  2.  Create Person contact");
            System.out.println("  3.  Create Organization contact");
            System.out.println("  4.  View contact details");
            System.out.println("  5.  Edit contact");
            System.out.println("  6.  Add phone to contact");
            System.out.println("  7.  Add email to contact");
            System.out.println("  8.  Soft-delete contact");
            System.out.println("  9.  Hard-delete contact");
            System.out.println("  10. Search");
            System.out.println("  11. Filter & Sort");
            System.out.println("  0.  Back");
            ConsoleHelper.printDivider();
            int choice = ConsoleHelper.readInt("Choice: ");
            try {
                switch (choice) {
                    case 1:  listContacts(); break;
                    case 2:  createPerson(); break;
                    case 3:  createOrg(); break;
                    case 4:  viewContact(); break;
                    case 5:  editContact(); break;
                    case 6:  addPhone(); break;
                    case 7:  addEmail(); break;
                    case 8:  softDelete(); break;
                    case 9:  hardDelete(); break;
                    case 10: searchMenu(); break;
                    case 11: filterSortMenu(); break;
                    case 0:  return;
                    default: System.out.println("  Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }

    private void listContacts() {
        List<Contact> contacts = contactService.listMyContacts();
        if (contacts.isEmpty()) {
            System.out.println("  No contacts found.");
        } else {
            contacts.forEach(c -> System.out.println("  [" + c.getContactId() + "] " + c.getDisplayName()));
        }
    }

    private void createPerson() {
        String first = ConsoleHelper.readLine("  First name: ");
        String last  = ConsoleHelper.readLine("  Last name : ");
        PersonContact p = contactService.createPerson(first, last);
        System.out.println("  Created: " + p.getDisplayName() + " | ID: " + p.getContactId());
    }

    private void createOrg() {
        String company = ConsoleHelper.readLine("  Company name: ");
        OrganizationContact o = contactService.createOrganization(company);
        System.out.println("  Created: " + o.getDisplayName() + " | ID: " + o.getContactId());
    }

    private void viewContact() {
        UUID id = parseUUID("  Contact ID: ");
        contactService.displayContact(id);
    }

    private void editContact() {
        UUID id = parseUUID("  Contact ID: ");
        Contact c = contactService.getOwned(id);
        if (c instanceof PersonContact) {
            String first = ConsoleHelper.readLine("  New first name (enter to skip): ");
            String last  = ConsoleHelper.readLine("  New last name  (enter to skip): ");
            if (!first.isEmpty() || !last.isEmpty()) {
                PersonContact p = (PersonContact) c;
                contactService.editPersonName(id,
                    first.isEmpty() ? p.getFirstName() : first,
                    last.isEmpty()  ? p.getLastName()  : last);
            }
            String bday = ConsoleHelper.readLine("  Birthday (yyyy-MM-dd, enter to skip): ");
            if (!bday.isEmpty()) {
                try {
                    contactService.editPersonBirthday(id, LocalDate.parse(bday));
                } catch (DateTimeParseException ex) {
                    System.out.println("  Invalid date format.");
                }
            }
        } else if (c instanceof OrganizationContact) {
            String name = ConsoleHelper.readLine("  New company name (enter to skip): ");
            if (!name.isEmpty()) contactService.editOrgName(id, name);
            String web = ConsoleHelper.readLine("  New website (enter to skip): ");
            if (!web.isEmpty()) contactService.editOrgWebsite(id, web);
        }
    }

    private void addPhone() {
        UUID   id     = parseUUID("  Contact ID : ");
        String number = ConsoleHelper.readLine("  Phone number: ");
        String label  = ConsoleHelper.readLine("  Label (Home/Work/Mobile): ");
        contactService.addPhone(id, number, label);
    }

    private void addEmail() {
        UUID   id      = parseUUID("  Contact ID: ");
        String address = ConsoleHelper.readLine("  Email address: ");
        String label   = ConsoleHelper.readLine("  Label (Personal/Work): ");
        contactService.addEmail(id, address, label);
    }

    private void softDelete() {
        UUID id = parseUUID("  Contact ID: ");
        if (ConsoleHelper.confirm("  Soft-delete this contact?"))
            contactService.softDeleteContact(id);
    }

    private void hardDelete() {
        UUID id = parseUUID("  Contact ID: ");
        if (ConsoleHelper.confirm("  Permanently delete this contact?"))
            contactService.hardDeleteContact(id);
    }

    private void searchMenu() {
        ConsoleHelper.printHeader("Search Contacts");
        System.out.println("  1. By name");
        System.out.println("  2. By phone");
        System.out.println("  3. By email");
        System.out.println("  4. By tag");
        int choice = ConsoleHelper.readInt("Choice: ");
        String query = ConsoleHelper.readLine("  Query: ");
        List<Contact> results;
        switch (choice) {
            case 1: results = searchService.searchByName(query);  break;
            case 2: results = searchService.searchByPhone(query); break;
            case 3: results = searchService.searchByEmail(query); break;
            case 4: results = searchService.searchByTag(query);   break;
            default: System.out.println("  Invalid option."); return;
        }
        printResults(results);
    }

    private void filterSortMenu() {
        ConsoleHelper.printHeader("Filter & Sort");
        System.out.println("  1. Filter by tag");
        System.out.println("  2. Filter frequently contacted (min count)");
        System.out.println("  3. Sort by name (A→Z)");
        System.out.println("  4. Sort by name (Z→A)");
        System.out.println("  5. Sort by date added (newest first)");
        System.out.println("  6. Sort by date added (oldest first)");
        int choice = ConsoleHelper.readInt("Choice: ");
        List<Contact> results;
        switch (choice) {
            case 1: {
                String tag = ConsoleHelper.readLine("  Tag name: ");
                results = filterSortService.filterByTag(tag); break;
            }
            case 2: {
                int min = ConsoleHelper.readInt("  Min contact count: ");
                results = filterSortService.filterFrequentlyContacted(min); break;
            }
            case 3: results = filterSortService.sortByName(true);        break;
            case 4: results = filterSortService.sortByName(false);       break;
            case 5: results = filterSortService.sortByDateAdded(false);  break;
            case 6: results = filterSortService.sortByDateAdded(true);   break;
            default: System.out.println("  Invalid option."); return;
        }
        printResults(results);
    }

    private void printResults(List<Contact> results) {
        if (results.isEmpty()) {
            System.out.println("  No results found.");
        } else {
            results.forEach(c -> System.out.println("  [" + c.getContactId() + "] " + c.getDisplayName()));
        }
    }

    private UUID parseUUID(String prompt) {
        try {
            return UUID.fromString(ConsoleHelper.readLine(prompt));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format.");
        }
    }
}
