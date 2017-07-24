
package com.sanoxy.controller.request.inventory;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.util.UserIdentity;
import java.util.ArrayList;
import java.util.List;


public class AddInventoryRequest extends ValidatedIdentifiedRequest {

	private Float suggestPrice;
	private String ean;
	private String title;
	private String brand;
	private String description;
	private String amazonItemType;
	private String amazonProductType;
	private List<String> bullets = new ArrayList();
	private String keyword;
	private List<String> imageUrls = new ArrayList();
        
        public AddInventoryRequest() {
        }
        
        public AddInventoryRequest(UserIdentity identity,
                                   Float suggestPrice,
                                   String ean,
                                   String title,
                                   String brand,
                                   String description,
                                   String amazonItemType,
                                   String amazonProductType,
                                   List<String> bullets,
                                   String keyword,
                                   List<String> imageUrls) {
                super(identity);
                this.suggestPrice = suggestPrice;
                this.ean = ean;              
                this.title = title;
                this.brand = brand;
                this.description = description;
                this.amazonItemType = amazonItemType;
                this.amazonProductType = amazonProductType;
                this.bullets = bullets;
                this.keyword = keyword;
                this.imageUrls = imageUrls;
        }

	@Override
	public void validate() throws InvalidRequestException {
                super.validate();
		if (title == null || title.isEmpty()) { throw new InvalidRequestException("title is missing"); }
		if (brand == null || brand.isEmpty()) { throw new InvalidRequestException("brand is missing"); }
	}

	public Float getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Float suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmazonItemType() {
		return amazonItemType;
	}

	public void setAmazonItemType(String amazonItemType) {
		this.amazonItemType = amazonItemType;
	}

	public String getAmazonProductType() {
		return amazonProductType;
	}

	public void setAmazonProductType(String amazonProductType) {
		this.amazonProductType = amazonProductType;
	}

	public List<String> getBullets() {
		return bullets;
	}

	public void setBullets(List<String> bullets) {
		this.bullets = bullets;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
}
