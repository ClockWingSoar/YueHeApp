package com.yuehe.app.api.controller;

import com.yuehe.app.dto.SaleDetailDTO;
import com.yuehe.app.dto.SaleCreateOrAdjustDTO;
import com.yuehe.app.service.SaleService;
import com.yuehe.app.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SaleController {
    
    @Autowired
    private SaleService saleService;
    
    @GetMapping
    public BaseResponse<Page<SaleDetailDTO>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Page<SaleDetailDTO> sales = saleService.getAllSales(page, size, sortBy, sortDir);
            return BaseResponse.success(sales);
        } catch (Exception e) {
            return BaseResponse.error("获取销售列表失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public BaseResponse<SaleDetailDTO> getSaleById(@PathVariable String id) {
        try {
            SaleDetailDTO sale = saleService.getSaleById(id);
            if (sale != null) {
                return BaseResponse.success(sale);
            } else {
                return BaseResponse.error("销售记录不存在");
            }
        } catch (Exception e) {
            return BaseResponse.error("获取销售信息失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    public BaseResponse<SaleDetailDTO> createSale(@RequestBody SaleCreateOrAdjustDTO saleCreateOrAdjustDTO) {
        try {
            SaleDetailDTO sale = saleService.createSale(saleCreateOrAdjustDTO);
            return BaseResponse.success(sale);
        } catch (Exception e) {
            return BaseResponse.error("创建销售记录失败: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public BaseResponse<SaleDetailDTO> updateSale(@PathVariable String id, @RequestBody SaleCreateOrAdjustDTO saleCreateOrAdjustDTO) {
        try {
            SaleDetailDTO sale = saleService.updateSale(id, saleCreateOrAdjustDTO);
            return BaseResponse.success(sale);
        } catch (Exception e) {
            return BaseResponse.error("更新销售记录失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteSale(@PathVariable String id) {
        try {
            saleService.deleteSale(id);
            return BaseResponse.success(null);
        } catch (Exception e) {
            return BaseResponse.error("删除销售记录失败: " + e.getMessage());
        }
    }
}
