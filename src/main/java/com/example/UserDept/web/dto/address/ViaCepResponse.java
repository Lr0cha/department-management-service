package com.example.UserDept.web.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViaCepResponse {
    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;
}
