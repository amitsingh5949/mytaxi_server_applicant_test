package com.mytaxi.service.driver;

import com.mytaxi.domainobject.DriverCarMappingDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    DriverCarMappingDO selectCar(DriverCarMappingDO driverCarDO) throws EntityNotFoundException, CarAlreadyInUseException;

    void deSelectCar(DriverCarMappingDO driverCarDO) throws EntityNotFoundException;
}
