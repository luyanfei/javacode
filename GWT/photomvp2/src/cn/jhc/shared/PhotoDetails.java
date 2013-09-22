package cn.jhc.shared;

import java.io.Serializable;

public class PhotoDetails implements Serializable {
	String title;
	String tags;
	String id;
	String thubnailUrl;
	String largeUrl;
	String date;
	
	public PhotoDetails() {}
	
	public PhotoDetails(String id) {
		super();
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThubnailUrl() {
		return thubnailUrl;
	}

	public void setThubnailUrl(String thubnailUrl) {
		this.thubnailUrl = thubnailUrl;
	}

	public String getLargeUrl() {
		return largeUrl;
	}

	public void setLargeUrl(String largeUrl) {
		this.largeUrl = largeUrl;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
