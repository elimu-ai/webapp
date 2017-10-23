package ai.elimu.model.project;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

import ai.elimu.model.BaseEntity;

@Entity
public class AppCategory extends BaseEntity {
    
    @NotNull
    private String name;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_collection_id")
	private AppCollection appCollection;
    
    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER)
    private List<AppGroup> appGroups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppGroup> getAppGroups() {
        return appGroups;
    }

    public void setAppGroups(List<AppGroup> appGroups) {
        this.appGroups = appGroups;
    }
}
