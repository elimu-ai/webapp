package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import java.util.Calendar;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import ai.elimu.model.enums.Role;

@Entity
public class Contributor extends BaseEntity {

    @NotNull // TODO: remove requirement of providing an e-mail address
    @Column(unique=true)
    private String email;
    
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registrationTime;
    
    // TODO: add registrationPlatform
    
    // TODO: add registrationProvider
    
//    @Column(unique=true)
    private String providerIdGoogle;
    
    /**
     * An Ethereum address. Expected format: "0xAb5801a7D398351b8bE11C439e05C5B3259aeC9B"
     */
    @Column(unique=true, length=42)
    private String providerIdWeb3;
    
//    @Column(unique=true)
    private String providerIdGitHub;

//    @Column(unique=true)
    private String providerIdDiscord;
    
    private String usernameGitHub;

    private String usernameDiscord;
    
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

    public String getProviderIdGoogle() {
        return providerIdGoogle;
    }

    public void setProviderIdGoogle(String providerIdGoogle) {
        this.providerIdGoogle = providerIdGoogle;
    }
    
    public String getProviderIdWeb3() {
        return providerIdWeb3;
    }

    public void setProviderIdWeb3(String providerIdWeb3) {
        this.providerIdWeb3 = providerIdWeb3;
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
    public String getProviderIdDiscord() {
        return providerIdDiscord;
    }

    public void setProviderIdDiscord(String providerIdDiscord) {
        this.providerIdDiscord = providerIdDiscord;
    }
    public String getUsernameDiscord() {
        return usernameDiscord;
    }

    public void setUsernameDiscord(String usernameDiscord) {
        this.usernameDiscord = usernameDiscord;
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
