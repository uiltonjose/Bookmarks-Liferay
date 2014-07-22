package com.liferay.bookmarks.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class BookmarkEntry implements Serializable {

	private static final long serialVersionUID = 1934312643235275225L;

	public static final String GROUP_ID = "groupId";
	public static final String FOLDER_ID = "folderId";
	public static final String ENTRY_ID = "entryId";
	public static final String NAME = "name";
	public static final String URL = "url";
	public static final String DESCRIPTION = "description";

	private long groupId;
	private long folderId;
	private long entryId;
	private String name;
	private String url;
	private String description;

	public BookmarkEntry() {
	
	}
	
	public BookmarkEntry(JSONObject jsonObj) throws JSONException {
		groupId = jsonObj.getLong(GROUP_ID);
		folderId = jsonObj.getLong(FOLDER_ID);
		entryId = jsonObj.getLong(ENTRY_ID);
		name = jsonObj.getString(NAME);
		url = jsonObj.getString(URL);
		description = jsonObj.getString(DESCRIPTION);
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

}
