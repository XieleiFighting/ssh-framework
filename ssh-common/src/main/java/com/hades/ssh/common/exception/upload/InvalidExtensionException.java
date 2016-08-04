package com.hades.ssh.common.exception.upload;

import org.apache.commons.fileupload.FileUploadException;

import java.util.Arrays;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 上午10:53:35
 * <p>Version: 1.0
 */
public class InvalidExtensionException extends FileUploadException {

	private static final long serialVersionUID = -50312011589056445L;
	
	private String[] allowedExtension;
    private String extension;
    private String filename;

    public InvalidExtensionException(String[] allowedExtension, String extension, String filename) {
        super("filename : [" + filename + "], extension : [" + extension + "], allowed extension : [" + Arrays.toString(allowedExtension) + "]");
        this.allowedExtension = allowedExtension;
        this.extension = extension;
        this.filename = filename;
    }

    public String[] getAllowedExtension() {
        return allowedExtension;
    }

    public String getExtension() {
        return extension;
    }

    public String getFilename() {
        return filename;
    }

    public static class InvalidImageExtensionException extends InvalidExtensionException {

		private static final long serialVersionUID = -1103189241589700072L;

		public InvalidImageExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

    public static class InvalidFlashExtensionException extends InvalidExtensionException {

		private static final long serialVersionUID = -1254955093682285411L;

		public InvalidFlashExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

    public static class InvalidMediaExtensionException extends InvalidExtensionException {

		private static final long serialVersionUID = -7163315300865053062L;

		public InvalidMediaExtensionException(String[] allowedExtension, String extension, String filename) {
            super(allowedExtension, extension, filename);
        }
    }

}

