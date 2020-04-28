
package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Experience;

import java.util.List;

public interface ExperienceService {

    void addExperience(Experience experience);
    
    void updateExperience(Experience experience);
    
    void deleteExperience(Experience experience);

    void deleteExperienceById(String experienceId);
    
    Experience getExperience(Experience experience);
    
    Experience getExperienceById(String experienceId);

    List<Experience> getExperienceByTeamId(String experienceId);

    Experience getExperienceByActivityId(String activityId);
}
    