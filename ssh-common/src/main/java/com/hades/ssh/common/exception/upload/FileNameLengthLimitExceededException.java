package com.hades.ssh.common.exception.upload;

import org.apache.commons.fileupload.FileUploadException;

/**
 * 文件名超长
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-8 下午8:44
 * <p>Version: 1.0
 */
public class FileNameLengthLimitExceededException extends FileUploadException {

	private static final long serialVersionUID = 7248302482536041245L;
	
	private int length;
    private int maxLength;
    private String filename;

    public FileNameLengthLimitExceededException(String filename, int length, int maxLength) {
        super("file name : [" + filename + "], length : [" + length + "], max length : [" + maxLength + "]");
        this.length = length;
        this.maxLength = maxLength;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public int getLength() {
        return length;
    }


    public int getMaxLength() {
        return maxLength;
    }

}
