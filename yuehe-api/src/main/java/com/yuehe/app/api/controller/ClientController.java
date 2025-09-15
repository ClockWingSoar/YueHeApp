package com.yuehe.app.api.controller;

import com.yuehe.app.entity.Client;
import com.yuehe.app.service.ClientService;
import com.yuehe.app.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<BaseResponse<Page<Client>>> getClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String shopId) {
        
        try {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<Client> clients;
            if (name != null || gender != null || shopId != null) {
                // 这里应该实现搜索逻辑
                clients = clientService.getAllClients(pageable);
            } else {
                clients = clientService.getAllClients(pageable);
            }
            
            return ResponseEntity.ok(BaseResponse.success(clients));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("获取客户列表失败: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Client>> getClient(@PathVariable String id) {
        try {
            Client client = clientService.getById(id);
            if (client != null) {
                return ResponseEntity.ok(BaseResponse.success(client));
            } else {
                return ResponseEntity.ok(BaseResponse.error("客户不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("获取客户详情失败: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Client>> createClient(@Valid @RequestBody CreateClientRequest request) {
        try {
            Client client = new Client();
            client.setName(request.getName());
            client.setAge(request.getAge());
            client.setGender(request.getGender());
            client.setSymptom(request.getSymptom());
            // 这里需要设置cosmeticShop，暂时跳过
            
            Client savedClient = clientService.create(client);
            return ResponseEntity.ok(BaseResponse.success(savedClient));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("创建客户失败: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Client>> updateClient(@PathVariable String id, @Valid @RequestBody UpdateClientRequest request) {
        try {
            Client client = clientService.getById(id);
            if (client == null) {
                return ResponseEntity.ok(BaseResponse.error("客户不存在"));
            }
            
            if (request.getName() != null) client.setName(request.getName());
            if (request.getAge() != null) client.setAge(request.getAge());
            if (request.getGender() != null) client.setGender(request.getGender());
            if (request.getSymptom() != null) client.setSymptom(request.getSymptom());
            
            Client updatedClient = clientService.create(client);
            return ResponseEntity.ok(BaseResponse.success(updatedClient));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("更新客户失败: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteClient(@PathVariable String id) {
        try {
            clientService.deleteById(id);
            return ResponseEntity.ok(BaseResponse.success("删除成功"));
        } catch (Exception e) {
            return ResponseEntity.ok(BaseResponse.error("删除客户失败: " + e.getMessage()));
        }
    }

    public static class CreateClientRequest {
        private String name;
        private Integer age;
        private String gender;
        private String symptom;
        private String shopId;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public String getSymptom() { return symptom; }
        public void setSymptom(String symptom) { this.symptom = symptom; }
        public String getShopId() { return shopId; }
        public void setShopId(String shopId) { this.shopId = shopId; }
    }

    public static class UpdateClientRequest {
        private String name;
        private Integer age;
        private String gender;
        private String symptom;
        private String shopId;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public String getSymptom() { return symptom; }
        public void setSymptom(String symptom) { this.symptom = symptom; }
        public String getShopId() { return shopId; }
        public void setShopId(String shopId) { this.shopId = shopId; }
    }
}
