package ai.elimu.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class DbMigration extends BaseEntity {
    
    @NotNull
    @Column(unique = true)
    private Integer version; // 1001001, 1001002, 1001003, ... (1.1.1, 1.1.2, 1.1.3, ...)
    
    @NotNull
    @Column(length = 10000)
    private String script; // SQL script copied from file in src/main/resources/db/migration/<version>.sql

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendar;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
