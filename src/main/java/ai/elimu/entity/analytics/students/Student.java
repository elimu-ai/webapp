package ai.elimu.entity.analytics.students;

import ai.elimu.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends BaseEntity {

  /**
   * A 64-bit number (expressed as a hexadecimal string), unique to each combination of 
   * app-signing key, user, and device.
   * 
   * See https://developer.android.com/reference/android/provider/Settings.Secure#ANDROID_ID
   */
  @NotNull
  @Column(unique = true)
  private String androidId;
}
