package com.telpoo.hotpic.object;

public class PicOj extends MyObject {
	public static final String[] keys = { "id", "name", "comment_count", "comment", "like",
		"url", "thumbnail", "type_cut","group_id" };
	public static final String[] keysDb = { "id", "name", "comment_count", "comment", "like",
		"url", "primarykey_thumbnail", "type_cut","group_id" };
	
	
	

	public static final String ID = keys[0];
	public static final String NAME = keys[1];

	public static final String COMMENT_COUNT = keys[2];
	public static final String COMMENT = keys[3];
	public static final String LIKE = keys[4];
	public static final String URL = keys[5];
	public static final String URL_THUMBNAIL = keys[6];
	public static final String TYPE_CUT = keys[7];
	public static final String GROUP_ID = keys[8];

}
