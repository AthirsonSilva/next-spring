package com.api.nextspring.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
	@NotEmpty(message = "Email is required")
	@Email(message = "Email must be valid")
	private String email;
	@NotEmpty(message = "Password is required")
	private String password;
}
