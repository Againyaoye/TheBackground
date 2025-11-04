package com.example.demo02.mapper;


import com.example.demo02.domain.Machine;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MachineMapper {

    // 查询所有设备
    @Select("SELECT * FROM machine")
    List<Machine> findAll();

    // 根据ID查询设备
    @Select("SELECT * FROM machine WHERE machine_id = #{machineId}")
    Machine findById(String machineId);

    // 根据状态查询设备
    @Select("SELECT * FROM machine WHERE status = #{status}")
    List<Machine> findByStatus(String status);


}
