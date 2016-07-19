package org.literacyapp.model.analytics;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.Student;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.enums.content.TaskType;

@Entity
public class TaskEvent extends DeviceEvent {
    
    @ManyToOne
    private Student student;
    
    @NotNull
    @ManyToOne
    private Application application;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}
