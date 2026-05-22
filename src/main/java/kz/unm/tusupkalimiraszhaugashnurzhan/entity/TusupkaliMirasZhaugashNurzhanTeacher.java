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
@Table(name = "teachers")
public class TusupkaliMirasZhaugashNurzhanTeacher {

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
    private String employeeNumber;

    @Column(nullable = false, length = 120)
    private String academicTitle;

    @Column(nullable = false)
    private LocalDate hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private TusupkaliMirasZhaugashNurzhanDepartment department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private TusupkaliMirasZhaugashNurzhanUser user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<TusupkaliMirasZhaugashNurzhanCourse> courses = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
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

    public List<TusupkaliMirasZhaugashNurzhanCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<TusupkaliMirasZhaugashNurzhanCourse> courses) {
        this.courses = courses;
    }

    public List<TusupkaliMirasZhaugashNurzhanFileAttachment> getDocuments() {
        return documents;
    }

    public void setDocuments(List<TusupkaliMirasZhaugashNurzhanFileAttachment> documents) {
        this.documents = documents;
    }
}
