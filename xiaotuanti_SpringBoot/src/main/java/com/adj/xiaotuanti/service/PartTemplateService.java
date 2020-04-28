package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.PartTemplate;

import java.util.List;

public interface PartTemplateService {

    void addPartTemplate(PartTemplate partTemplate);

    void updatePartTemplate(PartTemplate partTemplate);

    void deletePartTemplate(PartTemplate partTemplate);

    void deletePartTemplateById(String partTemplateId);

    PartTemplate getPartTemplate(PartTemplate partTemplate);

    PartTemplate getPartTemplateById(String partTemplateId);

    List<PartTemplate> getAllPartTemplates();

    PartTemplate getPartTemplateByActivityTemplate(ActivityTemplate activityTemplate);

    List<PartTemplate> getPartTemplatesByActivityTemplate(ActivityTemplate activityTemplate);

    void deletePartTemplateByActivityTemplate(ActivityTemplate activityTemplate);

    void deletePartTemplatesByActivityTemplate(ActivityTemplate activityTemplate);

}