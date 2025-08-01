package ai.elimu.entity.analytics;

import ai.elimu.entity.BaseEntity;
import ai.elimu.entity.application.Application;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;
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
   * A value in the range [0.0, 1.0].
   */
  private Float masteryScore;

  /**
   * The number of milliseconds passed between the student opening the assessment task 
   * and submitting a response. E.g. <code>15000</code>.
   */
  private Long timeSpentMs;

  /**
   * Any additional data should be stored in the format of a JSON object.
   * 
   * Example:
   * <pre>
   * {'word_ids_presented': [1,2,3], 'word_id_selected': [2]}
   * </pre>
   */
  @Column(length = 1024)
  private String additionalData;

  private ResearchExperiment researchExperiment;

  private ExperimentGroup experimentGroup;
}
