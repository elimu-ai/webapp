package ai.elimu.model.project;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;

@Entity
public class License extends BaseEntity {
    
    @NotNull
    private String licenseEmail;
    
    @NotNull
    private String licenseNumber;
    
    // TODO: app collection

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
}
