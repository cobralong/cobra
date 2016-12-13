package com.appchina.ios.mgnt.controller.model.funny;

/**
 * @author liuxinglong
 * @date 2016年11月8日
 */
public class CertificateMadeModel {

    /**
     * id 索引
     */
    private int id;
    /**
     * 图片名（带扩展名）
     */
    private String picName;
    /**
     * 模版类型 1-普通 2-奖状 3-结婚证
     */
    private int flag;
    /**
     * 模版分类
     */
    private String type;
    /**
     * 模版分类名称
     */
    private String typeName;

    public CertificateMadeModel() {
        super();
    }

    public CertificateMadeModel(int id, String picName, int flag, String type, String typeName) {
        super();
        this.id = id;
        this.picName = picName;
        this.flag = flag;
        this.type = type;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "CertificateMadeModel [id=" + id + ", picName=" + picName + ", flag=" + flag + ", type=" + type
                + ", typeName=" + typeName + "]";
    }
}
