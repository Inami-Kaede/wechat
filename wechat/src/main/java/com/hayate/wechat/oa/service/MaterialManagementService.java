package com.hayate.wechat.oa.service;

import java.util.List;
import java.util.Map;

public interface MaterialManagementService {

	String uploadTemp(String type, byte[] data, String fileFormat);

	byte[] getTemp(String mediaId);

	String getVideoTemp(String mediaId);

	String uploadMaterial(String type, byte[] data, String fileFormat);

	String uploadMaterialEx(String type, byte[] data, String fileFormat,
			String title, String introduction);

	byte[] getMaterial(String mediaId);

	List<Map<String, Object>> getNewsMaterial(String mediaId);

	String getVideoMaterial(String mediaId);

	String deleteMaterial(String mediaId);

	Map<String, Object> getMaterialCount();

	List<Map<String, Object>> getMaterialList(String type, int offset, int count);

	String uploadArticlePic(byte[] data, String fileFormat);

	String createNewsGroup(List<Map<String, Object>> articles);

	Map<String, Object> createNews(String title, String thumb_media_id,
			String author, String digest, int show_cover_pic, String content,
			String content_source_url);

	String updateNewsGroup(String mediaId, int index,
			Map<String, Object> articles);

}
