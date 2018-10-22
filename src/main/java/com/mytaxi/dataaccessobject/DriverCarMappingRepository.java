package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverCarMappingDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Database Access Object for Driver Car Mapping table.
 * <p/>
 */
public interface DriverCarMappingRepository extends CrudRepository<DriverCarMappingDO, Long>
{

    @Query(value = "SELECT dcm.id, dcm.dateCreated, dcm.isRiding, dcm.driver, dcm.car" +
            " FROM DriverCarMappingDO dcm WHERE dcm.driver.id=?1 and dcm.car.carId=?2 and dcm.isRiding=TRUE")
    public List<Object[]> findDriverCarMapping(Long driverId, Long carId);

    @Transactional
    @Modifying
    @Query("update DriverCarMappingDO dcm set isRiding=FALSE where dcm.driver.id=:driverId and dcm.car.carId=:carId and dcm.isRiding=TRUE")
    public void deSelectCar(@Param("driverId") Long driverId, @Param("carId")Long carId);

    @Query(value = "SELECT dcm.id, dcm.dateCreated, dcm.isRiding, dcm.driver, dcm.car" +
            " FROM DriverCarMappingDO dcm WHERE dcm.driver.id=?1 and dcm.isRiding=TRUE")
    public List<Object[]> findDriverCarMapping(Long driverId);

    @Query(value = "SELECT dcm.id, dcm.dateCreated, dcm.isRiding, dcm.driver, dcm.car" +
            " FROM DriverCarMappingDO dcm WHERE dcm.car.carId=?1 and dcm.isRiding=TRUE")
    public List<Object[]> findDriverCarMappingByCarId(Long carId);
}
