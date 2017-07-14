package com.sanoxy.dao.inventory;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "inventory_category")
public class InventoryCategory {
	
	@Id
	@NotNull
	@Column(name="cid")
	private Integer cid;
	private String category;
	
	@OneToMany(mappedBy = "inventoryCategory", cascade = CascadeType.ALL)
	private List<Inventory> inventories;

	public Integer getCid() {
		return cid;
	}

	public void setId(Integer cid) {
		this.cid = cid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@JsonIgnore
	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}
}
