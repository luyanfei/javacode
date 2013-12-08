package cn.jhc.exception;

import java.io.IOException;

public class ImageUploadException extends Exception {

	public ImageUploadException(String message) {
		super(message);
	}

	public ImageUploadException(String message, IOException e) {
		super(message, e);
	}

}
