package com.cherrysoft.manics.controller.v2.dto.users;

import com.cherrysoft.manics.controller.v2.dto.validation.OnCreate;
import com.cherrysoft.manics.controller.v2.dto.validation.Username;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

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
  @NotBlank(groups = {OnCreate.class}, message = "A username is required.")
  private final String username;

  @Email
  @NotBlank(groups = OnCreate.class, message = "An email is required.")
  private final String email;

  @Size(
      min = 6,
      max = 16,
      message = "Password must be between {min} and {max} chars."
  )
  @NotBlank(groups = {OnCreate.class}, message = "A password is required.")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private final String password;
}
