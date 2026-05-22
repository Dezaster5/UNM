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
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class TusupkaliMirasZhaugashNurzhanCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false, length = 40)
    private String semester;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private TusupkaliMirasZhaugashNurzhanTeacher teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TusupkaliMirasZhaugashNurzhanEnrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<TusupkaliMirasZhaugashNurzhanFileAttachment> documents = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TusupkaliMirasZhaugashNurzhanTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(TusupkaliMirasZhaugashNurzhanTeacher teacher) {
        this.teacher = teacher;
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
