// Console menu for managing logged-in user's profile details including name, email, and password
package com.seveneleven.mycontact.app;

import com.seveneleven.mycontact.service.ProfileService;
import com.seveneleven.mycontact.util.ConsoleHelper;

public class ProfileMenu {

    private final ProfileService profileService;

    public ProfileMenu(ProfileService profileService) {
        this.profileService = profileService;
    }

    public void show() {
        while (true) {
            ConsoleHelper.printHeader("Profile Management");
            System.out.println("  1. View my profile");
            System.out.println("  2. Update name");
            System.out.println("  3. Update email");
            System.out.println("  4. Change password");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int choice = ConsoleHelper.readInt("Choice: ");
            try {
                switch (choice) {
                    case 1: profileService.showProfile(); break;
                    case 2: {
                        String name = ConsoleHelper.readLine("  New name: ");
                        profileService.updateName(name); break;
                    }
                    case 3: {
                        String email = ConsoleHelper.readLine("  New email: ");
                        profileService.updateEmail(email); break;
                    }
                    case 4: {
                        String old = ConsoleHelper.readLine("  Current password: ");
                        String nw  = ConsoleHelper.readLine("  New password    : ");
                        profileService.changePassword(old, nw); break;
                    }
                    case 0: return;
                    default: System.out.println("  Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }
}
