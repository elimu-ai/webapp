package org.literacyapp.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registrationTime;
    
    private Long providerIdFacebook, providerIdGoogle, providerIdGitHub;
    
    private String firstName;
    
    private String lastName;

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

    public Calendar getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Calendar registrationTime) {
        this.registrationTime = registrationTime;
    }
    
    public Long getProviderIdFacebook() {
        return providerIdFacebook;
    }

    public void setProviderIdFacebook(Long providerIdFacebook) {
        this.providerIdFacebook = providerIdFacebook;
    }

    public Long getProviderIdGoogle() {
        return providerIdGoogle;
    }

    public void setProviderIdGoogle(Long providerIdGoogle) {
        this.providerIdGoogle = providerIdGoogle;
    }

    public Long getProviderIdGitHub() {
        return providerIdGitHub;
    }

    public void setProviderIdGitHub(Long providerIdGitHub) {
        this.providerIdGitHub = providerIdGitHub;
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
}
