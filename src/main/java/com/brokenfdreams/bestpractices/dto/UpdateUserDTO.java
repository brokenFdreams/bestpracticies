package com.brokenfdreams.bestpractices.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.NonNull;

@Data
@SuperBuilder
@Jacksonized
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = UpdateUserDTO.class)
@JsonSubTypes(@JsonSubTypes.Type(UserDTO.class))
public class UpdateUserDTO {

    @NonNull
    private final String login;

    @NonNull
    private final String firstName;

    @NonNull
    private final String lastName;

    private final int age;
}
