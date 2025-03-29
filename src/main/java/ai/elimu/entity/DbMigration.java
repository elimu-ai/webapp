package ai.elimu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}
