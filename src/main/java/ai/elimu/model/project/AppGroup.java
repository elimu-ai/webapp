package ai.elimu.model.project;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.admin.Application;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class AppGroup extends BaseEntity {
    
    @ManyToOne
    private AppCategory appCategory;
    
    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER)
    private List<Application> applications;
    
    public AppCategory getAppCategory() {
        return appCategory;
    }

    public void setAppCategory(AppCategory appCategory) {
        this.appCategory = appCategory;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
