package org.literacyapp.model.analytics;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;

@Entity
public class TaskEvent extends DeviceEvent {
    
    @ManyToOne
    private Student student;
    
    @NotNull
    @ManyToOne
    private Application application;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
