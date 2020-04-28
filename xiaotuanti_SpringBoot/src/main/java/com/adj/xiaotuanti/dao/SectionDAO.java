package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.pojo.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SectionDAO")
public interface SectionDAO {

    Section getSectionByNameAndTeam(@Param("section") Section section);

    Section getSectionById(@Param("sectionId") String sectionId);

    List<Section> getSectionsByTeamId(@Param("teamId") String teamId);

    List<Section> getAllSections();

    void addSection(@Param("section") Section section);

    void updateSection(@Param("section") Section section);

    void deleteSection(@Param("section") Section section);
}
