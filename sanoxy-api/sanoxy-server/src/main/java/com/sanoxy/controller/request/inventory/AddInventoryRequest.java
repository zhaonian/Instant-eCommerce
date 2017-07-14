/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.controller.request.inventory;

import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import com.sanoxy.dao.inventory.Inventory;

/**
 *
 * @author luan
 */
public class AddInventoryRequest implements ValidatedRequest {

	private Integer sku;
	private Integer suggestPrice;
	private String ean;
	private String title;
	private String brand;
	private String description;
	private String amazonItemType;
	private String amazonProductType;
	private String bullet1;
	private String bullet2;
	private String bullet3;
	private String bullet4;
	private String bullet5;
	private String keyword;
	private String mainImage;
	private String image2;
	private String image3;

	@Override
	public boolean isValid() throws InvalidRequestException {
		if (sku == null) { throw new InvalidRequestException("sku is invalid"); }
		if (title == null || title.isEmpty()) { throw new InvalidRequestException("title is invalid"); }
		if (brand == null || brand.isEmpty()) { throw new InvalidRequestException("brand is invalid"); }
		return true;
	}

	public Inventory asInventory() {
		Inventory inventory = new Inventory();
		inventory.setSku(sku);
		inventory.setSuggestPrice(suggestPrice);
		inventory.setEan(ean);
		inventory.setTitle(title);
		inventory.setBrand(brand);
		inventory.setDescription(description);
		inventory.setAmazonItemType(amazonItemType);
		inventory.setAmazonProductType(amazonProductType);
		inventory.setBullet1(bullet1);
		inventory.setBullet2(bullet2);
		inventory.setBullet3(bullet3);
		inventory.setBullet4(bullet4);
		inventory.setBullet5(bullet5);
		inventory.setKeyword(keyword);
		inventory.setMainImage(mainImage);
		inventory.setImage2(image2);
		inventory.setImage3(image3);
		return inventory;
	}
	
	public Integer getSku() {
		return sku;
	}

	public void setSku(Integer sku) {
		this.sku = sku;
	}

	public Integer getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Integer suggestPrice) {
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

	public String getBullet1() {
		return bullet1;
	}

	public void setBullet1(String bullet1) {
		this.bullet1 = bullet1;
	}

	public String getBullet2() {
		return bullet2;
	}

	public void setBullet2(String bullet2) {
		this.bullet2 = bullet2;
	}

	public String getBullet3() {
		return bullet3;
	}

	public void setBullet3(String bullet3) {
		this.bullet3 = bullet3;
	}

	public String getBullet4() {
		return bullet4;
	}

	public void setBullet4(String bullet4) {
		this.bullet4 = bullet4;
	}

	public String getBullet5() {
		return bullet5;
	}

	public void setBullet5(String bullet5) {
		this.bullet5 = bullet5;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}
}
