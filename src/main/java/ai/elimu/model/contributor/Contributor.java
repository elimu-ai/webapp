package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Contributor extends BaseEntity {

  @NotNull // TODO: remove requirement of providing an e-mail address
  @Column(unique = true)
  private String email;

  @NotEmpty
  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<Role> roles;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar registrationTime;

  // TODO: add registrationPlatform

  // TODO: add registrationProvider

  //    @Column(unique=true)
  private String providerIdGoogle;

  /**
   * An Ethereum address. Expected format: "0xAb5801a7D398351b8bE11C439e05C5B3259aeC9B"
   */
  @Column(unique = true, length = 42)
  private String providerIdWeb3;

  //    @Column(unique=true)
  private String providerIdGitHub;

  //    @Column(unique=true)
  private String providerIdDiscord;

  private String usernameGitHub;

  private String usernameDiscord;

  private String imageUrl;

  private String firstName;

  private String lastName;

  private String occupation;

  @Column(length = 1000)
  private String motivation;
}
