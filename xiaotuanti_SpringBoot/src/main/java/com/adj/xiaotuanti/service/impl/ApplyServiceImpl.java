package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.ApplyDAO;
import com.adj.xiaotuanti.pojo.Apply;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("applyService")
public class ApplyServiceImpl implements ApplyService {

    private final ApplyDAO applyDAO;

    @Autowired
    public ApplyServiceImpl(ApplyDAO applyDAO) {
        this.applyDAO = applyDAO;
    }

    public void addApply(Apply apply) {
        Apply tempApply = applyDAO.getApplyById(apply.getId());
        if (tempApply == null)
            applyDAO.addApply(apply);
        else
            applyDAO.updateApply(apply);
    }

    public void updateApply(Apply apply) {
        Apply tempApply = applyDAO.getApplyById(apply.getId());
        if (tempApply != null)
            applyDAO.updateApply(apply);
    }

    public void deleteApply(Apply apply) {
        Apply tempApply = applyDAO.getApplyById(apply.getId());
        if (tempApply != null)
            applyDAO.deleteApply(apply);
    }

    public void deleteApplyById(String applyId) {
        applyDAO.deleteApplyById(applyId);
    }

    public Apply getApplyById(String applyId) {
        return applyDAO.getApplyById(applyId);
    }

    public Apply getApplyByTeamIdAndUserId(Apply apply) {
        return applyDAO.getApplyByTeamIdAndUserId(apply);
    }

    public List<Apply> getAllApplies() {
        return applyDAO.getAllApplies();
    }

    public List<Apply> getAppliesByUserId(String userId) {
        return applyDAO.getAppliesByUserId(userId);
    }

    public List<Apply> getAppliesByUser(User user) {
        return applyDAO.getAppliesByUser(user);
    }

    public List<Apply> getAppliesByTeamId(String teamId) {
        return applyDAO.getAppliesByTeamId(teamId);
    }
}
