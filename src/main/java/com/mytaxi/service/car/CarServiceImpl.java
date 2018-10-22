package com.mytaxi.service.car;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.mapper.CarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class CarServiceImpl implements CarService
{

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private  CarRepository carRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;


    /**
     * Selects a driver by id.
     *
     * @param carId
     * @return found Car
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCarChecked(carId);
    }

    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }

    @Override
    public List<CarDO> findAll(){

      return  (List<CarDO>) carRepository.findAll();
    }
    /**
     * Creates a new driver.
     *
     * @param carDO
     * @return
     * @throws ConstraintsViolationException if a car already exists.
     */
    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException,EntityNotFoundException
    {
        CarDO car;
        try
        {
            if(carDO.getManufacturer().getManufacturerId() != null){
                ManufacturerDO manufacturerDO = manufacturerRepository.findById(carDO.getManufacturer().getManufacturerId())
                        .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carDO.getManufacturer().getManufacturerId()));
                carDO.setManufacturer(manufacturerDO);
            }
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }


    /**
     * Deletes an existing car by id.
     *
     * @param carId
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
    }


}
