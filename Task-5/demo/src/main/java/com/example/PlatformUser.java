package com.example;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "platform_users",
        // Ensure email is unique across the table
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class PlatformUser {

    // 1. Primary Key and Generation Strategy
    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses database auto-increment
    @Column(name = "user_id")
    private Long id;

    // 2. Simple Field with Constraints
    @Column(name = "first_name", nullable = false, length = 75) // Not Null constraint
    private String firstName;

    // 3. Sensitive/Masked Field
    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    // 4. Unique and Indexed Field
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // 5. Temporal Field (Date)
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // 6. Temporal Field (Timestamp)
    @Column(name = "created_at", updatable = false) // Field value shouldn't be updated after creation
    private LocalDateTime createdAt = LocalDateTime.now();

    // 7. Enumerated Type Field
    @Enumerated(EnumType.STRING) // Stores the enum's name (e.g., "STUDENT")
    @Column(name = "user_role", nullable = false)
    private UserRole role;

    // 8. Large Text Field (User Bio/Notes)
    @Lob // Maps to a large text/CLOB column type
    @Column(name = "bio_notes")
    private String notes;

    // 9. Simple Boolean Field with Default Value
    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE") // Can set a default at the schema level
    private Boolean isActive = true;

    // 10. Embedded Object Field (Address/Location Details)
    @Embedded // Embeds the fields of the LocationDetails class into the 'platform_users' table
    private LocationDetails address;

    // 11. Relationship Field (Many-to-One) - E.g., a student belongs to one main class/cohort
    @ManyToOne(fetch = FetchType.LAZY) // Loads the related entity only when explicitly accessed
    @JoinColumn(name = "cohort_id", nullable = true) // Specifies the foreign key column
    private Cohort mainCohort; // Assuming a separate Cohort entity

    // 12. Relationship Field (One-to-Many) - E.g., a teacher manages multiple courses
    // Use @Transient if a field is not mapped to a column, but here we need 12 mapped fields.
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE) // If the teacher is removed, related courses are also removed (CASCADE)
    private List<Course> managedCourses; // Assuming a separate Course entity

    // Standard getters/setters/constructors omitted for brevity...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocationDetails getAddress() {
        return address;
    }

    public void setAddress(LocationDetails address) {
        this.address = address;
    }

    public Cohort getMainCohort() {
        return mainCohort;
    }

    public void setMainCohort(Cohort mainCohort) {
        this.mainCohort = mainCohort;
    }

    public List<Course> getManagedCourses() {
        return managedCourses;
    }

    public void setManagedCourses(List<Course> managedCourses) {
        this.managedCourses = managedCourses;
    }
}

// Helper Enum
enum UserRole {
    STUDENT,
    TEACHER,
    ADMIN,
    STAFF
}

// Helper Class for @Embedded Field
@Embeddable // Marks the class whose fields will be embedded into the owning entity's table
class LocationDetails {
    @Column(name = "city", length = 50)
    private String city;
    @Column(name = "country", length = 50)
    private String country;
    // ...

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

// Minimal structure for associated entities
@Entity class Cohort {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    // ...
}

@Entity class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String title;
    @ManyToOne @JoinColumn(name = "teacher_id") private PlatformUser teacher;
    // ...
}