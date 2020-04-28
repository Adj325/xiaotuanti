package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.SectionDAO;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sectionService")
public class SectionServiceImpl implements SectionService {

    private final SectionDAO sectionDAO;

    @Autowired
    public SectionServiceImpl(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    // 获取所有部门
    public List<Section> getAllSections() {
        return sectionDAO.getAllSections();
    }

    // 根据id获取部门
    public Section getSectionById(String sectionId) {
        return sectionDAO.getSectionById(sectionId);
    }

    // 根据团体id获取部门
    public List<Section> getSectionsByTeamId(String teamId) {
        return sectionDAO.getSectionsByTeamId(teamId);
    }

    // 添加部门
    public void addSection(Section section) {
        sectionDAO.addSection(section);
    }

    // 根据部门名及团体获取部门
    public Section getSectionByNameAndTeam(Section section) {
        return sectionDAO.getSectionByNameAndTeam(section);
    }

    // 修改部门
    public void updateSection(Section section) {
        sectionDAO.updateSection(section);
    }

    // 删除部门
    public void deleteSection(Section section) {
        if (section != null && section.getMemberNum() <= 0) {
            sectionDAO.deleteSection(section);
        }
    }
}
