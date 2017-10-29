package ai.elimu.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.Contributor;

@Entity
public class Project extends BaseEntity {
    
    @NotNull
    private String name;
    
	@ManyToMany(fetch = FetchType.EAGER)
    private List<Contributor> managers;
    
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AppCategory> appCategories = new ArrayList<AppCategory>();

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
