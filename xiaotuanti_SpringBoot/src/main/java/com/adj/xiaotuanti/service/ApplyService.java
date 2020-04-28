package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Apply;
import com.adj.xiaotuanti.pojo.User;

import java.util.List;

public interface ApplyService {

    void addApply(Apply apply);

    void updateApply(Apply apply);

    void deleteApply(Apply apply);

    void deleteApplyById(String applyId);


    Apply getApplyById(String applyId);

    Apply getApplyByTeamIdAndUserId(Apply apply);

    List<Apply> getAllApplies();

    List<Apply> getAppliesByUserId(String userId);

    List<Apply> getAppliesByUser(User user);

    List<Apply> getAppliesByTeamId(String teamId);

}
