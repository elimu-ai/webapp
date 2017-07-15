package ai.elimu.model.analytics;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import ai.elimu.model.Student;
import ai.elimu.model.admin.Application;

@MappedSuperclass
public abstract class LearningEvent extends DeviceEvent {
    
    @NotNull
    @ManyToOne
    private Application application;
    
    @ManyToOne
    private Student student;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
