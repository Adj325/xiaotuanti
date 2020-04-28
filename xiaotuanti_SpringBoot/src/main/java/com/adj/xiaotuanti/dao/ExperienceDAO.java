
package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Experience;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ExperienceDAO")
public interface ExperienceDAO {

    void addExperience(@Param("experience") Experience experience);

    void updateExperience(@Param("experience") Experience experience);

    void deleteExperience(@Param("experience") Experience experience);

    void deleteExperienceById(@Param("experienceId") String experienceId);

    Experience getExperience(@Param("experience") Experience experience);

    Experience getExperienceById(@Param("experienceId") String experienceId);

    List<Experience> getExperienceByTeamId(@Param("teamId") String teamId);

    Experience getExperienceByActivityId(@Param("activityId") String activityId);
}
