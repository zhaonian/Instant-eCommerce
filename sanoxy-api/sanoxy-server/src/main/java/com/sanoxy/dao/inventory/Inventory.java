package com.sanoxy.dao.inventory;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;


@Entity
@Indexed
@Table(name = "inventory")
public class Inventory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

        @NotNull
	private Integer sku;
        
	@ManyToOne(targetEntity=InventoryCategory.class)
	@JoinColumn(name = "cid")
	private InventoryCategory inventoryCategory;
        
	private Integer suggestPrice;
	private String ean;
        
        @Field
	private String title;
        
        @Field
	private String brand;
        
        @Field
        @Column(columnDefinition = "TEXT")
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
	
//	@OneToMany(mappedBy="inventory_id", cascade = CascadeType.ALL)
//	private List<WarehouseCategory> warehouseCateogries;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSku() {
		return sku;
	}

	public void setSku(Integer sku) {
		this.sku = sku;
	}


	public InventoryCategory getInventoryCategory() {
		return inventoryCategory;
	}

	public void setInventoryCategory(InventoryCategory inventoryCategory) {
		this.inventoryCategory = inventoryCategory;
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

	public Integer getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Integer suggestPrice) {
		this.suggestPrice = suggestPrice;
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
