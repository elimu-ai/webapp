package ai.elimu.model.project;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ai.elimu.model.BaseEntity;

@Entity
public class AppCollection extends BaseEntity {
    
    @NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
    private Project project;
    
    @NotNull
    private String name;
    
    @NotEmpty
	@OneToMany(mappedBy = "appCollection", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AppCategory> appCategories = new ArrayList<AppCategory>();
    
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
