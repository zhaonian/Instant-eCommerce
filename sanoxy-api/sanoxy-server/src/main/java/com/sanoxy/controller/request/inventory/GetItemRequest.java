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
public class GetItemRequest implements ValidatedRequest {

        private String id; // what id?
        private Integer startIndex;
        private Integer endIndex;

        @Override
        public boolean isValid() throws InvalidRequestException {
                if (id == null || id.isEmpty()) {
                        throw new InvalidRequestException("id is invalid");
                }
                if (startIndex == null) {
                        throw new InvalidRequestException("start index is invalid");
                }
                if (endIndex == null) {
                        throw new InvalidRequestException("end index is invalid");
                }
                return true;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public Integer getStartIndex() {
                return startIndex;
        }

        public void setStartIndex(Integer startIndex) {
                this.startIndex = startIndex;
        }

        public Integer getEndIndex() {
                return endIndex;
        }

        public void setEndIndex(Integer endIndex) {
                this.endIndex = endIndex;
        }
}
