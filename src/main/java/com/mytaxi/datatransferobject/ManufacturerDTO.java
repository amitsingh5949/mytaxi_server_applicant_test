package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerDTO
{
    private Long manufacturerId;

    @NotNull(message = "error.name.notnull")
    private String  name;

    @NotNull(message = "error.location.notnull")
    private String  location;

    @NotNull(message = "error.contactEmail.notnull")
    private String  contactEmail;


    
}
