// Console menu for creating, listing, applying, and removing tags from contacts
package com.seveneleven.mycontact.app;

import com.seveneleven.mycontact.service.TagService;
import com.seveneleven.mycontact.util.ConsoleHelper;

import java.util.UUID;

public class TagMenu {

    private final TagService tagService;

    public TagMenu(TagService tagService) {
        this.tagService = tagService;
    }

    public void show() {
        while (true) {
            ConsoleHelper.printHeader("Tags");
            System.out.println("  1. List all tags");
            System.out.println("  2. Create tag");
            System.out.println("  3. Apply tag to contact");
            System.out.println("  4. Remove tag from contact");
            System.out.println("  5. Delete tag");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int choice = ConsoleHelper.readInt("Choice: ");
            try {
                switch (choice) {
                    case 1: tagService.listAllTags(); break;
                    case 2: {
                        String name = ConsoleHelper.readLine("  Tag name: ");
                        tagService.createTag(name); break;
                    }
                    case 3: {
                        String name = ConsoleHelper.readLine("  Tag name  : ");
                        UUID   cid  = parseUUID("  Contact ID: ");
                        tagService.applyTagToContact(name, cid); break;
                    }
                    case 4: {
                        String name = ConsoleHelper.readLine("  Tag name  : ");
                        UUID   cid  = parseUUID("  Contact ID: ");
                        tagService.removeTagFromContact(name, cid); break;
                    }
                    case 5: {
                        String name = ConsoleHelper.readLine("  Tag name: ");
                        if (ConsoleHelper.confirm("  Delete tag '" + name + "'?"))
                            tagService.deleteTag(name); break;
                    }
                    case 0: return;
                    default: System.out.println("  Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
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
