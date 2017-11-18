package ai.elimu.model.project;

import ai.elimu.model.BaseEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class AppCollection extends BaseEntity {
    
    @NotNull
    @ManyToOne
    private Project project;
    
    @NotNull
    private String name;
    
    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppCategory> appCategories;
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppCategory> getAppCategories() {
        return appCategories;
    }

    public void setAppCategories(List<AppCategory> appCategories) {
        this.appCategories = appCategories;
    }
}
