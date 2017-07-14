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
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Indexed
@Table(name = "inventory")
public class Inventory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
        
	@ManyToOne(targetEntity=InventoryCategory.class)
	@JoinColumn(name = "cid")
	private InventoryCategory inventoryCategory;
        
        @NotNull
	private Float suggestPrice;
        
        @NotNull
        @NotEmpty
	private String ean;
        
        @Field
        @NotNull
        @NotEmpty
	private String title;
        
        @Field
        @NotNull
        @NotEmpty
	private String brand;
        
        @Field
        @NotNull
        @NotEmpty
        @Column(columnDefinition = "TEXT")
	private String description;
        
        @NotNull
	private String amazonItemType;
        
        @NotNull
	private String amazonProductType;
        
        @NotEmpty
	private String bulletsJson;
        
        @NotNull
        @NotEmpty
        @Field
	private String keyword;
        
        @NotNull
        @NotEmpty
	private String imageUrlsJson;

        
	
//	@OneToMany(mappedBy="inventory_id", cascade = CascadeType.ALL)
//	private List<WarehouseCategory> warehouseCateogries;
        
        public Inventory() {
        }
        
        public Inventory(InventoryCategory inventoryCategory, 
                         Float suggestPrice, 
                         String ean, 
                         String title, 
                         String brand, 
                         String description,
                         String amazonItemType,
                         String amazonProductType,
                         String bulletsJson,
                         String keyword,
                         String imageUrlsJson) {
                this.inventoryCategory = inventoryCategory;
                this.suggestPrice = suggestPrice;
                this.ean = ean;
                this.title = title;
                this.brand = brand;
                this.description = description;
                this.amazonItemType = amazonItemType;
                this.amazonProductType = amazonProductType;
                this.bulletsJson = bulletsJson;
                this.keyword = keyword;
                this.imageUrlsJson = imageUrlsJson;
        }
        
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Float getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Float suggestPrice) {
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

	public String getBulletsJson() {
                return this.bulletsJson;
        }
        
        public void setBulletsJson(String bulletsJson) {
                this.bulletsJson = bulletsJson;
        }

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getImageUrlsJson() {
                return this.imageUrlsJson;
        }
        
        public void setsImageUrlsJson(String imageUrlsJson) {
                this.imageUrlsJson = imageUrlsJson;
        }
}
