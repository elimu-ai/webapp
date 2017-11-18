package ai.elimu.model.admin;

import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.enums.content.NumeracySkill;
import ai.elimu.model.enums.admin.ApplicationStatus;
import ai.elimu.model.project.Project;

@Entity
public class Application extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @NotNull
    private String packageName;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<LiteracySkill> literacySkills;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<NumeracySkill> numeracySkills;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    
    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    @ManyToOne
    private Project project;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Set<LiteracySkill> getLiteracySkills() {
        return literacySkills;
    }

    public void setLiteracySkills(Set<LiteracySkill> literacySkills) {
        this.literacySkills = literacySkills;
    }

    public Set<NumeracySkill> getNumeracySkills() {
        return numeracySkills;
    }

    public void setNumeracySkills(Set<NumeracySkill> numeracySkills) {
        this.numeracySkills = numeracySkills;
    }
    
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
