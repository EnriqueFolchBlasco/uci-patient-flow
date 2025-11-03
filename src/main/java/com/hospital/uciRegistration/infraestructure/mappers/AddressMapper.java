package com.hospital.uciRegistration.infraestructure.mappers;

import com.hospital.uciRegistration.domain.model.patient.Address;
import com.hospital.uciRegistration.infraestructure.entity.AddressEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressEmbeddable toAddressEmbeddable(Address address) {
        if (address == null) return null;
        return new AddressEmbeddable(
                address.getStreet(),
                address.getCity(),
                address.getZip(),
                address.getState()
        );
    }

    public Address fromAddressEmbeddable(AddressEmbeddable embeddable) {
        if (embeddable == null) return null;
        return new Address(
                embeddable.getStreet(),
                embeddable.getCity(),
                embeddable.getZip(),
                embeddable.getState()
        );
    }
}
