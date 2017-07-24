package com.sanoxy.dao.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "inventory_category")
public class InventoryCategory implements Serializable {
        
	private Integer cid;
	private String categoryName;
        private Integer numInventories;
	private Collection<Inventory> inventories;

        public InventoryCategory() {
        }
        
        public InventoryCategory(Integer numInventories, String categoryName) {
                this.numInventories = numInventories;
                this.categoryName = categoryName;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="cid")
	public Integer getCid() {
		return cid;
	}

	public void setId(Integer cid) {
		this.cid = cid;
	}
        
        @NotNull
        public Integer getNumInventories() {
                return this.numInventories;
        }
        
        public void setNumInventories(Integer numInventories) {
                this.numInventories = numInventories;
        }

        @NotNull
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@JsonIgnore
/*        @OneToMany(mappedBy = "inventoryCategory", cascade = CascadeType.ALL)*/
        @Transient
	public Collection<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}
}
