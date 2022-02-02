package com.coderhouse.model.request;

import lombok.*;

import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    @NotBlank(message = "El campo type no puede vacio")
    @Pattern(regexp = "^(Admin|Client|Editor)$", message = "Solo acepta: Admin,Client,Editor")
    private String type;
}
