package com.mytaxi.mapper;

import com.mytaxi.datatransferobject.DriverCarMappingDTO;
import com.mytaxi.domainobject.DriverCarMappingDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverCarMapper {

    DriverCarMapper INSTANCE = Mappers.getMapper(DriverCarMapper.class);

    @Mappings({
            @Mapping(target = "car.carId", source = "car.carId"),
            @Mapping(target = "driver.id", source = "driver.id")
    })
    DriverCarMappingDO map(DriverCarMappingDTO driverCarDTO);

    @Mappings({
            @Mapping(target = "car.carId", source = "car.carId"),
            @Mapping(target = "driver.id", source = "driver.id")
    })
    DriverCarMappingDTO map(DriverCarMappingDO driverCarDO);


    List<DriverCarMappingDTO> map(List<DriverCarMappingDO> cars);

}

