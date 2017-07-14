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
public class AddCategoryRequest implements ValidatedRequest {
        
        private String categoryName;
        
        public AddCategoryRequest() {
        }
        
        public AddCategoryRequest(String categoryName) {
                this.categoryName = categoryName;
        }

        @Override
        public boolean isValid() throws InvalidRequestException {
                return !categoryName.isEmpty();
        }

        public String getCategoryName() {
                return categoryName;
        }

        public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
        }
}
