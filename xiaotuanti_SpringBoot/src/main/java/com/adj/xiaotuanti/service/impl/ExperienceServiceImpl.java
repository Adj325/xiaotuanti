
package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.ExperienceDAO;
import com.adj.xiaotuanti.pojo.Experience;
import com.adj.xiaotuanti.pojo.Willing;
import com.adj.xiaotuanti.service.ExperienceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("experienceService")
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceDAO experienceDAO;

    @Autowired
    public ExperienceServiceImpl(ExperienceDAO experienceDAO) {
        this.experienceDAO = experienceDAO;
    }

    public void addExperience(Experience experience) {
        experienceDAO.addExperience(experience);
    }

    public void updateExperience(Experience experience) {
        experienceDAO.updateExperience(experience);
    }

    public void deleteExperience(Experience experience) {
        experienceDAO.deleteExperience(experience);
    }

    public void deleteExperienceById(String experienceId) {
        experienceDAO.deleteExperienceById(experienceId);
    }

    public Experience getExperience(Experience experience) {
        return experienceDAO.getExperience(experience);
    }

    public Experience getExperienceById(String experienceId) {
        return experienceDAO.getExperienceById(experienceId);
    }

    public List<Experience> getExperienceByTeamId(String teamId) {
        return experienceDAO.getExperienceByTeamId(teamId);
    }

    @Override
    public Experience getExperienceByActivityId(String activityId) {
        return experienceDAO.getExperienceByActivityId(activityId);
    }
}
