/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.controller.request.inventory;

import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;

/**
 *
 * @author luan
 */
public class AddItemRequest implements ValidatedRequest {
        
        private String id;
        private String category_name;
        private String inventory;

        @Override
        public boolean isValid() throws InvalidRequestException {
                return true;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getCategory_name() {
                return category_name;
        }

        public void setCategory_name(String category_name) {
                this.category_name = category_name;
        }

        public String getInventory() {
                return inventory;
        }

        public void setInventory(String inventory) {
                this.inventory = inventory;
        }
}
