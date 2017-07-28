package com.sanoxy.dao.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
	private byte[] bulletsJson;
	private String keyword;
	private Collection<InventoryImage> inventoryImages;

	
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
                         Collection<InventoryImage> images) {
                this.inventoryCategory = inventoryCategory;
                this.suggestPrice = suggestPrice;
                this.ean = ean;
                this.title = title;
                this.brand = brand;
                this.description = description;
                this.amazonItemType = amazonItemType;
                this.amazonProductType = amazonProductType;
                this.bulletsJson = bulletsJson.getBytes(StandardCharsets.US_ASCII);
                this.keyword = keyword;
                this.inventoryImages = images;
        }
        
        @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "iid")
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
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
        @Column(length = 1024)
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
        @Lob
        @Basic(fetch=FetchType.LAZY)
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
        @Basic(fetch=FetchType.LAZY)
	public byte[] getBulletsJson() {
                return this.bulletsJson;
        }
        
        public void setBulletsJson(byte[] bulletsJson) {
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
        
        @Lob
        @NotNull
        @NotEmpty
        @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinTable(name = "inventory_join_image", 
                   /*joinColumns = @JoinColumn(name = "iid", referencedColumnName = "fiid"), */
                   inverseJoinColumns = @JoinColumn(name = "imid", referencedColumnName = "imid"))
        @JsonIgnore
	public Collection<InventoryImage> getInventoryImages() {
                return this.inventoryImages;
        }
        
        public void setInventoryImages(Collection<InventoryImage> inventoryImages) {
                this.inventoryImages = inventoryImages;
        }
}
