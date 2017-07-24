package com.sanoxy.dao.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanoxy.dao.user.Workspace;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "inventory_category", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"wid", "categoryName"})
       }
)
public class InventoryCategory implements Serializable {
        
	private Integer cid;
        private Workspace workspace;
	private String categoryName;
        private Integer numInventories;
	private Collection<Inventory> inventories;

        public InventoryCategory() {
        }
        
        public InventoryCategory(Workspace workspace, String categoryName) {
                this.workspace = workspace;
                this.numInventories = 0;
                this.categoryName = categoryName;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cid")
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}
        
        @NotNull
        @ManyToOne(targetEntity=Workspace.class)
        @JoinColumn(name = "wid")
        public Workspace getWorkspace() {
                return this.workspace;
        }
        
        public void setWorkspace(Workspace workspace) {
                this.workspace = workspace;
        }
        
        @NotNull
        public Integer getNumInventories() {
                return this.numInventories;
        }
        
        public void setNumInventories(Integer numInventories) {
                this.numInventories = numInventories;
        }

        @NotNull
        @NotEmpty
        @Column(name = "categoryName")
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
