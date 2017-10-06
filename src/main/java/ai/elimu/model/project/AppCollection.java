package ai.elimu.model.project;

import ai.elimu.model.BaseEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class AppCollection extends BaseEntity {
    
    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER)
    private List<AppCategory> appCategories;
    
    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER)
    private List<License> licenses;

    public List<AppCategory> getAppCategories() {
        return appCategories;
    }

    public void setAppCategories(List<AppCategory> appCategories) {
        this.appCategories = appCategories;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }
}
