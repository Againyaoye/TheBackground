package com.example.demo02.mapper;

import com.example.demo02.domain.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    // 查询所有交易记录
    @Select("SELECT transaction_id, user_id, machine_id, order_status, total_liters, " +
            "final_amount, start_time, end_time, water_add_switch, fill_up, device_temperature, " +
            "battery_level, latitude_and_longitude FROM transaction")
    List<Transaction> findAll();

    // 根据交易ID查询
    @Select("SELECT transaction_id, user_id, machine_id, order_status, total_liters, " +
            "final_amount, start_time, end_time, water_add_switch, fill_up, device_temperature, " +
            "battery_level, latitude_and_longitude FROM transaction " +
            "WHERE transaction_id = #{transactionId}")
    Transaction findByTransactionId(String transactionId);

    // 根据用户ID查询交易记录
    @Select("SELECT transaction_id, user_id, machine_id, order_status, total_liters, " +
            "final_amount, start_time, end_time, water_add_switch, fill_up, device_temperature, " +
            "battery_level, latitude_and_longitude FROM transaction " +
            "WHERE user_id = #{userId} ORDER BY start_time DESC")
    List<Transaction> findByUserId(String userId);

    // 根据设备ID查询交易记录
    @Select("SELECT transaction_id, user_id, machine_id, order_status, total_liters, " +
            "final_amount, start_time, end_time, water_add_switch, fill_up, device_temperature, " +
            "battery_level, latitude_and_longitude FROM transaction " +
            "WHERE machine_id = #{machineId} ORDER BY start_time DESC")
    List<Transaction> findByMachineId(String machineId);

    // 插入交易记录
    @Insert("INSERT INTO transaction(transaction_id, user_id, machine_id, order_status, " +
            "total_liters, final_amount, start_time, end_time, water_add_switch, fill_up, " +
            "device_temperature, battery_level, latitude_and_longitude) " +
            "VALUES(#{transactionId}, #{userId}, #{machineId}, #{orderStatus}, " +
            "#{totalLiters}, #{finalAmount}, #{startTime}, #{endTime}, #{waterAddSwitch}, " +
            "#{fillUp}, #{deviceTemperature}, #{batteryLevel}, #{latitudeAndLongitude})")
    int insert(Transaction transaction);

    // 更新交易记录
    @Update("UPDATE transaction SET user_id=#{userId}, machine_id=#{machineId}, order_status=#{orderStatus}, " +
            "total_liters=#{totalLiters}, final_amount=#{finalAmount}, start_time=#{startTime}, " +
            "end_time=#{endTime}, water_add_switch=#{waterAddSwitch}, fill_up=#{fillUp}, " +
            "device_temperature=#{deviceTemperature}, battery_level=#{batteryLevel}, " +
            "latitude_and_longitude=#{latitudeAndLongitude} WHERE transaction_id=#{transactionId}")
    int update(Transaction transaction);

    // 更新订单状态
    @Update("UPDATE transaction SET order_status=#{orderStatus} WHERE transaction_id=#{transactionId}")
    int updateOrderStatus(@Param("transactionId") String transactionId, @Param("orderStatus") String orderStatus);

    // 完成交易（更新结束信息）
    @Update("UPDATE transaction SET total_liters=#{totalLiters}, final_amount=#{finalAmount}, " +
            "end_time=#{endTime}, order_status=#{orderStatus} WHERE transaction_id=#{transactionId}")
    int completeTransaction(@Param("transactionId") String transactionId,
                            @Param("totalLiters") Double totalLiters,
                            @Param("finalAmount") Double finalAmount,
                            @Param("endTime") String endTime,
                            @Param("orderStatus") String orderStatus);

    // 更新设备状态信息
    @Update("UPDATE transaction SET water_add_switch=#{waterAddSwitch}, fill_up=#{fillUp}, " +
            "device_temperature=#{deviceTemperature}, battery_level=#{batteryLevel}, " +
            "latitude_and_longitude=#{latitudeAndLongitude} WHERE transaction_id=#{transactionId}")
    int updateDeviceStatus(@Param("transactionId") String transactionId,
                           @Param("waterAddSwitch") String waterAddSwitch,
                           @Param("fillUp") String fillUp,
                           @Param("deviceTemperature") String deviceTemperature,
                           @Param("batteryLevel") String batteryLevel,
                           @Param("latitudeAndLongitude") String latitudeAndLongitude);

    // 检查交易是否存在
    @Select("SELECT COUNT(*) FROM transaction WHERE transaction_id = #{transactionId}")
    int existsByTransactionId(String transactionId);

    // 分页查询
    @Select("SELECT * FROM transaction LIMIT #{size} OFFSET #{offset}")
    List<Transaction> findWithPagination(@Param("offset") int offset, @Param("size") int size);

    // 获取总记录数
    @Select("SELECT COUNT(*) FROM transaction")
    int getTotalCount();




}
