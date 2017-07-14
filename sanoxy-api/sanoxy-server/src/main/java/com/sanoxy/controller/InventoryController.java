/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.controller;

import com.sanoxy.controller.request.inventory.AddInventoryRequest;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import com.sanoxy.controller.service.exception.ResourceMissingException;
import com.sanoxy.dao.inventory.Inventory;
import com.sanoxy.dao.inventory.InventoryCategory;
import com.sanoxy.repository.inventory.InventoryCategoryRepository;
import com.sanoxy.repository.inventory.InventoryRepository;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author luan
 */
@Controller
@RequestMapping(value = "api/inventory", produces = {MediaType.APPLICATION_JSON_VALUE})
public class InventoryController {

		@Autowired
		private InventoryCategoryRepository inventoryCategoryRepository;
		@Autowired
		private InventoryRepository inventoryRepository;
	
		/*
		 * Get all the categories
		 */
        @RequestMapping(value = {"/categories", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Collection<String> getInventoryCategories() throws InvalidRequestException {
        		Collection<String> categories = inventoryCategoryRepository.findAllCategoryNames();
                return categories;
        }

        /*
         * Get all the inventories in a certain category
         */
        @RequestMapping(value = {"/{categoryId}/{startIndex}/{endIndex}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Collection<Inventory> getInventories(@PathVariable("categoryId") Integer categoryId, 
						    @PathVariable("startIndex") Integer startIndex, 
						    @PathVariable("endIndex") Integer endIndex) throws InvalidRequestException {
        		Integer numOfRowsToShow = endIndex - startIndex;
                Collection<Inventory> inventories = inventoryRepository.findAllInventoryItemsByCategoryId(categoryId, startIndex, numOfRowsToShow);
                System.out.println(inventories);
                return inventories;
        }

        /*
         * Add new inventory into a certain category
         */
        @RequestMapping(value = {"/{categoryId}/add", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response getInventoryCategories(@PathVariable("categoryId") Integer categoryId, 
        				       @RequestBody AddInventoryRequest request) throws InvalidRequestException, ResourceMissingException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                InventoryCategory inventoryCategory = inventoryCategoryRepository.findByCid(categoryId);
                if (inventoryCategory == null) {
                	throw new ResourceMissingException("Category does not exist");
                }
                Inventory inventory = request.asInventory();
                inventory.setInventoryCategory(inventoryCategory);
                inventoryRepository.save(inventory);

                return new Response(Status.Success);
        }
}
