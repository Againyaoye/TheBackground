package com.example.demo02.controller;

import com.example.demo02.domain.Transaction;
import com.example.demo02.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {


    @Autowired
    private TransactionMapper transactionMapper;

    // 获取交易记录（带分页参数）
    @GetMapping
    public ResponseEntity<Map<String, Object>> getTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            int offset = (page - 1) * size;

            List<Transaction> transactions = transactionMapper.findWithPagination(offset, size);
            int totalCount = transactionMapper.getTotalCount();

            Map<String, Object> response = new HashMap<>();
            response.put("total", totalCount);
            response.put("page", page);
            response.put("size", size);
            response.put("data", transactions);
            response.put("totalPages", (int) Math.ceil((double) totalCount / size));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据交易ID获取交易详情
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String transactionId) {
        try {
            Transaction transaction = transactionMapper.findByTransactionId(transactionId);
            if (transaction != null) {
                return ResponseEntity.ok(transaction);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据用户ID获取交易记录
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable String userId) {
        try {
            List<Transaction> transactions = transactionMapper.findByUserId(userId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 根据设备ID获取交易记录
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Transaction>> getTransactionsByMachineId(@PathVariable String machineId) {
        try {
            List<Transaction> transactions = transactionMapper.findByMachineId(machineId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 创建交易记录（开始交易）
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            // 检查交易ID是否已存在
            if (transactionMapper.existsByTransactionId(transaction.getTransactionId()) > 0) {
                return ResponseEntity.badRequest().body("交易ID已存在");
            }

            int result = transactionMapper.insert(transaction);
            if (result > 0) {
                return ResponseEntity.ok("交易记录创建成功");
            } else {
                return ResponseEntity.badRequest().body("交易记录创建失败");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("服务器错误: " + e.getMessage());
        }
    }

    // 更新交易记录
    @PutMapping("/{transactionId}")
    public ResponseEntity<?> updateTransaction(@PathVariable String transactionId, @RequestBody Transaction transaction) {
        try {
            // 确保URL中的ID与请求体中的ID一致
            if (!transactionId.equals(transaction.getTransactionId())) {
                return ResponseEntity.badRequest().body("交易ID不匹配");
            }

            // 检查交易记录是否存在
            if (transactionMapper.existsByTransactionId(transactionId) == 0) {
                return ResponseEntity.notFound().build();
            }

            int result = transactionMapper.update(transaction);
            if (result > 0) {
                return ResponseEntity.ok("交易记录更新成功");
            } else {
                return ResponseEntity.badRequest().body("交易记录更新失败");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("服务器错误: " + e.getMessage());
        }
    }



    // 完成交易（更新结束信息）
    @PatchMapping("/{transactionId}/complete")
    public ResponseEntity<?> completeTransaction(@PathVariable String transactionId, @RequestBody Map<String, Object> request) {
        try {
            Double totalLiters = (Double) request.get("totalLiters");
            Double finalAmount = (Double) request.get("finalAmount");
            String endTime = (String) request.get("endTime");
            String orderStatus = (String) request.get("orderStatus");

            if (totalLiters == null || finalAmount == null || endTime == null || orderStatus == null) {
                return ResponseEntity.badRequest().body("所有结束参数都不能为空");
            }

            // 检查交易记录是否存在
            if (transactionMapper.existsByTransactionId(transactionId) == 0) {
                return ResponseEntity.notFound().build();
            }

            int result = transactionMapper.completeTransaction(transactionId, totalLiters, finalAmount, endTime, orderStatus);
            if (result > 0) {
                return ResponseEntity.ok("交易完成信息更新成功");
            } else {
                return ResponseEntity.badRequest().body("交易完成信息更新失败");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("服务器错误: " + e.getMessage());
        }
    }





}
