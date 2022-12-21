package com.cherrysoft.manics.web.v2.dto.users;

import com.cherrysoft.manics.web.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.web.v2.dto.validation.Username;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class ManicUserDTO extends RepresentationModel<ManicUserDTO> {
  @Null
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @Username
  @NotBlank(message = "A username is required.", groups = OnCreate.class)
  private final String username;

  @Size(
      min = 6,
      max = 16,
      message = "Password must be between {min} and {max} chars."
  )
  @NotBlank(message = "A password is required.", groups = OnCreate.class)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private final String password;

  @Email
  @NotBlank(message = "An email is required.", groups = OnCreate.class)
  private final String email;

  @Override
  @NonNull
  public String toString() {
    return "ManicUserDTO{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + "*******" + '\'' +
        ", email='" + email + '\'' +
        '}';
  }

}
