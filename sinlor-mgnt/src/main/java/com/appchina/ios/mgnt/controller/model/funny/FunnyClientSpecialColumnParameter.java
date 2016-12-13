package com.appchina.ios.mgnt.controller.model.funny;

import java.util.Date;

import com.appchina.ios.mgnt.controller.model.info.TimedStatusStartSizeParameter;


public class FunnyClientSpecialColumnParameter extends TimedStatusStartSizeParameter {

    private int id;
    private String title;// 标题
    private String img;// 背景图url
    private String icon;// 头像图地址
    private Integer authorId;// 作者id
    private Integer ctype;// 专栏分类
    private Date relaeseTime;// 发布时间
    private int columnDetailId;// 专栏详情id
    private Integer promoteStatus;

    private Integer publish;// 是否发布
    // 草稿特有参数
    private Integer editStatus;
    private Integer source;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    public Date getRelaeseTime() {
        return relaeseTime;
    }

    public void setRelaeseTime(Date relaeseTime) {
        this.relaeseTime = relaeseTime;
    }

    public int getColumnDetailId() {
        return columnDetailId;
    }

    public void setColumnDetailId(int columnDetailId) {
        this.columnDetailId = columnDetailId;
    }

    public Integer getPublish() {
        return publish;
    }

    public void setPublish(Integer publish) {
        this.publish = publish;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getPromoteStatus() {
        return promoteStatus;
    }

    public void setPromoteStatus(Integer promoteStatus) {
        this.promoteStatus = promoteStatus;
    }

}
