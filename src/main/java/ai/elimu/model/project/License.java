package ai.elimu.model.project;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;
import javax.persistence.OneToOne;

@Entity
public class License extends BaseEntity {
    
    @NotNull
    private String licenseEmail;
    
    @NotNull
    private String licenseNumber;
    
    // TODO: maximum number of devices
    
    // TODO: expiry date
    
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;
    
    private String organization;
    
    @NotNull
    @OneToOne
    private AppCollection appCollection;

    public String getLicenseEmail() {
        return licenseEmail;
    }

    public void setLicenseEmail(String licenseEmail) {
        this.licenseEmail = licenseEmail;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public AppCollection getAppCollection() {
        return appCollection;
    }

    public void setAppCollection(AppCollection appCollection) {
        this.appCollection = appCollection;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
