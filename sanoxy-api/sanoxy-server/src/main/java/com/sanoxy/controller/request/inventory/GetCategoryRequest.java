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
public class GetCategoryRequest implements ValidatedRequest {
        
        private String id;
        
        @Override
        public boolean isValid() throws InvalidRequestException {
                if (id == null || id.isEmpty()) {
                        throw new InvalidRequestException("inventory id is invalid");
                }
                return true;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }
}
