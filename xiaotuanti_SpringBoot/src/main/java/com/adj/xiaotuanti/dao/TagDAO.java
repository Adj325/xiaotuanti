package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Tag;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("TagDAO")
public interface TagDAO {

    void addTag(@Param("tag") Tag tag);

    void updateTag(@Param("tag") Tag tag);

    void deleteTag(@Param("tag") Tag tag);

    void deleteTagById(@Param("id") String id);

    Tag getTag(@Param("tag") Tag tag);

    Tag getTagById(@Param("id") String tagId);

    Tag getTagByName(@Param("name") String name);

    List<Tag> getAllTags();

    List<Tag> getTagsByActivityTemplateId(@Param("templateId") String templateId);

    List<Tag> getTagsByActivityId(@Param("activityId") String activityId);
}
