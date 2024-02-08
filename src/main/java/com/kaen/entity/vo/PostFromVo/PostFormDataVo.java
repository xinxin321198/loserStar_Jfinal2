package com.kaen.entity.vo.PostFromVo;

import java.util.List;

public class PostFormDataVo {

    private List<ProjectPlanVo> projectPlanVos;
    private List<ProjectOrgVo> ProjectOrgVos;
    private List<ProjectEntityVo> projectentityvo;

    public List<ProjectPlanVo> getProjectPlanVos() {
        return projectPlanVos;
    }

    public void setProjectPlanVos(List<ProjectPlanVo> projectPlanVos) {
        this.projectPlanVos = projectPlanVos;
    }

    public List<ProjectOrgVo> getProjectOrgVos() {
        return ProjectOrgVos;
    }

    public void setProjectOrgVos(List<ProjectOrgVo> projectOrgVos) {
        ProjectOrgVos = projectOrgVos;
    }

    public List<ProjectEntityVo> getProjectentityvo() {
        return projectentityvo;
    }

    public void setProjectentityvo(List<ProjectEntityVo> projectentityvo) {
        this.projectentityvo = projectentityvo;
    }

}
