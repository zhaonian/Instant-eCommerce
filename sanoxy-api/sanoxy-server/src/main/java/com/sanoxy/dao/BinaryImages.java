package com.sanoxy.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


public class BinaryImages {
        
        public static byte[] im2bytes(BufferedImage image) throws IOException {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                return baos.toByteArray();
        }

        public static BufferedImage bytes2im(byte[] bytes) throws IOException {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                return ImageIO.read(bais);
        }

        public static void writeInt(byte[] bytes, int s, int i) {
                // Big-endian.
                bytes[s + 0] = (byte) (s & 0XFF);
                bytes[s + 1] = (byte) ((s >>> 8) & 0XFF);
                bytes[s + 2] = (byte) ((s >>> 16) & 0XFF);
                bytes[s + 3] = (byte) ((s >>> 24) & 0XFF);
        }

        public static int readInt(byte[] bytes, int s) {
                // Big-endian.
                return bytes[s + 0] | (bytes[s + 1] << 8) | (bytes[s + 2] << 16) | (bytes[s + 3] << 24);
        }

        public List<BufferedImage> imgs = new ArrayList<>();

        public BinaryImages() {
        }

        public BinaryImages(BufferedImage im) {
                imgs.add(im);
        }

        public void add(BufferedImage im) {
                imgs.add(im);
        }

        public List<BufferedImage> getAll() {
                return imgs;
        }

        public byte[] toBytes() throws IOException {
                List<byte[]> im_bytes = new ArrayList<>();

                int size = 0;
                for (BufferedImage im : imgs) {
                        byte[] b = im2bytes(im);
                        size += b.length;
                        im_bytes.add(b);

                }

                byte[] all = new byte[size + im_bytes.size() * 4];
                int ptr = 0;
                for (byte[] b : im_bytes) {
                        writeInt(all, ptr, b.length);
                        ptr += 4;
                        System.arraycopy(b, 0, all, ptr, b.length);
                        ptr += b.length;
                }
                return all;
        }

        public void fromBytes(byte[] bytes) throws IOException {
                int len = bytes.length;
                int ptr = 0;
                while (ptr < len) {
                        int s = readInt(bytes, ptr);
                        ptr += 4;
                        byte[] b = new byte[s];
                        System.arraycopy(bytes, ptr, b, 0, s);
                        ptr += s;

                        BufferedImage im;
                        im = bytes2im(b);
                        add(im);
                }
        }
}
