/* Licensed under Apache-2.0 */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity for users information
 *
 * @author suhail.k
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends EntityDoc {

  @Column(name = "username", length = 100)
  private String username;

  @Column(name = "firstname", length = 100)
  private String firstName;

  @Column(name = "lastname", length = 100)
  private String lastName;

  @Column(name = "email", length = 200)
  private String email;

  @Column(name = "hintquestion", length = 100)
  private String hintQuestion;

  @Column(name = "hintanswer", length = 100)
  private String hintAnswer;

  @Column(name = "lastattempteddate", columnDefinition = "TIMESTAMP")
  private LocalDateTime lastAttemptedDate;

  @Column(name = "lastlogindate", columnDefinition = "TIMESTAMP")
  private LocalDateTime lastLoginDate;

  @Column(name = "companyxid")
  private Long companyXId;

  @Column(name = "isactive")
  private boolean isActive;

  @Column(name = "branchxid")
  private Long branchXId;

  @Column(name = "timezonexid")
  private Long timezoneXId;

  @Column(name = "designationxid")
  private Long designationXId;

  @Column(name = "sessiontime")
  private Long sessionTime;

  @Column(name = "profilepath", length = 500)
  private String profilePath;

  @Column(name = "token", columnDefinition = "TEXT")
  private String token;

  @Column(name = "logintime", columnDefinition = "TIMESTAMP")
  private LocalDate loginTime;

  @Column(name = "keycloakid", length = 100)
  private String keycloakId;

  @OneToMany(
      mappedBy = "users",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<RoleUserMapping> roleUserMappings;

  @ManyToOne
  @JoinColumn(name = "rolexid")
  private Roles roles;
}
