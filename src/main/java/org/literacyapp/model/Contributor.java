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
    
    private String providerIdFacebook, providerIdGoogle, providerIdGitHub;
    
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
    
    public String getProviderIdFacebook() {
        return providerIdFacebook;
    }

    public void setProviderIdFacebook(String providerIdFacebook) {
        this.providerIdFacebook = providerIdFacebook;
    }

    public String getProviderIdGoogle() {
        return providerIdGoogle;
    }

    public void setProviderIdGoogle(String providerIdGoogle) {
        this.providerIdGoogle = providerIdGoogle;
    }

    public String getProviderIdGitHub() {
        return providerIdGitHub;
    }

    public void setProviderIdGitHub(String providerIdGitHub) {
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
