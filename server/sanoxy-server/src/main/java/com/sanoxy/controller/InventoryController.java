/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.controller;

import com.sanoxy.controller.request.inventory.AddItemRequest;
import com.sanoxy.controller.request.inventory.GetCategoryRequest;
import com.sanoxy.controller.request.inventory.GetItemRequest;
import com.sanoxy.controller.response.CategoryDetail;
import com.sanoxy.controller.response.ItemDetail;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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

        @RequestMapping(value = {"/categories", ""}, method = RequestMethod.GET)
        @ResponseBody
        public CategoryDetail getInventoryCategories(@RequestBody GetCategoryRequest request) throws InvalidRequestException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                // TODO: implement this
                return new CategoryDetail();
        }

        @RequestMapping(value = {"/category/items", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Collection<ItemDetail> getInventoryCategories(@RequestBody GetItemRequest request) throws InvalidRequestException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                // TODO: implement this
                List<ItemDetail> result = new ArrayList();
                return result;
        }

        @RequestMapping(value = {"/category/item/add", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response getInventoryCategories(@RequestBody AddItemRequest request) throws InvalidRequestException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                // TODO: implement this
                return new Response(Status.Success);
        }
}
