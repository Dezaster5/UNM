package kz.unm.tusupkalimiraszhaugashnurzhan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "file_attachments")
public class TusupkaliMirasZhaugashNurzhanFileAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false, unique = true)
    private String storedFileName;

    @Column(nullable = false, length = 120)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private TusupkaliMirasZhaugashNurzhanUser uploadedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private TusupkaliMirasZhaugashNurzhanStudent student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private TusupkaliMirasZhaugashNurzhanTeacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private TusupkaliMirasZhaugashNurzhanCourse course;

    @PrePersist
    void onCreate() {
        uploadDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public TusupkaliMirasZhaugashNurzhanUser getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(TusupkaliMirasZhaugashNurzhanUser uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public TusupkaliMirasZhaugashNurzhanStudent getStudent() {
        return student;
    }

    public void setStudent(TusupkaliMirasZhaugashNurzhanStudent student) {
        this.student = student;
    }

    public TusupkaliMirasZhaugashNurzhanTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(TusupkaliMirasZhaugashNurzhanTeacher teacher) {
        this.teacher = teacher;
    }

    public TusupkaliMirasZhaugashNurzhanCourse getCourse() {
        return course;
    }

    public void setCourse(TusupkaliMirasZhaugashNurzhanCourse course) {
        this.course = course;
    }
}
