// Main entry point that bootstraps all repositories, services, and menus for the MyContacts console app
package com.seveneleven.mycontact.app;

import com.seveneleven.mycontact.repository.*;
import com.seveneleven.mycontact.service.*;

public class ContactsApp {

    public static void main(String[] args) {

        // Repositories
        UserRepository    userRepo    = new UserRepository();
        ContactRepository contactRepo = new ContactRepository();
        GroupRepository   groupRepo   = new GroupRepository();
        TagRepository     tagRepo     = new TagRepository();

        // Services
        AuthService       authService       = new AuthService(userRepo);
        ProfileService    profileService    = new ProfileService(userRepo);
        ContactService    contactService    = new ContactService(contactRepo);
        GroupService      groupService      = new GroupService(groupRepo, contactRepo);
        TagService        tagService        = new TagService(tagRepo, contactRepo);
        SearchService     searchService     = new SearchService(contactRepo);
        FilterSortService filterSortService = new FilterSortService(contactRepo);

        // Menus
        AuthMenu    authMenu    = new AuthMenu(authService);
        ProfileMenu profileMenu = new ProfileMenu(profileService);
        ContactMenu contactMenu = new ContactMenu(contactService, searchService, filterSortService);
        GroupMenu   groupMenu   = new GroupMenu(groupService);
        TagMenu     tagMenu     = new TagMenu(tagService);
        MainMenu    mainMenu    = new MainMenu(authService, authMenu, contactMenu,
                                              profileMenu, groupMenu, tagMenu);

        // Launch
        mainMenu.run();
    }
}
