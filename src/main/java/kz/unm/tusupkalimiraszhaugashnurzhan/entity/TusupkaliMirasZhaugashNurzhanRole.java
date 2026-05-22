package kz.unm.tusupkalimiraszhaugashnurzhan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class TusupkaliMirasZhaugashNurzhanRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 40)
    private TusupkaliMirasZhaugashNurzhanRoleName name;

    @Column(length = 255)
    private String description;

    @OneToMany(mappedBy = "role")
    private List<TusupkaliMirasZhaugashNurzhanUser> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TusupkaliMirasZhaugashNurzhanRoleName getName() {
        return name;
    }

    public void setName(TusupkaliMirasZhaugashNurzhanRoleName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TusupkaliMirasZhaugashNurzhanUser> getUsers() {
        return users;
    }

    public void setUsers(List<TusupkaliMirasZhaugashNurzhanUser> users) {
        this.users = users;
    }
}
