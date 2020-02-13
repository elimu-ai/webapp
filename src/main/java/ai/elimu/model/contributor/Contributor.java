package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import ai.elimu.model.enums.Role;

@Entity
public class Contributor extends BaseEntity {

    @NotNull
    @Column(unique=true)
    private String email;
    
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registrationTime;
    
    private String providerIdFacebook, providerIdGoogle, providerIdGitHub;
    
    private String usernameGitHub;
    
    private String imageUrl;
    
    private String firstName;
    
    private String lastName;
    
    private String occupation;
    
    @Column(length = 1000)
    private String motivation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
    
    public String getUsernameGitHub() {
        return usernameGitHub;
    }

    public void setUsernameGitHub(String usernameGitHub) {
        this.usernameGitHub = usernameGitHub;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
    
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }
}
