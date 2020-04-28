package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.TagDAO;
import com.adj.xiaotuanti.pojo.ActivityTemplate;
import com.adj.xiaotuanti.pojo.Tag;
import com.adj.xiaotuanti.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagService")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDAO tagDAO;

    public void addTag(Tag tag) {
        tagDAO.addTag(tag);
    }

    public void updateTag(Tag tag) {
        tagDAO.updateTag(tag);
    }

    public void deleteTag(Tag tag) {
        tagDAO.deleteTag(tag);
    }

    public void deleteTagById(String tagId) {
        tagDAO.deleteTagById(tagId);
    }

    public Tag getTag(Tag tag) {
        return tagDAO.getTag(tag);
    }

    public Tag getTagById(String tagId) {
        return tagDAO.getTagById(tagId);
    }

    public List<Tag> getAllTags() {
        return tagDAO.getAllTags();
    }

    @Override
    public List<Tag> getTagsByActivityTemplateId(String templateId) {
        return tagDAO.getTagsByActivityTemplateId(templateId);
    }

    public Tag getTagByName(String name) {
        return tagDAO.getTagByName(name);
    }

}