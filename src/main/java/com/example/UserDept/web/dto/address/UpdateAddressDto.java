package com.example.UserDept.web.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateAddressDto {
    @NotBlank
    @Pattern(
            message = "Formato do cep inválido",
            regexp = "^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{5}-[0-9]{3})|([0-9]{8}))$"
    )
    private String zipCode;

    @NotNull
    private Integer houseNumber;
}
