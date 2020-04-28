package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.PartTemplateDAO;
import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.PartTemplate;
import com.adj.xiaotuanti.service.PartTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("partTemplateService")
public class PartTemplateServiceImpl implements PartTemplateService {

    @Autowired
    private PartTemplateDAO partTemplateDAO;

    public void addPartTemplate(PartTemplate partTemplate) {
        partTemplateDAO.addPartTemplate(partTemplate);
    }

    public void updatePartTemplate(PartTemplate partTemplate) {
        partTemplateDAO.updatePartTemplate(partTemplate);
    }

    public void deletePartTemplate(PartTemplate partTemplate) {
        partTemplateDAO.deletePartTemplate(partTemplate);
    }

    public void deletePartTemplateById(String partTemplateId) {
        partTemplateDAO.deletePartTemplateById(partTemplateId);
    }

    public PartTemplate getPartTemplate(PartTemplate partTemplate) {
        return partTemplateDAO.getPartTemplate(partTemplate);
    }

    public PartTemplate getPartTemplateById(String partTemplateId) {
        return partTemplateDAO.getPartTemplateById(partTemplateId);
    }

    public List<PartTemplate> getAllPartTemplates() {
        return partTemplateDAO.getAllPartTemplates();
    }

    public PartTemplate getPartTemplateByActivityTemplate(ActivityTemplate activityTemplate) {
        return partTemplateDAO.getPartTemplateByActivityTemplate(activityTemplate);
    }

    public List<PartTemplate> getPartTemplatesByActivityTemplate(ActivityTemplate activityTemplate) {
        return partTemplateDAO.getPartTemplatesByActivityTemplate(activityTemplate);
    }

    public void deletePartTemplateByActivityTemplate(ActivityTemplate activityTemplate) {
        partTemplateDAO.getPartTemplateByActivityTemplate(activityTemplate);
    }

    public void deletePartTemplatesByActivityTemplate(ActivityTemplate activityTemplate) {
        partTemplateDAO.deletePartTemplatesByActivityTemplate(activityTemplate);
    }

}