package kz.unm.tusupkalimiraszhaugashnurzhan.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class TusupkaliMirasZhaugashNurzhanStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Column(nullable = false, unique = true, length = 40)
    private String studentNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 40)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private TusupkaliMirasZhaugashNurzhanDepartment department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private TusupkaliMirasZhaugashNurzhanUser user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TusupkaliMirasZhaugashNurzhanEnrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<TusupkaliMirasZhaugashNurzhanFileAttachment> documents = new ArrayList<>();

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TusupkaliMirasZhaugashNurzhanDepartment getDepartment() {
        return department;
    }

    public void setDepartment(TusupkaliMirasZhaugashNurzhanDepartment department) {
        this.department = department;
    }

    public TusupkaliMirasZhaugashNurzhanUser getUser() {
        return user;
    }

    public void setUser(TusupkaliMirasZhaugashNurzhanUser user) {
        this.user = user;
    }

    public List<TusupkaliMirasZhaugashNurzhanEnrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<TusupkaliMirasZhaugashNurzhanEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<TusupkaliMirasZhaugashNurzhanFileAttachment> getDocuments() {
        return documents;
    }

    public void setDocuments(List<TusupkaliMirasZhaugashNurzhanFileAttachment> documents) {
        this.documents = documents;
    }
}
