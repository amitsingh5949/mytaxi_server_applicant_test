package com.mytaxi.domainobject;

import com.mytaxi.domainvalue.EngineType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(
    name = "cars",
    uniqueConstraints = @UniqueConstraint(name = "licensePlate", columnNames = {"license_plate"})
)
@Where(clause = "deleted='false'")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CarDO
{

    @Id
    @Column(name = "car_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long carId;

    @Column(name = "date_created",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(name = "deleted",nullable = false)
    private Boolean deleted = false;

    @Column(name = "license_plate",nullable = false)
    private String licensePlate;

    @Column(name = "seat_count",nullable = false)
    private Integer seatCount;

    @Column(name = "convertible",nullable = false)
    private Boolean convertible;

    @Column(name = "rating",nullable = false)
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type",nullable = false)
    private EngineType engineType;

    @Column(name = "km_driven",nullable = false)
    private Integer kmDriven;

    @Column(name = "top_speed",nullable = false)
    private Float topSpeed;

    @JoinColumn(name = "manufacturer_id")
    @OneToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    ManufacturerDO manufacturer;

}
