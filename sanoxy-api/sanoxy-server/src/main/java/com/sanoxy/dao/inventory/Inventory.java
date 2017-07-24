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
        
	private Integer iid;
	private InventoryCategory inventoryCategory;
	private Float suggestPrice;
	private String ean;
	private String title;
	private String brand;
	private String description;
	private String amazonItemType;
	private String amazonProductType;
	private String bulletsJson;
	private String keyword;
	private String imageUrlsJson;

	
//	@OneToMany(mappedBy="inventory_id", cascade = CascadeType.ALL)
//	private List<WarehouseCategory> warehouseCateogries;
        
        public Inventory() {
        }
        
        public Inventory(Float suggestPrice, 
                         String ean, 
                         String title, 
                         String brand, 
                         String description,
                         String amazonItemType,
                         String amazonProductType,
                         String bulletsJson,
                         String keyword,
                         String imageUrlsJson) {
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
                this(suggestPrice, ean, title, brand, description, amazonItemType, amazonProductType, bulletsJson, keyword, imageUrlsJson);
                this.inventoryCategory = inventoryCategory;
        }
        
        @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "iid")
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer id) {
		this.iid = id;
	}

        @ManyToOne(targetEntity=InventoryCategory.class)
	@JoinColumn(name = "cid")
	public InventoryCategory getInventoryCategory() {
		return inventoryCategory;
	}

	public void setInventoryCategory(InventoryCategory inventoryCategory) {
		this.inventoryCategory = inventoryCategory;
	}

        @NotNull
        @NotEmpty
	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

        @Field
        @NotNull
        @NotEmpty
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

        @Field
        @NotNull
        @NotEmpty
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

        @Field
        @NotNull
        @NotEmpty
        @Column(columnDefinition = "TEXT")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

        @NotNull
	public Float getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Float suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

        @NotNull
	public String getAmazonItemType() {
		return amazonItemType;
	}

	public void setAmazonItemType(String amazonItemType) {
		this.amazonItemType = amazonItemType;
	}

        @NotNull
	public String getAmazonProductType() {
		return amazonProductType;
	}

	public void setAmazonProductType(String amazonProductType) {
		this.amazonProductType = amazonProductType;
	}

        @NotEmpty
	public String getBulletsJson() {
                return this.bulletsJson;
        }
        
        public void setBulletsJson(String bulletsJson) {
                this.bulletsJson = bulletsJson;
        }

        @NotNull
        @NotEmpty
        @Field
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

        @NotNull
        @NotEmpty
	public String getImageUrlsJson() {
                return this.imageUrlsJson;
        }
        
        public void setsImageUrlsJson(String imageUrlsJson) {
                this.imageUrlsJson = imageUrlsJson;
        }
}
