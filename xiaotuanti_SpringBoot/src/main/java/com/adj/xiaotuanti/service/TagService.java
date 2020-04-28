package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Tag;

import java.util.List;

public interface TagService {

    void addTag(Tag tag);

    void updateTag(Tag tag);

    void deleteTag(Tag tag);

    void deleteTagById(String tagId);

    Tag getTag(Tag tag);

    Tag getTagById(String tagId);

    List<Tag> getAllTags();

    List<Tag> getTagsByActivityTemplateId(String templateId);

    Tag getTagByName(String name);
}