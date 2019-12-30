package ai.elimu.model.project;

import ai.elimu.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class AppCategory extends BaseEntity {
    
    @NotNull
    @ManyToOne
    private Project project;
    
    @NotNull
    private String name;
    
    private String backgroundColor; // Expected format: "250,250,250" (RBG)
    
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
    
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
