
package com.sanoxy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.controller.request.inventory.AddCategoryRequest;
import com.sanoxy.controller.request.inventory.AddInventoryRequest;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.service.InventoryService;
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
        private InventoryService inventoryService;
        
        @Autowired
        private SecurityService securityService;

        /*
	 * Get all the categories
         */
        @RequestMapping(value = {"/category/get", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Collection<InventoryCategory> 
        getInventoryCategories(@RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                       PermissionDeniedException,
                                                                                       ResourceMissingException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.ReadCategory.getPermission());
                return inventoryService.getInventoryCategories(request.getUserIdentity());
        }
        
        @RequestMapping(value = {"/category/add", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response addCategory(@RequestBody AddCategoryRequest request) throws InvalidRequestException, 
                                                                                    PermissionDeniedException,
                                                                                    ResourceMissingException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.CreateCategory.getPermission());
                if (inventoryService.addInventoryCategory(request.getUserIdentity(), request.getCategoryName()))
                        return new Response(Status.Success);
                else
                        return new Response(Status.Failed);
        }
        
        @RequestMapping(value = {"/category/delete/{categoryId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response deleteCategory(@PathVariable("categoryId") Integer categoryId, 
                                       @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                               PermissionDeniedException,
                                                                                               ResourceMissingException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.DeleteCategory.getPermission());
                
                if (categoryId == null)
                        throw new InvalidRequestException("categoryId is missing");
                
                if (inventoryService.deleteInventoryCategory(request.getUserIdentity(), categoryId))
                        return new Response(Status.Success);
                else
                        return new Response(Status.Failed);
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
                                                                                                            PermissionDeniedException,
                                                                                                            ResourceMissingException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.ReadInventory.getPermission());
                return inventoryService.getInventories(request.getUserIdentity(), categoryId, startIndex, endIndex);
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
                if (inventoryService.addInventory(request.getUserIdentity(), categoryId, request.asInventory()))
                        return new Response(Status.Success);
                else
                        return new Response(Status.Failed);                
        }
        
        @RequestMapping(value = {"/inventory/delete/{inventoryId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response deleteInventory(@PathVariable("inventoryId") Integer inventoryId,
                                        @RequestBody AddInventoryRequest request) throws InvalidRequestException, 
                                                                                         PermissionDeniedException,
                                                                                         ResourceMissingException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), WorkspacePermission.DeleteInventory.getPermission());
                
                if (inventoryId == null)
                        throw new InvalidRequestException("inventoryId is missing");
                
                if (inventoryService.deleteInventory(request.getUserIdentity(), inventoryId))
                        return new Response(Status.Success);
                else
                        return new Response(Status.Failed);
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
                return inventoryService.search(request.getUserIdentity(), keyword);
        }
}
