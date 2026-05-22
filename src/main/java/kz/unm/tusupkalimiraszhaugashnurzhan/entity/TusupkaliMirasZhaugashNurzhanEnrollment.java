package kz.unm.tusupkalimiraszhaugashnurzhan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
)
public class TusupkaliMirasZhaugashNurzhanEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private TusupkaliMirasZhaugashNurzhanStudent student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private TusupkaliMirasZhaugashNurzhanCourse course;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Column(length = 10)
    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TusupkaliMirasZhaugashNurzhanEnrollmentStatus status =
            TusupkaliMirasZhaugashNurzhanEnrollmentStatus.ACTIVE;

    @PrePersist
    void onCreate() {
        if (enrollmentDate == null) {
            enrollmentDate = LocalDate.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TusupkaliMirasZhaugashNurzhanStudent getStudent() {
        return student;
    }

    public void setStudent(TusupkaliMirasZhaugashNurzhanStudent student) {
        this.student = student;
    }

    public TusupkaliMirasZhaugashNurzhanCourse getCourse() {
        return course;
    }

    public void setCourse(TusupkaliMirasZhaugashNurzhanCourse course) {
        this.course = course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public TusupkaliMirasZhaugashNurzhanEnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(TusupkaliMirasZhaugashNurzhanEnrollmentStatus status) {
        this.status = status;
    }
}
