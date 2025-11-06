package com.example.demo02.controller;



import com.example.demo02.domain.Machine;
import com.example.demo02.mapper.MachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/machines")

public class machineController {

    @Autowired
    private MachineMapper machineMapper;

    // 获取所有设备列表
    @GetMapping
    public ResponseEntity<List<Machine>> getAllMachines() {
        try {
            List<Machine> machines = machineMapper.findAll();
            return ResponseEntity.ok(machines);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据ID获取设备详情
    @GetMapping("/{machineId}")
    public ResponseEntity<Machine> getMachineById(@PathVariable String machineId) {
        try {
            Machine machine = machineMapper.findById(machineId);
            if (machine != null) {
                return ResponseEntity.ok(machine);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据状态查询设备
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Machine>> getMachinesByStatus(@PathVariable String status) {
        try {
            List<Machine> machines = machineMapper.findByStatus(status);
            return ResponseEntity.ok(machines);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
