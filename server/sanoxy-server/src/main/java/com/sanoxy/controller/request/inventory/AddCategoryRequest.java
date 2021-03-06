
package com.sanoxy.controller.request.inventory;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.util.UserIdentity;


public class AddCategoryRequest extends ValidatedIdentifiedRequest {
        
        private String categoryName;
        
        public AddCategoryRequest() {
        }
        
        public AddCategoryRequest(UserIdentity identity, String categoryName) {
                super(identity);
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
