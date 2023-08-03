package com.bicheka.user;

import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {
    
    @NonNull
    @NotBlank
    @NotEmpty
    private String streetAddress;

    private String apt;

    @NonNull
    @NotBlank
    @NotEmpty
    private String city;

    @NonNull
    @NotBlank
    @NotEmpty
    private String country;

    @NonNull
    @NotBlank
    @NotEmpty
    private String zipCode;

    @NonNull
    @NotBlank
    @NotEmpty
    private String state;
}
