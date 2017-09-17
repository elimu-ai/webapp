package ai.elimu.model.project;

import ai.elimu.model.BaseEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class AppCategory extends BaseEntity {
    
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
