package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Database Access Object for Car table.
 * <p/>
 */
public interface CarRepository extends CrudRepository<CarDO, Long>
{
   CarDO findByLicensePlate(String licencePlate);
   List<CarDO> findByRating(float rating);
}
