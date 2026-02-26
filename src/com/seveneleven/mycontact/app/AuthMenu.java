// Console menu handling user registration, login, and logout interactions
package com.seveneleven.mycontact.app;

import com.seveneleven.mycontact.service.AuthService;
import com.seveneleven.mycontact.util.ConsoleHelper;

public class AuthMenu {

    private final AuthService authService;

    public AuthMenu(AuthService authService) {
        this.authService = authService;
    }

    public void show() {
        while (true) {
            ConsoleHelper.printHeader("Authentication");
            System.out.println("  1. Register");
            System.out.println("  2. Login");
            System.out.println("  0. Back");
            ConsoleHelper.printDivider();
            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1: doRegister(); break;
                case 2: { if (doLogin()) return; break; }
                case 0: return;
                default: System.out.println("  Invalid option.");
            }
        }
    }

    private void doRegister() {
        ConsoleHelper.printHeader("Register New User");
        String name  = ConsoleHelper.readLine("  Full name     : ");
        String email = ConsoleHelper.readLine("  Email         : ");
        String pwd   = ConsoleHelper.readLine("  Password      : ");
        String type  = ConsoleHelper.readLine("  Account type (free/premium): ");
        try {
            authService.register(name, email, pwd, type);
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private boolean doLogin() {
        ConsoleHelper.printHeader("Login");
        String email = ConsoleHelper.readLine("  Email   : ");
        String pwd   = ConsoleHelper.readLine("  Password: ");
        try {
            return authService.login(email, pwd);
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
            return false;
        }
    }
}
