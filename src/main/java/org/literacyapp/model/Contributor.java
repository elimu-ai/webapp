package org.literacyapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.literacyapp.model.enums.Role;

@Entity
public class Contributor extends BaseEntity {

    @NotNull
    @Column(unique=true)
    private String email;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @NotNull
    @Size(min=2)
    private String firstName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
