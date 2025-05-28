package ai.elimu.entity.analytics;

import ai.elimu.entity.BaseEntity;
import ai.elimu.entity.application.Application;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AssessmentEvent extends BaseEntity {

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar timestamp;

  /**
   * A 64-bit number (expressed as a hexadecimal string), unique to each combination of 
   * app-signing key, user, and device.
   * 
   * See https://developer.android.com/reference/android/provider/Settings.Secure#ANDROID_ID
   */
  @NotNull
  private String androidId;

  /**
   * The package name of the {@link #application} where the assessment event occurred.
   * E.g. <code>ai.elimu.soundcards</code>.
   */
  @NotNull
  private String packageName;

  /**
   * This field will only be populated if a corresponding {@link Application} can be 
   * found in the database for the {@link #packageName}.
   */
  @ManyToOne
  private Application application;

  /**
   * Any additional data should be stored in the format of a JSON object.
   * 
   * Example:
   * <pre>
   * {'word_ids_presented': [1,2,3], 'word_id_selected': 2}
   * </pre>
   */
  @Column(length = 1024)
  private String additionalData;
}
