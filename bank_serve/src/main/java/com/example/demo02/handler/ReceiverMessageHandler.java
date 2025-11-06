package com.example.demo02.handler;

// 更新导入路径

import com.example.demo02.domain.Transaction;
import com.example.demo02.mapper.TransactionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReceiverMessageHandler implements MessageHandler {

//    @Autowired
//   private TransactionRepository transactionRepository;

@Autowired(required = false) // 暂时设置为可选依赖
private TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper; // JSON解析器

    // 添加初始化检查
    @PostConstruct
    public void init() {
        System.out.println("=== ReceiverMessageHandler 初始化检查 ===");
        System.out.println("transactionRepository: " + (transactionRepository != null ? "✅ 已注入" : "❌ 未注入"));
        System.out.println("===================================");
    }


    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
//        String payload = message.getPayload().toString();
//        MessageHeaders headers = message.getHeaders();
//        String topicName = headers.get("mqtt_receivedTopic").toString();

        String payload = message.getPayload().toString();
        MessageHeaders headers = message.getHeaders();
        String topicName = headers.get("mqtt_receivedTopic").toString();

        System.out.println("收到MQTT消息 - 主题: " + topicName);
        System.out.println("消息内容: " + payload);


        // 解析JSON并保存到数据库
        saveToTransactionTable(payload, topicName);

    }
    private void saveToTransactionTable(String payload, String topic) {
        try {
            System.out.println("=== 开始解析MQTT消息并保存到数据库 ===");

            // 解析JSON
            JsonNode jsonNode = objectMapper.readTree(payload);
            System.out.println("✅ JSON解析成功");

            // 创建Transaction对象（ID会自动生成）
            Transaction transaction = new Transaction();
            System.out.println("生成的交易ID: " + transaction.getTransactionId());

            // 直接设置字段 - 最简单的方式
            if (jsonNode.has("water_add_switch")) {
                transaction.setWaterAddSwitch(jsonNode.get("water_add_switch").asText());
                System.out.println("✅ 设置 water_add_switch: " + jsonNode.get("water_add_switch").asText());
            }

            if (jsonNode.has("fill_up")) {
                transaction.setFillUp(jsonNode.get("fill_up").asText());
                System.out.println("✅ 设置 fill_up: " + jsonNode.get("fill_up").asText());
            }

            if (jsonNode.has("device_temperature")) {
                transaction.setDeviceTemperature(jsonNode.get("device_temperature").asText());
                System.out.println("✅ 设置 device_temperature: " + jsonNode.get("device_temperature").asText());
            }

            if (jsonNode.has("battery_level")) {
                transaction.setBatteryLevel(jsonNode.get("battery_level").asText());
                System.out.println("✅ 设置 battery_level: " + jsonNode.get("battery_level").asText());
            }

            if (jsonNode.has("latitude_and_longitude")) {
                transaction.setLatitudeAndLongitude(jsonNode.get("latitude_and_longitude").asText());
                System.out.println("✅ 设置 latitude_and_longitude: " + jsonNode.get("latitude_and_longitude").asText());
            }

            System.out.println("准备保存的数据: " + transaction.toString());

            // 保存到数据库
            int result = transactionRepository.insert(transaction);

            if (result > 0) {
                System.out.println("✅ 数据保存成功！交易ID: " + transaction.getTransactionId());
            } else {
                System.err.println("❌ 数据保存失败");
            }

        } catch (Exception e) {
            System.err.println("❌ 保存数据失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 从JSON中获取字符串字段
     */
    private String getStringFromJson(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asText() : null;
    }

    /**
     * 从JSON中获取Double字段
     */
    private Double getDoubleFromJson(JsonNode jsonNode, String fieldName) {
        return jsonNode.has(fieldName) ? jsonNode.get(fieldName).asDouble() : null;
    }

    /**
     * 从JSON中获取DateTime字段
     */
    private LocalDateTime getDateTimeFromJson(JsonNode jsonNode, String fieldName) {
        if (!jsonNode.has(fieldName)) {
            return null;
        }

        try {
            String dateTimeStr = jsonNode.get(fieldName).asText();

            // 方法1: 使用 DateTimeFormatter 解析
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(dateTimeStr, formatter);

            // 或者方法2: 如果格式不是标准的ISO格式，可以使用自定义格式
            // DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // return LocalDateTime.parse(dateTimeStr, customFormatter);

        } catch (Exception e) {
            System.err.println("Error parsing datetime field '" + fieldName + "': " + e.getMessage());
            return null;
        }
    }

}
