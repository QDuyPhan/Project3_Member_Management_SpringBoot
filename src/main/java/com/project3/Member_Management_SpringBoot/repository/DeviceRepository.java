/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.repository;

import com.project3.Member_Management_SpringBoot.model.Device;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author buing
 */
@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {

    @Query("SELECT d FROM Device d")
    List<Device> findAllDevices();

    @Query("SELECT d FROM Device d LEFT JOIN Usage u ON d.id = u.device.id AND u.paidTime IS NULL AND u.borrowedTime IS NOT NULL WHERE u.device IS NULL")
    List<Device> findAvailableDevices();

    @Query("SELECT d FROM Device d LEFT JOIN Usage u ON d.id = u.device.id AND u.paidTime IS NULL AND u.borrowedTime IS NOT NULL WHERE u.device IS NULL AND d.name LIKE ?1")
    List<Device> findByNameLike(String name);

    @Query("SELECT d FROM Device d WHERE CAST(d.id AS string) LIKE CONCAT(:id, '%')")
    List<Device> findAllDevicesLikeId(@Param("id") int id);

    @Query("SELECT d FROM Device d WHERE d.name LIKE %?1% OR CAST(d.id AS string) LIKE %?1%")
    Page<Device> findAllByNameOrID(String query, Pageable pageable);
    
    @Query("SELECT COUNT(DISTINCT d.id) FROM Device d JOIN Usage u ON u.device.id = d.id WHERE u.paidTime IS NOT NULL AND (:startDate IS NULL OR u.borrowedTime >= :startDate) AND (:endDate IS NULL OR u.paidTime <= :endDate) AND (:name IS NULL OR d.name LIKE :name)")
    Integer statisticsTotalBorrowedDevice(@Param("name") String name, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
    
    @Query("SELECT COUNT(DISTINCT d.id) FROM Device d JOIN Usage u ON u.device.id = d.id WHERE u.borrowedTime IS NOT NULL AND u.paidTime IS NULL AND (:startDate IS NULL OR u.borrowedTime >= :startDate) AND (:endDate IS NULL OR u.borrowedTime <= :endDate)")
    Integer statisticsTotalBorrowingDevice(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
