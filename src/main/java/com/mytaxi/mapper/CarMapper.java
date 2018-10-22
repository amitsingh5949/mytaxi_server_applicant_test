package com.mytaxi.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper{

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mappings({
            @Mapping(target = "carId", source = "carId"),
            @Mapping(target = "manufacturer.manufacturerId", source = "manufacturer.manufacturerId")
    })
    CarDO map(CarDTO carDTO);

    @Mappings({
            @Mapping(target = "carId", source = "carId"),
            @Mapping(target = "manufacturer.manufacturerId", source = "manufacturer.manufacturerId")
    })
    CarDTO map(CarDO carDO);


    List<CarDTO> map(List<CarDO> cars);

}

