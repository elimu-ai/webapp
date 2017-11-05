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
    
    // TODO: firstName
    
    // TODO: lastName
    
    // TODO: company name
    
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
}
