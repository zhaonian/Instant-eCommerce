
package com.sanoxy.dao.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class InventoryImage implements Serializable {
        private Integer imid;
        private String url;
        private byte[] imgData;
        // private List<Inventory> inventories;
        
        public InventoryImage() {
        }
        
        private static byte[] im2bytes(BufferedImage image) throws IOException {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                return baos.toByteArray();
        }

        private static BufferedImage bytes2im(byte[] bytes) throws IOException {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                return ImageIO.read(bais);
        }
        
        public InventoryImage(String url, byte[] imgFile) {
                this.url = url;
                this.imgData = imgFile;
        }
        
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "imid")
        public Integer getImid() {
                return this.imid;
        }
        
        public void setImid(Integer imid) {
                this.imid = imid;
        }
        
        public String getUrl() {
                return this.url;
        }
        
        public void setUrl(String url) {
                this.url = url;
        }
        
        @Basic(fetch=FetchType.LAZY)
        public byte[] getImgData() {
                return this.imgData;
        }
        
        public void setImgData(byte[] imgData) {
                this.imgData = imgData;
        }
        
        @Transient
        @JsonIgnore
        public BufferedImage getImage() throws IOException {
                return bytes2im(imgData);
        }
/*
        @ManyToMany(mappedBy = "images", cascade = CascadeType.DETACH)
        public List<Inventory> getInventories() {
                return inventories;
        }

        public void setInventories(List<Inventory> inventories) {
                this.inventories = inventories;
        }*/
}
