/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.configuration.ControllerTest;
import com.sanoxy.controller.request.inventory.AddCategoryRequest;
import com.sanoxy.controller.request.inventory.AddInventoryRequest;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.repository.inventory.InventoryCategoryRepository;
import com.sanoxy.repository.inventory.InventoryRepository;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author davis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class InventoryControllerTest extends ControllerTest {
        
        @Autowired
        private InventoryRepository inventoryRepository;
        
        @Autowired
        private InventoryCategoryRepository inventoryCategoryRepository;
        
        private AddCategoryRequest[] genAddCategoryRequests(int n) {
                AddCategoryRequest[] requests = new AddCategoryRequest[n];
                for (int i = 0; i < n; i ++) {
                        requests[i] = new AddCategoryRequest(Integer.toString(i));
                }
                return requests;
        }
        
        private AddInventoryRequest[] genAddInventoryRequests(int n, InventoryCategory[] categories) {
                AddInventoryRequest[] requests = new AddInventoryRequest[n*categories.length];
                int i = 0;
                for (InventoryCategory category : categories) {
                        for (int j = 0; j < n; i++, j ++) {
                                requests[i] = new AddInventoryRequest();
                        }
                }
                return requests;
        }
        
        private List<InventoryCategory> getAllCategory() throws Exception {
                MvcResult result = mockMvc.perform(get("/api/access/category/get/"))
                                                .andExpect(status().isOk())
                                                .andReturn();
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(content, new TypeReference<List<InventoryCategory>>(){});
        }
        
        @Test
        @Rollback
        public void categoryTest() throws Exception {
                // add.
                AddCategoryRequest[] requests = genAddCategoryRequests(100);
                for (AddCategoryRequest request: requests) {
                        mockMvc.perform(post("/api/access/category/add")
                                .content(json(request))
                                .contentType(MEDIA_TYPE))
                               .andExpect(status().isOk());
                        List<InventoryCategory> c = inventoryCategoryRepository.findByCategoryName(request.getCategoryName());
                        assertTrue(c.size() == 1);
                }
                List<InventoryCategory> cs = getAllCategory();
                assertTrue(cs.size() == requests.length);
                
                // delete.
                for (InventoryCategory category: cs) {
                        mockMvc.perform(post("/api/access/category/delete/" + category.getCid()))
                               .andExpect(status().isOk());
                        List<InventoryCategory> c = inventoryCategoryRepository.findByCategoryName(category.getCategoryName());
                        assertTrue(c.isEmpty());
                }
                cs = getAllCategory();
                assertTrue(cs.isEmpty());
        }
        
        @Test
        @Rollback
        public void addInventoryTest() {
                
        }
}
