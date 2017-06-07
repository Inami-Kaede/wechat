package com.hayate.wechat.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class PictureSizeUtil {

	/**
	 * 按指定大小把图片进行缩和放（会遵循原图高宽比例）
	 * @param data
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] resize(byte[] data,int width, int height){		
		
		ByteArrayInputStream in = null;
		ByteArrayOutputStream out = null;
		
		try {
			in = new ByteArrayInputStream(data);
			out = new ByteArrayOutputStream();
			BufferedImage image = ImageIO.read(in);
			BufferedImage newImage = Thumbnails.of(image).size(width,height).asBufferedImage();
			ImageIO.write(newImage,"jpg",out);
			byte[] newData = out.toByteArray();
			return newData;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(in != null){
					in.close();
					in = null;
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}			
			try {
				if(out != null){
					out.close();
					out = null;
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		return null;		
	}
	
	/**
	 * 按照比例进行缩小和放大
	 * @param data
	 * @param scale
	 * @return
	 */
	public static byte[] resize(byte[] data,double scale){		
		ByteArrayInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new ByteArrayInputStream(data);
			out = new ByteArrayOutputStream();
			BufferedImage image = ImageIO.read(in);
			BufferedImage newImage = Thumbnails.of(image).scale(scale).asBufferedImage();
			ImageIO.write(newImage,"jpg",out);
			byte[] newData = out.toByteArray();
			return newData;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(in != null){
					in.close();
					in = null;
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}			
			try {
				if(out != null){
					out.close();
					out = null;
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 		
		return null;		
	}
	
	/**
	 * 图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
	 * @param data
	 * @param quality
	 * @return
	 */
	public static byte[] quality(byte[] data,double quality){	
		ByteArrayInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new ByteArrayInputStream(data);
			out = new ByteArrayOutputStream();
			BufferedImage image = ImageIO.read(in);
			BufferedImage newImage = Thumbnails.of(image).outputQuality(quality).asBufferedImage();
			ImageIO.write(newImage,"jpg",out);
			byte[] newData = out.toByteArray();
			return newData;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(in != null){
					in.close();
					in = null;
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}			
			try {
				if(out != null){
					out.close();
					out = null;
				}				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 		
		return null;		
	}
}
