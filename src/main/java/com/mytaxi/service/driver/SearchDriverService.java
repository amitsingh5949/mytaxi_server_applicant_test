package com.mytaxi.service.driver;

import com.mytaxi.domainobject.DriverCarMappingDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.Collection;
import java.util.List;

public interface SearchDriverService
{

    List<DriverDO> find(OnlineStatus onlineStatus);

    DriverDO findByName(String driverName) throws EntityNotFoundException;


    DriverDO findByLicencePlate(String licencePlate);

    List<DriverDO> findDriversByCarRating(float rating);
}
