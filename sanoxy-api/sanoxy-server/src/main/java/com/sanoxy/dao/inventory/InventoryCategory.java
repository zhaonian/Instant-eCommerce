package com.sanoxy.dao.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "inventory_category")
public class InventoryCategory implements Serializable {
	
	@Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cid")
	private Integer cid;
        
        @NotNull
	private String categoryName;
	
	@OneToMany(mappedBy = "inventoryCategory", cascade = CascadeType.ALL)
	private List<Inventory> inventories;

        public InventoryCategory() {
        }
        
        public InventoryCategory(String categoryName) {
                this.categoryName = categoryName;
        }

	public Integer getCid() {
		return cid;
	}

	public void setId(Integer cid) {
		this.cid = cid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@JsonIgnore
	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}
}
