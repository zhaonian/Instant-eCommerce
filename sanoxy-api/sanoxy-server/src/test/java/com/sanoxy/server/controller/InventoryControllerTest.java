
package com.sanoxy.server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.controller.request.inventory.AddCategoryRequest;
import com.sanoxy.controller.request.inventory.AddInventoryRequest;
import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.dao.user.Workspace;
import com.sanoxy.repository.inventory.InventoryCategoryRepository;
import com.sanoxy.repository.inventory.InventoryRepository;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.UserIdentity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
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


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class InventoryControllerTest extends SanoxyControllerTest {
        
        @Autowired
        private InventoryRepository inventoryRepository;
        
        @Autowired
        private InventoryCategoryRepository inventoryCategoryRepository;
        
        private AddCategoryRequest[] genAddCategoryRequests(UserIdentity identity, int n) {
                AddCategoryRequest[] requests = new AddCategoryRequest[n];
                for (int i = 0; i < n; i ++) {
                        requests[i] = new AddCategoryRequest(identity, Integer.toString(i));
                }
                return requests;
        }
        
        private AddInventoryRequest[] genAddInventoryRequests(UserIdentity identity, int n, Collection<InventoryCategory> categories) throws IOException {
                String[] titles = {"Testing titles", 
                                   "Titles Can Be (And Should Be) Extremely Long", 
                                   "Every Title Is Allowed 250 Characters",
                                   "I Can Include Any Information I Want In My Title",
                                   "The Title Only Impacts The Word Search Results And The Product Listing"};
                
                AddInventoryRequest[] requests = new AddInventoryRequest[n*categories.size()];
                Random rng = new Random(10);
                byte[] img = Files.readAllBytes(Paths.get("res/horse2.jpg"));
                byte[] img2 = Files.readAllBytes(Paths.get("res/zebra.jpg"));
                int i = 0;
                for (InventoryCategory category : categories) {
                        for (int j = 0; j < n; i++, j ++) {
                                requests[i] = new AddInventoryRequest(identity, 
                                        rng.nextFloat()*10, 
                                        Integer.toString(rng.nextInt()),
                                        titles[rng.nextInt(1000)%titles.length],
                                        "Concox",
                                        "Brief description",
                                        "Electronics",
                                        "Tablets",
                                        new ArrayList() {{ this.add("Good quality"); this.add("Lowest price"); }},
                                        "Random item",
                                        new ArrayList<byte[]>() {{ this.add(img); this.add(img2); }});
                        }
                }
                return requests;
        }
        
        private void requestAddCategories(UserIdentity identity, int n) throws Exception {
                Workspace workspace = getLoggedInWorkspace(identity);
                
                AddCategoryRequest[] requests = genAddCategoryRequests(identity, n);
                for (AddCategoryRequest request: requests) {
                        mockMvc.perform(post("/api/access/category/add")
                                .content(json(request))
                                .contentType(MEDIA_TYPE))
                               .andExpect(status().isOk());
                        Collection<InventoryCategory> c = 
                                inventoryCategoryRepository.findByWorkspaceWidAndCategoryName(workspace.getWid(), request.getCategoryName());
                        assertTrue(c.size() == 1);
                }
        }
        
        private void requestDeleteCategories(UserIdentity identity, Collection<InventoryCategory> categories) throws Exception {
                Workspace workspace = getLoggedInWorkspace(identity);
                
                for (InventoryCategory category: categories) {
                        mockMvc.perform(post("/api/access/category/delete/" + category.getCid())
                               .content(json(new ValidatedIdentifiedRequest(identity)))
                               .contentType(MEDIA_TYPE))
                               .andExpect(status().isOk());
                        Collection<InventoryCategory> c = 
                                inventoryCategoryRepository.findByWorkspaceWidAndCategoryName(workspace.getWid(), category.getCategoryName());
                        assertTrue(c.isEmpty());
                }
        }
        
        private List<InventoryCategory> requestGetAllCategory(UserIdentity identity) throws Exception {
                MvcResult result = mockMvc.perform(get("/api/access/category/get")
                                                .content(json(new ValidatedIdentifiedRequest(identity)))
                                                .contentType(MEDIA_TYPE))
                                                .andExpect(status().isOk())
                                                .andReturn();
                String content = result.getResponse().getContentAsString();
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(content, new TypeReference<List<InventoryCategory>>(){});
        }
        
        private void requestAddAllInventories(UserIdentity identity, int n, int m) throws Exception {
                requestAddCategories(identity, n);
                List<InventoryCategory> cs = requestGetAllCategory(identity);
                
                AddInventoryRequest[] requests = genAddInventoryRequests(identity, m, cs);
                for (int i = 0; i < requests.length; i ++) {
                        InventoryCategory category = cs.get(i/m);
                        MvcResult result = mockMvc.perform(post("/api/access/inventory/add/" + category.getCid())
                                .content(json(requests[i]))
                                .contentType(MEDIA_TYPE))
                                .andExpect(status().isOk())
                                .andReturn();
                        
                        Inventory inventory = responseToInventory(result);
                        assertTrue(requests[i].getTitle().equals(inventory.getTitle()));
                        
                        mockMvc.perform(get("/api/access/inventory/get/image/" + inventory.getIid())
                                .content(json(new ValidatedIdentifiedRequest(identity)))
                                .contentType(MEDIA_TYPE))
                                .andExpect(status().isOk());
                }
        }
        
        @Test
        @Rollback
        public void categoryTest() throws Exception {
                final int n = 100;
                
                requestNewUser();
                UserIdentity iid = requestNewUserLogin();
                IdentityInfo rootInfo = requestNewRootUser(iid, "sanoxy");
                UserIdentity rootIId = requestUserLogin(rootInfo.getUser().getName(), rootInfo.getRawPasscode(), "sanoxy");
                
                // add.
                requestAddCategories(rootIId, n);
                List<InventoryCategory> cs = requestGetAllCategory(rootIId);
                assertTrue(cs.size() == n);
                
                // delete.
                requestDeleteCategories(rootIId, cs);
                cs = requestGetAllCategory(rootIId);
                assertTrue(cs.isEmpty());
        }
        
        @Test
        @Rollback
        public void addInventoryTest() throws Exception {
                final int n = 10;
                final int m = 5;
                
                requestNewUser();
                UserIdentity iid = requestNewUserLogin();
                IdentityInfo rootInfo = requestNewRootUser(iid, "sanoxy");
                UserIdentity rootIId = requestUserLogin(rootInfo.getUser().getName(), rootInfo.getRawPasscode(), "sanoxy");
                
                requestAddAllInventories(rootIId, n, m);
        }
}
