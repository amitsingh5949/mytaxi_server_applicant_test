package com.mytaxi.controller;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.mapper.CarMapper;
import com.mytaxi.mapper.DriverMapper;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.driver.SearchDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All operations with a Car will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/search")
public class SearchDriverController
{


    @Autowired
    private SearchDriverService searchDriverService;

    @RequestMapping(value = "/getDriverByOnlineStatus",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(searchDriverService.find(onlineStatus));
    }

    @RequestMapping(value = "/getDriverByName",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public DriverDTO findByUserName(@RequestParam String driverName) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(searchDriverService.findByName(driverName));
    }

    @RequestMapping(value = "/findByLicencePlate",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public DriverDTO findByLicencePlate(@RequestParam String licencePlate) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(searchDriverService.findByLicencePlate(licencePlate));
    }

    @RequestMapping(value = "/findDriversByCarRating",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<DriverDTO> findDriversByCarRating(@RequestParam float rating)
    {
        return DriverMapper.makeDriverDTOList(searchDriverService.findDriversByCarRating(rating));
    }



}
