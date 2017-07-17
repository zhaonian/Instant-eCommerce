
package com.sanoxy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.controller.request.inventory.AddCategoryRequest;
import com.sanoxy.controller.request.inventory.AddInventoryRequest;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.repository.inventory.InventoryCategoryRepository;
import com.sanoxy.repository.inventory.InventoryRepository;
import com.sanoxy.service.SecurityService;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.WorkspacePermission;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "api/access", produces = {MediaType.APPLICATION_JSON_VALUE})
public class InventoryController {

        @Autowired
        private InventoryCategoryRepository inventoryCategoryRepository;
        @Autowired
        private InventoryRepository inventoryRepository;
        @Autowired
        private SecurityService securityService;

        /*
	 * Get all the categories
         */
        @RequestMapping(value = {"/category/get", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Collection<InventoryCategory> getInventoryCategories(@RequestBody ValidatedIdentifiedRequest request) 
                                                                                                        throws InvalidRequestException, 
                                                                                                               PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.ReadCategory.getPermission());
                Collection<InventoryCategory> categories = inventoryCategoryRepository.findAllByOrderByCategoryNameAsc();
                return categories;
        }
        
        @RequestMapping(value = {"/category/add", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response addCategory(@RequestBody AddCategoryRequest request) throws InvalidRequestException, 
                                                                                    PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.CreateCategory.getPermission());
                
                InventoryCategory category = new InventoryCategory(request.getCategoryName());
                inventoryCategoryRepository.save(category);
                
                return new Response(Status.Success);
        }
        
        @RequestMapping(value = {"/category/delete/{categoryId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response deleteCategory(@PathVariable("categoryId") Integer categoryId, 
                                       @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                               PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.DeleteCategory.getPermission());
                
                if (categoryId == null) {
                        throw new InvalidRequestException("categoryId is missing");
                }
                if (1 != inventoryCategoryRepository.deleteByCid(categoryId))
                        return new Response(Status.Failed);
              
                return new Response(Status.Success);
        }

        /*
         * Get all the inventories in a certain category
         */
        @RequestMapping(value = {"/inventory/get/{categoryId}/{startIndex}/{endIndex}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Collection<Inventory> getInventories(@PathVariable("categoryId") Integer categoryId,
                                                    @PathVariable("startIndex") Integer startIndex,
                                                    @PathVariable("endIndex") Integer endIndex,
                                                    @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException,               
                                                                                                            PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.ReadInventory.getPermission());
                
                Integer numOfRowsToShow = endIndex - startIndex;
                Collection<Inventory> inventories = inventoryRepository.findAllInventoryItemsByCategoryId(categoryId, startIndex, numOfRowsToShow);
                return inventories;
        }
        
        @RequestMapping(value = {"/inventory/get/{inventoryId}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Inventory getInventory(@PathVariable("inventoryId") Integer inventoryId,
                                      @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                              PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.ReadInventory.getPermission());
                return null;
        }

        /*
         * Add new inventory into a certain category
         */
        @RequestMapping(value = {"/inventory/add/{categoryId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response addInventory(@PathVariable("categoryId") Integer categoryId, 
                                     @RequestBody AddInventoryRequest request) throws InvalidRequestException, 
                                                                                      ResourceMissingException, 
                                                                                      JsonProcessingException,
                                                                                      PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.CreateInventory.getPermission());
                
                InventoryCategory inventoryCategory = inventoryCategoryRepository.findByCid(categoryId);
                if (inventoryCategory == null) {
                        throw new ResourceMissingException("Category does not exist");
                }
                Inventory inventory = request.asInventory(inventoryCategory);
                inventory.setInventoryCategory(inventoryCategory);
                inventoryRepository.save(inventory);

                return new Response(Status.Success);
        }
        
        @RequestMapping(value = {"/inventory/delete/{inventoryId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response deleteInventory(@PathVariable("inventoryId") Integer inventoryId,
                                        @RequestBody AddInventoryRequest request) throws InvalidRequestException, 
                                                                                         PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.DeleteInventory.getPermission());
                
                if (inventoryId == null) {
                        throw new InvalidRequestException("inventoryId is missing");
                }
                if (1 != inventoryRepository.deleteById(inventoryId))
                        return new Response(Status.Failed);
             
                return new Response(Status.Success);
        }
        
        @RequestMapping(value = {"/inventory/search/{keyword}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Collection<Inventory> findInventory(@PathVariable("keyword") String keyword,
                                                   @RequestBody AddInventoryRequest request) throws InvalidRequestException, 
                                                                                                    ResourceMissingException, 
                                                                                                    PermissionDeniedException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.ReadInventory.getPermission());
                
                if (keyword == null || keyword.isEmpty())
                        throw new InvalidRequestException("Keyword can't be empty.");
                
                return inventoryRepository.searchInventoryByKeyword(keyword);
        }
}
