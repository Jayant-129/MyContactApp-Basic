// Top-level console menu routing the user to Contacts, Profile, Groups, and Tags sections
package com.seveneleven.mycontact.app;

import com.seveneleven.mycontact.service.AuthService;
import com.seveneleven.mycontact.session.SessionManager;
import com.seveneleven.mycontact.util.ConsoleHelper;

public class MainMenu {

    private final AuthService  authService;
    private final AuthMenu     authMenu;
    private final ContactMenu  contactMenu;
    private final ProfileMenu  profileMenu;
    private final GroupMenu    groupMenu;
    private final TagMenu      tagMenu;

    public MainMenu(AuthService authService, AuthMenu authMenu,
                    ContactMenu contactMenu, ProfileMenu profileMenu,
                    GroupMenu groupMenu, TagMenu tagMenu) {
        this.authService  = authService;
        this.authMenu     = authMenu;
        this.contactMenu  = contactMenu;
        this.profileMenu  = profileMenu;
        this.groupMenu    = groupMenu;
        this.tagMenu      = tagMenu;
    }

    public void run() {
        System.out.println("\n  Welcome to MyContacts App!");
        while (true) {
            if (!SessionManager.isLoggedIn()) {
                authMenu.show();
                if (!SessionManager.isLoggedIn()) {
                    System.out.println("\n  Goodbye!");
                    break;
                }
            }

            ConsoleHelper.printHeader("Main Menu  [" + SessionManager.getCurrentUser().getName() + "]");
            System.out.println("  1. Contacts");
            System.out.println("  2. Profile");
            System.out.println("  3. Groups");
            System.out.println("  4. Tags");
            System.out.println("  5. Logout");
            System.out.println("  0. Exit");
            ConsoleHelper.printDivider();
            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1: contactMenu.show();  break;
                case 2: profileMenu.show();  break;
                case 3: groupMenu.show();    break;
                case 4: tagMenu.show();      break;
                case 5: authService.logout(); break;
                case 0: System.out.println("\n  Goodbye!"); return;
                default: System.out.println("  Invalid option.");
            }
        }
    }
}
