package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverCarMappingRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.DriverCarMappingDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class SearchDriverServiceImpl implements SearchDriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(SearchDriverServiceImpl.class);

    private final DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverCarMappingRepository driverCarRepository;


    public SearchDriverServiceImpl(final DriverRepository driverRepository)
    {
        this.driverRepository = driverRepository;
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {

        return driverRepository.findByOnlineStatus(onlineStatus);
    }

    @Override
    public DriverDO findByName(String driverName) throws EntityNotFoundException {

        DriverCarMappingDO driverCarMappingDO = DriverCarMappingDO.builder().build();
        DriverDO driverDO =  driverRepository.findByUsername(driverName);
        return driverDO;
    }

    @Override
    public DriverDO findByLicencePlate(String licencePlate) {
        DriverDO driverDO = null;
        CarDO carDo = carRepository.findByLicensePlate(licencePlate);
        if(carDo != null){
            List<Object[]> driverCarMappingDOList = driverCarRepository.findDriverCarMappingByCarId(carDo.getCarId());
            Object[] arr= driverCarMappingDOList.get(0);
            if(arr.length > 3){
                driverDO = (DriverDO)arr[3];
            }

        }
        return driverDO;
    }

    @Override
    public List<DriverDO> findDriversByCarRating(float rating) {
        List<DriverDO> drivers = new ArrayList<>();
        List<CarDO> cars = carRepository.findByRating(rating);
        if(cars.size() != 0) {
            cars.stream().forEach( car -> {
                List<Object[]> driverCarMappingDOList = driverCarRepository.findDriverCarMappingByCarId(car.getCarId());
                if(driverCarMappingDOList.size() > 0) {
                    Object[] arr = driverCarMappingDOList.get(0);
                    if (arr.length > 3) {
                        drivers.add((DriverDO) arr[3]);
                    }
                }
            });

        }
        return drivers;
    }
}
