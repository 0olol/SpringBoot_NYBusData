package com.Oolol.SpringBoot_NYBusData.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Oolol.SpringBoot_NYBusData.Entity.rawData;

public interface rawDataRepository extends JpaRepository<rawData,Long> {
    @Modifying
    @Query(value = "SELECT min(id) as id from example3 group by RECORDED_AT_TIME, VEHICLE_REF having count(*) > 1" , nativeQuery = true)
    public List<Long> findMinId(@Param("column1") String column1, @Param("column2") String column2);

    @Modifying
    @Query(value = "SELECT * FROM EXAMPLE3 WHERE ID IN (SELECT MIN(ID) FROM EXAMPLE3 GROUP BY RECORDED_AT_TIME, VEHICLE_REF HAVING COUNT(*) >= 1 order by MIN(ID) ASC)", nativeQuery = true)
    public List<rawData> findUniqueIds();
    
    // @Transactional
    // @Modifying
    // @Query( value ="UPDATE EXAMPLE3 SET CORRECT = 'true' where id in (Select MIN(id) from EXAMPLE3 group by :column1 ,:column2 having count(*) > 1)",nativeQuery = true)
    // void update1(@Param("column1") String column1, @Param("column2") String column2);
    // void update1(String column1, String column2);


    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE EXAMPLE3 SET CORRECT = 'true' where id in (Select MIN(id) from EXAMPLE3 group by (RECORDED_AT_TIME , VEHICLE_REF) having count(*) = 1)",nativeQuery = true)
    // void update2(@Param("column1") String column1, @Param("column2") String column2);

    // @Transactional
    // @Modifying
    // @Query(value ="UPDATE EXAMPLE3 SET CORRECT = 'true' where id in (Select MIN(id) from EXAMPLE3 group by (?1, ?2) having count(*) = 1)",nativeQuery = true)
    // public void updateBasedOnColumn(String column1, String column2);
    // void update1(String column1, String column2);

    // @Transactional
    // @Modifying
    // @Query(value ="UPDATE EXAMPLE3 SET CORRECT = 'true' where id in (Select MIN(id) from EXAMPLE3 group by (?1, ?2) having count(*) = 1)",nativeQuery = true)
    // public void update1(String column1, String column2);
    // // void update1(String column1, String column2);


    // @Transactional
    // @Modifying
    // @Query(value = "UPDATE EXAMPLE3 SET CORRECT = 'true' where id in (Select MIN(id) from EXAMPLE3 group by RECORDED_AT_TIME , VEHICLE_REF having count(*) = 1)",nativeQuery = true)
    // public void update2(@Param("column1") String column1, @Param("column2") String column2);

    // @Query(value = "Select * from EXAMPLE3 group by ID, RECORDED_AT_TIME , VEHICLE_REF having count(*) > 1" , nativeQuery = true)


    // public List<Long> findIdbyDistnctRecordedAtTimeAndVehicleRef();
    // @Modifying
    // @Query(value = "SELECT MIN(ID) FROM EXAMPLE3 GROUP BY RECORDED_AT_TIME, VEHICLE_REF HAVING COUNT(*) >= 1 order by MIN(ID) ASC", nativeQuery = true)
    // public List<Long> findUniqueIds();\
}

