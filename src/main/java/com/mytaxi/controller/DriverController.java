package com.mytaxi.controller;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverCarMappingDTO;
import com.mytaxi.domainobject.DriverCarMappingDO;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.mapper.DriverCarMapper;
import com.mytaxi.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.DriverService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }

    @Autowired
    DriverCarMapper driverCarMapper;

    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }

    @RequestMapping(value = "/selectCar",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public DriverCarMappingDTO selectCar(@RequestParam long driverId, @RequestParam long carId) throws ConstraintsViolationException, EntityNotFoundException, CarAlreadyInUseException {

        CarDTO car = CarDTO.builder().carId(carId).build();
        DriverDTO driver = DriverDTO.builder().setId(driverId).build();
        DriverCarMappingDTO driverCarDTO = DriverCarMappingDTO.builder().car(car).driver(driver).isRiding(true).build();
        DriverCarMappingDO driverCarDO = driverCarMapper.map(driverCarDTO);
        return driverCarMapper.map(driverService.selectCar(driverCarDO));
    }

    @RequestMapping(value = "/deselectCar",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void deSelectCar( @RequestParam long driverId, @RequestParam long carId) throws ConstraintsViolationException, EntityNotFoundException {

        CarDTO car = CarDTO.builder().carId(carId).build();
        DriverDTO driver = DriverDTO.builder().setId(driverId).build();
        DriverCarMappingDTO driverCarDTO = DriverCarMappingDTO.builder().car(car).driver(driver).build();

        DriverCarMappingDO driverCarDO = driverCarMapper.map(driverCarDTO);
        driverService.deSelectCar(driverCarDO);
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }



}
