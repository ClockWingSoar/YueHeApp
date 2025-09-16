package com.yuehe.app.api.controller;

import com.yuehe.app.dto.ClientDetailDTO;
import com.yuehe.app.dto.ClientShopDTO;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping
    public BaseResponse<Page<ClientDetailDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            Page<ClientDetailDTO> clients = clientService.getAllClients(page, size, sortBy, sortDir);
            return BaseResponse.success(clients);
        } catch (Exception e) {
            return BaseResponse.error("获取客户列表失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public BaseResponse<ClientDetailDTO> getClientById(@PathVariable String id) {
        try {
            ClientDetailDTO client = clientService.getClientById(id);
            if (client != null) {
                return BaseResponse.success(client);
            } else {
                return BaseResponse.error("客户不存在");
            }
        } catch (Exception e) {
            return BaseResponse.error("获取客户信息失败: " + e.getMessage());
        }
    }
    
    @PostMapping
    public BaseResponse<ClientDetailDTO> createClient(@RequestBody ClientShopDTO clientShopDTO) {
        try {
            ClientDetailDTO client = clientService.createClient(clientShopDTO);
            return BaseResponse.success(client);
        } catch (Exception e) {
            return BaseResponse.error("创建客户失败: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public BaseResponse<ClientDetailDTO> updateClient(@PathVariable String id, @RequestBody ClientShopDTO clientShopDTO) {
        try {
            ClientDetailDTO client = clientService.updateClient(id, clientShopDTO);
            return BaseResponse.success(client);
        } catch (Exception e) {
            return BaseResponse.error("更新客户失败: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteClient(@PathVariable String id) {
        try {
            clientService.deleteClient(id);
            return BaseResponse.success(null);
        } catch (Exception e) {
            return BaseResponse.error("删除客户失败: " + e.getMessage());
        }
    }
}
