package com.fillubell.pojo;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class PersonDTO {
    @Size(min = 3, max = 50, message = "email 3 - 50")
    private String email;

    @NotEmpty(message = "Naim is not empty")
    @Size(min = 2, max = 100, message = "Name ot 2 do 100")
    private String username;

    private String password;

    private String role;
}