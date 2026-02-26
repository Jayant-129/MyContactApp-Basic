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

### UC5 — View Contact Details
- **Classes**: `Contact.toString()`, `PersonContact.toString()`, `OrganizationContact.toString()`, `ContactService.displayContact()`
- **OOP**: Method overriding for formatted display, polymorphic `toString()`
- **Java**: `StringBuilder`, `String.format`, chained `super.toString()`

### UC6 — Edit Contacts
- **Classes**: `PersonContact` setters, `OrganizationContact` setters, `ContactService.editPerson*()`, `ContactService.editOrg*()`
- **OOP**: Setter methods with validation, `instanceof` type check before cast
- **Java**: `LocalDate.parse()`, defensive validation before state mutation

### UC7 — Delete Contacts
- **Classes**: `Contact.softDelete()`, `ContactRepository.hardDelete()`, `ContactService.softDeleteContact()`, `ContactService.hardDeleteContact()`
- **OOP**: Lifecycle state managed via `boolean deleted` flag, encapsulated delete logic
- **Java**: `HashMap.remove()` for hard delete, `isDeleted()` filter in repository queries
