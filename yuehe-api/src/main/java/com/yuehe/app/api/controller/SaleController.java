package com.yuehe.app.api.controller;

import com.yuehe.app.entity.Sale;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public ResponseEntity<BaseResponse<Page<Sale>>> getSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        try {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<Sale> sales;
            if (clientName != null || employeeName != null || startDate != null || endDate != null) {
                // 这里应该实现搜索逻辑
                sales = saleService.getAllSales(pageable);
            } else {
                sales = saleService.getAllSales(pageable);
            }
            
            return ResponseEntity.ok(BaseResponse.success(sales));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("获取销售列表失败: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Sale>> getSale(@PathVariable String id) {
        try {
            Sale sale = saleService.getById(id);
            if (sale != null) {
                return ResponseEntity.ok(BaseResponse.success(sale));
            } else {
                return ResponseEntity.ok(BaseResponse.error("销售记录不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("获取销售详情失败: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Sale>> createSale(@Valid @RequestBody CreateSaleRequest request) {
        try {
            Sale sale = new Sale();
            sale.setItemNumber(request.getItemNumber());
            sale.setCreateCardTotalAmount(request.getCreateCardTotalAmount());
            sale.setReceivedAmount(request.getReceivedAmount());
            sale.setReceivedEarnedAmount(request.getReceivedEarnedAmount());
            sale.setCreateCardDate(request.getCreateCardDate());
            sale.setDescription(request.getDescription());
            // 这里需要设置关联的client, employee, beautifySkinItem，暂时跳过
            
            Sale savedSale = saleService.create(sale);
            return ResponseEntity.ok(BaseResponse.success(savedSale));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("创建销售记录失败: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Sale>> updateSale(@PathVariable String id, @Valid @RequestBody UpdateSaleRequest request) {
        try {
            Sale sale = saleService.getById(id);
            if (sale == null) {
                return ResponseEntity.ok(BaseResponse.error("销售记录不存在"));
            }
            
            if (request.getItemNumber() != null) sale.setItemNumber(request.getItemNumber());
            if (request.getCreateCardTotalAmount() != null) sale.setCreateCardTotalAmount(request.getCreateCardTotalAmount());
            if (request.getReceivedAmount() != null) sale.setReceivedAmount(request.getReceivedAmount());
            if (request.getReceivedEarnedAmount() != null) sale.setReceivedEarnedAmount(request.getReceivedEarnedAmount());
            if (request.getCreateCardDate() != null) sale.setCreateCardDate(request.getCreateCardDate());
            if (request.getDescription() != null) sale.setDescription(request.getDescription());
            
            Sale updatedSale = saleService.create(sale);
            return ResponseEntity.ok(BaseResponse.success(updatedSale));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("更新销售记录失败: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteSale(@PathVariable String id) {
        try {
            saleService.deleteById(id);
            return ResponseEntity.ok(BaseResponse.success("删除成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("删除销售记录失败: " + e.getMessage()));
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getSalesSummary(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            // 这里应该实现汇总统计逻辑
            Map<String, Object> summary = new HashMap<>();
            summary.put("totalAmount", 0);
            summary.put("receivedAmount", 0);
            summary.put("totalCount", 0);
            
            return ResponseEntity.ok(BaseResponse.success(summary));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("获取销售汇总失败: " + e.getMessage()));
        }
    }

    public static class CreateSaleRequest {
        private String clientId;
        private String beautifySkinItemId;
        private String sellerId;
        private Integer itemNumber;
        private Long createCardTotalAmount;
        private Long receivedAmount;
        private Long receivedEarnedAmount;
        private String createCardDate;
        private String description;

        // Getters and setters
        public String getClientId() { return clientId; }
        public void setClientId(String clientId) { this.clientId = clientId; }
        public String getBeautifySkinItemId() { return beautifySkinItemId; }
        public void setBeautifySkinItemId(String beautifySkinItemId) { this.beautifySkinItemId = beautifySkinItemId; }
        public String getSellerId() { return sellerId; }
        public void setSellerId(String sellerId) { this.sellerId = sellerId; }
        public Integer getItemNumber() { return itemNumber; }
        public void setItemNumber(Integer itemNumber) { this.itemNumber = itemNumber; }
        public Long getCreateCardTotalAmount() { return createCardTotalAmount; }
        public void setCreateCardTotalAmount(Long createCardTotalAmount) { this.createCardTotalAmount = createCardTotalAmount; }
        public Long getReceivedAmount() { return receivedAmount; }
        public void setReceivedAmount(Long receivedAmount) { this.receivedAmount = receivedAmount; }
        public Long getReceivedEarnedAmount() { return receivedEarnedAmount; }
        public void setReceivedEarnedAmount(Long receivedEarnedAmount) { this.receivedEarnedAmount = receivedEarnedAmount; }
        public String getCreateCardDate() { return createCardDate; }
        public void setCreateCardDate(String createCardDate) { this.createCardDate = createCardDate; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class UpdateSaleRequest {
        private String clientId;
        private String beautifySkinItemId;
        private String sellerId;
        private Integer itemNumber;
        private Long createCardTotalAmount;
        private Long receivedAmount;
        private Long receivedEarnedAmount;
        private String createCardDate;
        private String description;

        // Getters and setters
        public String getClientId() { return clientId; }
        public void setClientId(String clientId) { this.clientId = clientId; }
        public String getBeautifySkinItemId() { return beautifySkinItemId; }
        public void setBeautifySkinItemId(String beautifySkinItemId) { this.beautifySkinItemId = beautifySkinItemId; }
        public String getSellerId() { return sellerId; }
        public void setSellerId(String sellerId) { this.sellerId = sellerId; }
        public Integer getItemNumber() { return itemNumber; }
        public void setItemNumber(Integer itemNumber) { this.itemNumber = itemNumber; }
        public Long getCreateCardTotalAmount() { return createCardTotalAmount; }
        public void setCreateCardTotalAmount(Long createCardTotalAmount) { this.createCardTotalAmount = createCardTotalAmount; }
        public Long getReceivedAmount() { return receivedAmount; }
        public void setReceivedAmount(Long receivedAmount) { this.receivedAmount = receivedAmount; }
        public Long getReceivedEarnedAmount() { return receivedEarnedAmount; }
        public void setReceivedEarnedAmount(Long receivedEarnedAmount) { this.receivedEarnedAmount = receivedEarnedAmount; }
        public String getCreateCardDate() { return createCardDate; }
        public void setCreateCardDate(String createCardDate) { this.createCardDate = createCardDate; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
