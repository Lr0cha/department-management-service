package com.example.UserDept.services;

import com.example.UserDept.entities.employee.embedded.Address;
import com.example.UserDept.exceptions.ResourceNotFoundException;
import com.example.UserDept.web.dto.address.ViaCepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AddressService {
    private final WebClient webClient;

    public AddressService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://viacep.com.br/ws").build();
    }

    public Address getAddressByZipCode(String zipCode, Integer houseNumber) {

        ViaCepResponse response = webClient
                .get()
                .uri("/{cep}/json", zipCode)
                .retrieve()
                .bodyToMono(ViaCepResponse.class)
                .block();

        if (response == null || response.getCep() == null) {
            throw new ResourceNotFoundException("Endereço não encontrado para o CEP: " + zipCode);
        }

        Address address = new Address();
        address.setZipCode(response.getCep());
        address.setStreet(response.getLogradouro());
        address.setNeighborhood(response.getBairro());
        address.setCity(response.getLocalidade());
        address.setState(response.getUf());
        address.setHouseNumber(houseNumber);

        return address;
    }
}
