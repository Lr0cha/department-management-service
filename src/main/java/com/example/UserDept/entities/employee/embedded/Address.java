package com.example.UserDept.entities.employee.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Address {
    @Column(name = "zip_code")
    private String zipCode;

    private String street;

    @Column(name = "house_number")
    private Integer houseNumber;

    private String neighborhood;

    private String city;

    private String state;
}
