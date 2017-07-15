package ai.elimu.model.analytics;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ai.elimu.model.Student;

@Entity
public class ApplicationOpenedEvent extends DeviceEvent {
    
    @NotNull
    private String packageName;
    
    @ManyToOne
    private Student student;
    
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
