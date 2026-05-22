package kz.unm.tusupkalimiraszhaugashnurzhan.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class TusupkaliMirasZhaugashNurzhanDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<TusupkaliMirasZhaugashNurzhanStudent> students = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<TusupkaliMirasZhaugashNurzhanTeacher> teachers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TusupkaliMirasZhaugashNurzhanStudent> getStudents() {
        return students;
    }

    public void setStudents(List<TusupkaliMirasZhaugashNurzhanStudent> students) {
        this.students = students;
    }

    public List<TusupkaliMirasZhaugashNurzhanTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TusupkaliMirasZhaugashNurzhanTeacher> teachers) {
        this.teachers = teachers;
    }
}
