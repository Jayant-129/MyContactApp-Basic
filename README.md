# MyContacts App

## Use Cases

### UC1 — User Registration
- **Classes**: `User` (abstract), `FreeUser`, `PremiumUser`, `UserRole` (enum), `PasswordHasher`, `InputValidator`, `UserRepository`
- **OOP**: Encapsulation (private fields + getters/setters), Inheritance (`FreeUser`/`PremiumUser` extend `User`), Abstraction (`getUserType()`)
- **Java**: `UUID`, `MessageDigest` (SHA-256), regex validation, `HashMap` in-memory store

### UC2 — User Authentication
- **Classes**: `SessionManager`, `AuthService`
- **OOP**: Polymorphism (returns abstract `User`), session state encapsulation
- **Java**: `Optional`, `static` session field, password hash comparison

### UC3 — Profile Management
- **Classes**: `ProfileService`
- **OOP**: Setter methods with validation, encapsulated session guard
- **Java**: Input validation, password re-verification before change

### UC4 — Create Contacts
- **Classes**: `Contact` (abstract), `PersonContact`, `OrganizationContact`, `PhoneNumber`, `EmailAddress`, `Tag`, `ContactRepository`, `ContactService`
- **OOP**: Abstract class hierarchy, Composition (`Contact` has `List<PhoneNumber>` + `List<EmailAddress>` + `Set<Tag>`), Polymorphism (`getDisplayName()`)
- **Java**: `UUID`, `LocalDateTime`, `Collections.unmodifiableList`, `equals()`/`hashCode()` on `Tag`
