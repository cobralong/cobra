package com.appchina.ios.mgnt.controller.model;

import java.util.Date;
import java.util.Map;

public class Menu {
    private Integer id;

    private String name;

    private String url;

    private String description;

    private Integer parentId;

    private Integer sort;

    private String permissionCode;

    private Boolean markDelete;

    private Date createTime;
    
    private Map<String, String> menuMeta;
    
    @Override
    public String toString() {
    	return "ID: " + this.id + ", Name: " + this.name + ", MarkDelete: " + this.markDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }

    public Boolean getMarkDelete() {
		return markDelete;
	}

	public void setMarkDelete(Boolean markDelete) {
		this.markDelete = markDelete;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Map<String, String> getMenuMeta() {
		return menuMeta;
	}

	public void setMenuMeta(Map<String, String> menuMeta) {
		this.menuMeta = menuMeta;
	}
}