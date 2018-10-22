package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverCarMappingRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverCarMappingDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverCarMappingRepository driverCarRepository;


    public DefaultDriverService(final DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }



    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

    @Override
    public DriverCarMappingDO selectCar(DriverCarMappingDO driverCarDO) throws EntityNotFoundException, CarAlreadyInUseException {

        DriverCarMappingDO driverCarMap = null;

        DriverDO driverDO = driverRepository.findById(driverCarDO.getDriver().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverCarDO.getDriver().getId()));

       CarDO carDO =  carRepository.findById(driverCarDO.getCar().getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverCarDO.getCar().getCarId()));

       if(carDO != null && driverDO != null){
           List<Object[]> driverCarMappingDO = driverCarRepository.findDriverCarMapping(driverCarDO.getDriver().getId(),
                   driverCarDO.getCar().getCarId() );

           if(driverCarMappingDO.size() == 0){
               driverCarMap = driverCarRepository.save(driverCarDO);
           }
           else{
               throw new CarAlreadyInUseException("Car Already In USe Exception " +  "driver id :" + driverCarDO.getDriver().getId() +
                       "car id :" + driverCarDO.getCar().getCarId());
           }

       }

       return driverCarMap;
    }

    @Override
    public void deSelectCar(DriverCarMappingDO driverCarDO) throws EntityNotFoundException {

        DriverDO driverDO = driverRepository.findById(driverCarDO.getDriver().getId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverCarDO.getDriver().getId()));

        CarDO carDO =  carRepository.findById(driverCarDO.getCar().getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverCarDO.getCar().getCarId()));

        if(carDO != null && driverDO != null){
            List<Object[]>  driverCarMappingDO = driverCarRepository.findDriverCarMapping(driverCarDO.getDriver().getId(),
                    driverCarDO.getCar().getCarId() );

            if(driverCarMappingDO.size() != 0){
               driverCarRepository.deSelectCar(driverCarDO.getDriver().getId(),driverCarDO.getCar().getCarId());
            }
        }
    }

}
