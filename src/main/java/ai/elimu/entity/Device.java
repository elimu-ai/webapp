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
public class Device extends BaseEntity {

  @NotNull
  @Column(unique = true)
  private String androidId;

  @NotNull
  private String deviceManufacturer;

  @NotNull
  private String deviceModel;

  @NotNull
  private String deviceSerial;

  // TODO: @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar timeRegistered; // Time of first synchronization with server

  @NotNull
  private String remoteAddress; // IP address during registration

  @NotNull
  private Integer osVersion;
}
