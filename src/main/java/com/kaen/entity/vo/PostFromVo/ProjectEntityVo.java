package com.kaen.entity.vo.PostFromVo;

public class ProjectEntityVo {

    private long planid;//计划id
    private long orgid;//招聘单位id
    private long id;//自己的id
    private String  orgname;//单位名称
    private String hrpostname;//招聘岗位
    private String majortype;//招娉专业类别
    private String major;//招娉专业
    private String education;//学历要求
    private String other;//其他
    private String hrsubject;//考试科目
    private int zpperson;//招娉人数
    private int bmperson;//报名人数

    public long getPlanid() {
        return planid;
    }

    public void setPlanid(long planid) {
        this.planid = planid;
    }

    public long getOrgid() {
        return orgid;
    }

    public void setOrgid(long orgid) {
        this.orgid = orgid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getHrpostname() {
        return hrpostname;
    }

    public void setHrpostname(String hrpostname) {
        this.hrpostname = hrpostname;
    }

    public String getMajortype() {
        return majortype;
    }

    public void setMajortype(String majortype) {
        this.majortype = majortype;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getHrsubject() {
        return hrsubject;
    }

    public void setHrsubject(String hrsubject) {
        this.hrsubject = hrsubject;
    }

    public int getZpperson() {
        return zpperson;
    }

    public void setZpperson(int zpperson) {
        this.zpperson = zpperson;
    }

    public int getBmperson() {
        return bmperson;
    }

    public void setBmperson(int bmperson) {
        this.bmperson = bmperson;
    }

}
