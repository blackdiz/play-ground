package com.example.demo;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TestMapper {
    
    @Mapping(target = "id", source = "name")
    TestDomain toTestDomain(TestDto testDto);
    void update(TestDto testDto, @MappingTarget TestDomain.TestDomainBuilder testDomain);
    
    @InheritInverseConfiguration
    TestDto toTestDomain(TestDomain testDomain);
}
