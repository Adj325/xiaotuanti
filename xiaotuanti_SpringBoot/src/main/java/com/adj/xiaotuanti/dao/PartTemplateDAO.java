package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.PartTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PartTemplateDAO")
public interface PartTemplateDAO {

    void addPartTemplate(@Param("partTemplate") PartTemplate partTemplate);

    void updatePartTemplate(@Param("partTemplate") PartTemplate partTemplate);

    void deletePartTemplate(@Param("partTemplate") PartTemplate partTemplate);

    void deletePartTemplateById(@Param("partTemplateId") String partTemplateId);

    PartTemplate getPartTemplate(@Param("partTemplate") PartTemplate partTemplate);

    PartTemplate getPartTemplateById(@Param("partTemplateId") String partTemplateId);

    List<PartTemplate> getAllPartTemplates();

    PartTemplate getPartTemplateByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    List<PartTemplate> getPartTemplatesByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    void deletePartTemplateByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    void deletePartTemplatesByActivityTemplate(@Param("activityTemplate") ActivityTemplate activityTemplate);

    PartTemplate getPartTemplateByActivityTemplateId(@Param("activityTemplateId") String activityTemplateId);

    List<PartTemplate> getPartTemplatesByActivityTemplateId(@Param("activityTemplateId") String activityTemplateId);

    void deletePartTemplateByActivityTemplateId(@Param("activityTemplateId") String activityTemplateId);

    void deletePartTemplatesByActivityTemplateId(@Param("activityTemplateId") String activityTemplateId);

}
