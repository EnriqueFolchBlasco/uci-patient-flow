package com.hospital.uciRegistration.infraestructure.mappers;

import com.hospital.uciRegistration.domain.model.patient.Address;
import com.hospital.uciRegistration.infraestructure.entity.AddressEmbeddable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressEmbeddable toAddressEmbeddable(Address address);

    Address fromAddressEmbeddable(AddressEmbeddable embeddable);
}