package br.com.zer0framework.model;

import java.util.Date;

public class File {

    private Integer id;
    private String fileName;
    private String originalFileName;
    private Date created;
    private String fileType;
    private Date lastModified;

    public File(Integer id, String fileName, String originalFileName, Date created, String fileType, Date lastModified) {
        this.id = id;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.created = created;
        this.fileType = fileType;
        this.lastModified = lastModified;
    }

    public File(String fileName, String originalFileName, String fileType) {
        this.id = null;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.created = new Date();
        this.fileType = fileType;
        this.lastModified=null;
    }

    public File() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

}
