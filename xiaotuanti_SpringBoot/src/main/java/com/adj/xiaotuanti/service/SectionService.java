package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Section;

import java.util.List;

public interface SectionService {

    // 根据id获取部门
    Section getSectionById(String sectionId);

    // 根据部门名及团体获取部门
    Section getSectionByNameAndTeam(Section section);

    // 获取所有部门
    List<Section> getAllSections();

    // 根据团体id获取部门
    List<Section> getSectionsByTeamId(String teamId);

    // 添加部门
    void addSection(Section section);

    // 修改部门
    void updateSection(Section section);

    // 删除部门
    void deleteSection(Section section);
}
