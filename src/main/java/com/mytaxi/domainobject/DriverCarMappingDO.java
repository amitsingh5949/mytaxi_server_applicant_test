package com.mytaxi.domainobject;

import com.mytaxi.domainvalue.EngineType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table( name = "driver_car_mapping" )
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class DriverCarMappingDO
{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "date_created",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(name = "is_riding",nullable = false)
    private Boolean isRiding = true;

    @JoinColumn(name = "driver_id")
    @OneToOne(fetch = FetchType.EAGER)
    DriverDO driver;

    @JoinColumn(name = "car_id")
    @OneToOne(fetch = FetchType.EAGER)
    CarDO car;

}
