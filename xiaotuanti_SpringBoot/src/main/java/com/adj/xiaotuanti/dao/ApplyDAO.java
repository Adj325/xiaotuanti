package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Apply;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.nlpcn.commons.lang.occurrence.Count;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ApplyDAO")
public interface ApplyDAO {
    void addApply(@Param("apply") Apply apply);

    void updateApply(@Param("apply") Apply apply);

    void deleteApply(@Param("apply") Apply apply);

    void deleteApplyById(@Param("applyId") String applyId);

    Apply getApplyById(@Param("applyId") String applyId);

    Apply getApplyByTeamIdAndUserId(@Param("apply") Apply apply);

    List<Apply> getAllApplies();

    List<Apply> getAppliesByUserId(@Param("userId") String userId);

    List<Apply> getAppliesByUser(@Param("user") User user);

    List<Apply> getAppliesByTeamId(@Param("teamId") String teamId);

}
