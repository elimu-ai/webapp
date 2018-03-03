package ai.elimu.model.project;

import ai.elimu.model.BaseEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

@Entity
public class AppCategory extends BaseEntity {
    
    @NotNull
    private String name;
    
    private String backgroundColor; // Expected format: "250,250,250" (RBG)
    
    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER)
    private List<AppGroup> appGroups;

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

    public List<AppGroup> getAppGroups() {
        return appGroups;
    }

    public void setAppGroups(List<AppGroup> appGroups) {
        this.appGroups = appGroups;
    }
}
