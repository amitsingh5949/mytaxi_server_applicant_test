package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.GeoCoordinate;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    private Long carId;

    private Boolean deleted;


    @NotNull(message = "error.license_plate.notnull")
    private String  licensePlate;

    @NotNull(message = "error.seat_count.notnull")
    private Integer seatCount;

    private Boolean  convertible;

    private Float  rating;

    private EngineType engineType;

    private Long  kmDriven;

    private Float   topSpeed;

    private ManufacturerDTO  manufacturer;


}
