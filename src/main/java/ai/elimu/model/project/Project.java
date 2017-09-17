package ai.elimu.model.project;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.Contributor;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Project extends BaseEntity {
    
    @NotNull
    private String name;
    
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Contributor> managers;
    
    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER)
    private List<AppCategory> appCategories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contributor> getManagers() {
        return managers;
    }

    public void setManagers(List<Contributor> managers) {
        this.managers = managers;
    }

    public List<AppCategory> getAppCategories() {
        return appCategories;
    }

    public void setAppCategories(List<AppCategory> appCategories) {
        this.appCategories = appCategories;
    }
}
