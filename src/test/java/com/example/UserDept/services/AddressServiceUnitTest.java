package com.example.UserDept.services;

import com.example.UserDept.entities.employee.embedded.Address;
import com.example.UserDept.exceptions.ResourceNotFoundException;
import com.example.UserDept.web.dto.address.ViaCepResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceUnitTest {

    @Mock
    private WebClient.Builder webClientBuilderMock;

    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersUriSpec uriSpecMock;
    @Mock
    private WebClient.RequestBodySpec bodySpecMock;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    private AddressService addressService;


    @BeforeEach
    void setUp() {
        when(webClientBuilderMock.baseUrl(anyString())).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientMock);

        addressService = new AddressService(webClientBuilderMock);
    }

    @Test
    void givenZipCodeAndHouseNumber_returnViaCepResponse_whenSuccess() {
        String zipCode = "01001-000";
        Integer houseNumber = 123;

        ViaCepResponse mockResponse = new ViaCepResponse();
        mockResponse.setCep("01001-000");
        mockResponse.setLogradouro("Praça da Sé");
        mockResponse.setBairro("Sé");
        mockResponse.setLocalidade("São Paulo");
        mockResponse.setUf("SP");

        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri("/{cep}/json", zipCode)).thenReturn(bodySpecMock);
        when(bodySpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ViaCepResponse.class)).thenReturn(Mono.just(mockResponse));

        Address address = addressService.getAddressByZipCode(zipCode, houseNumber);

        assertNotNull(address);
        assertEquals("01001-000", address.getZipCode());
        assertEquals("Praça da Sé", address.getStreet());
        assertEquals("Sé", address.getNeighborhood());
        assertEquals("São Paulo", address.getCity());
        assertEquals("SP", address.getState());
        assertEquals(houseNumber, address.getHouseNumber());
    }

    @Test
    void givenZipCodeAndHouseNumber_throwResourceNotFound_whenAddressNotFound()  {
        String zipCode = "00000-000";
        Integer houseNumber = 123;
        when(webClientMock.get()).thenReturn(uriSpecMock);
        when(uriSpecMock.uri("/{cep}/json", zipCode)).thenReturn(bodySpecMock);
        when(bodySpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(ViaCepResponse.class)).thenReturn(Mono.empty());

        ResourceNotFoundException exception =  assertThrows(ResourceNotFoundException.class, () -> {
            addressService.getAddressByZipCode(zipCode, houseNumber);
        });
        assertEquals("Address not found for the ZIP code: 00000-000", exception.getMessage());
    }
}
