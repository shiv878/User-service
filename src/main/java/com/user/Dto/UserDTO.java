package com.user.Dto;

import jakarta.validation.constraints.*;
import org.apache.logging.log4j.message.Message;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

public class UserDTO {



    private Long id;
    @NotBlank(message = "First name should not be null or blank")
    private String firstName;
    @NotBlank(message = "First name should not be null or blank")
    private String lastName;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;
    @NotBlank(message = "Password should not be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(
            regexp = "^(?=.{8,}$)(?=.*[A-Z])(?=.*[^A-Za-z0-9]).*$",
            message = "Password must be at least 8 chars, include an uppercase letter and a special character"
    )

    private String password;
    @NotNull(message = "Phone number should not be null")
    @Digits(integer = 10, fraction = 0, message = "Phone number should contain only digits")
    private Long phoneNumber;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
