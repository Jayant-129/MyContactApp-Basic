// Console menu for creating and managing contact groups and performing bulk operations
package com.seveneleven.mycontact.app;

import com.seveneleven.mycontact.model.Tag;
import com.seveneleven.mycontact.service.GroupService;
import com.seveneleven.mycontact.util.ConsoleHelper;

import java.util.UUID;

public class GroupMenu {

    private final GroupService groupService;

    public GroupMenu(GroupService groupService) {
        this.groupService = groupService;
    }

    public void show() {
        while (true) {
            ConsoleHelper.printHeader("Groups");
            System.out.println("  1. List my groups");
            System.out.println("  2. Create group");
            System.out.println("  3. View group members");
            System.out.println("  4. Add contact to group");
            System.out.println("  5. Remove contact from group");
            System.out.println("  6. Bulk tag all members");
            System.out.println("  7. Bulk soft-delete all members");
            System.out.println("  8. Delete group");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int choice = ConsoleHelper.readInt("Choice: ");
            try {
                switch (choice) {
                    case 1: groupService.listGroups(); break;
                    case 2: {
                        String name = ConsoleHelper.readLine("  Group name: ");
                        groupService.createGroup(name); break;
                    }
                    case 3: {
                        UUID gid = parseUUID("  Group ID: ");
                        groupService.showGroup(gid); break;
                    }
                    case 4: {
                        UUID gid = parseUUID("  Group ID  : ");
                        UUID cid = parseUUID("  Contact ID: ");
                        groupService.addContactToGroup(gid, cid); break;
                    }
                    case 5: {
                        UUID gid = parseUUID("  Group ID  : ");
                        UUID cid = parseUUID("  Contact ID: ");
                        groupService.removeContactFromGroup(gid, cid); break;
                    }
                    case 6: {
                        UUID gid     = parseUUID("  Group ID : ");
                        String label = ConsoleHelper.readLine("  Tag name  : ");
                        groupService.bulkTag(gid, new Tag(label)); break;
                    }
                    case 7: {
                        UUID gid = parseUUID("  Group ID: ");
                        if (ConsoleHelper.confirm("  Soft-delete all members?"))
                            groupService.bulkSoftDelete(gid); break;
                    }
                    case 8: {
                        UUID gid = parseUUID("  Group ID: ");
                        if (ConsoleHelper.confirm("  Delete this group?"))
                            groupService.deleteGroup(gid); break;
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
