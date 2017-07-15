/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.controller.request.inventory;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.service.exception.InvalidRequestException;

/**
 *
 * @author luan
 */
public class AddCategoryRequest extends ValidatedIdentifiedRequest {
        
        private String categoryName;
        
        public AddCategoryRequest() {
        }
        
        public AddCategoryRequest(String categoryName) {
                this.categoryName = categoryName;
        }

        @Override
        public void validate() throws InvalidRequestException {
                super.validate();
                if (categoryName.isEmpty()) 
                        throw new InvalidRequestException("Category name is empty");
        }

        public String getCategoryName() {
                return categoryName;
        }

        public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
        }
}
