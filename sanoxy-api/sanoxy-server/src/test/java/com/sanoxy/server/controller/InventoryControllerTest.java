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
import java.util.Collection;
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
        
        private AddInventoryRequest[] genAddInventoryRequests(int n, Collection<InventoryCategory> categories) {
                AddInventoryRequest[] requests = new AddInventoryRequest[n*categories.size()];
                int i = 0;
                for (InventoryCategory category : categories) {
                        for (int j = 0; j < n; i++, j ++) {
                                requests[i] = new AddInventoryRequest();
                        }
                }
                return requests;
        }
        
        private void requestAddCategories(int n) throws Exception {
                AddCategoryRequest[] requests = genAddCategoryRequests(n);
                for (AddCategoryRequest request: requests) {
                        mockMvc.perform(post("/api/access/category/add")
                                .content(json(request))
                                .contentType(MEDIA_TYPE))
                               .andExpect(status().isOk());
                        List<InventoryCategory> c = inventoryCategoryRepository.findByCategoryName(request.getCategoryName());
                        assertTrue(c.size() == 1);
                }
        }
        
        private void requestDeleteCategories(Collection<InventoryCategory> categories) throws Exception {
                for (InventoryCategory category: categories) {
                        mockMvc.perform(post("/api/access/category/delete/" + category.getCid()))
                               .andExpect(status().isOk());
                        List<InventoryCategory> c = inventoryCategoryRepository.findByCategoryName(category.getCategoryName());
                        assertTrue(c.isEmpty());
                }
        }
        
        private List<InventoryCategory> requestGetAllCategory() throws Exception {
                MvcResult result = mockMvc.perform(get("/api/access/category/get/"))
                                                .andExpect(status().isOk())
                                                .andReturn();
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(content, new TypeReference<List<InventoryCategory>>(){});
        }
        
        private void requestAddAllInventories(int n, int m) throws Exception {
                requestAddCategories(n);
                List<InventoryCategory> cs = requestGetAllCategory();
                
                AddInventoryRequest[] requests = genAddInventoryRequests(m, cs);
                for (int i = 0; i < requests.length; i ++) {
                        InventoryCategory category = cs.get(i/m);
                        mockMvc.perform(post("api/access/inventory/add/" + category.getCid()))
                                .andExpect(status().isOk());
                }
        }
        
        @Test
        @Rollback
        public void categoryTest() throws Exception {
                final int n = 100;
                
                // add.
                requestAddCategories(n);
                List<InventoryCategory> cs = requestGetAllCategory();
                assertTrue(cs.size() == n);
                
                // delete.
                requestDeleteCategories(cs);
                cs = requestGetAllCategory();
                assertTrue(cs.isEmpty());
        }
        
        @Test
        @Rollback
        public void addInventoryTest() throws Exception {
                final int n = 10;
                final int m = 100;
                
                requestAddAllInventories(n, m);
        }
}
