
package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.WillingDAO;
import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Willing;

import com.adj.xiaotuanti.service.WillingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("willingService")
public class WillingServiceImpl implements WillingService {

    private final WillingDAO willingDAO;

    @Autowired
    public WillingServiceImpl(WillingDAO willingDAO) {
        this.willingDAO = willingDAO;
    }

    public void addWilling(Willing willing) {
        willingDAO.addWilling(willing);
    }

    public void updateWilling(Willing willing) {
        willingDAO.updateWilling(willing);
    }

    public void deleteWilling(Willing willing) {
        willingDAO.deleteWilling(willing);
    }

    public void deleteWillingByActivity(Activity activity){
        willingDAO.deleteWillingByActivity(activity);
    }

    public void deleteWillingByActivityId(String activityId){
        willingDAO.deleteWillingByActivityId(activityId);
    }

    public Willing getWilling(Willing willing) {
        return willingDAO.getWilling(willing);
    }

    public Willing getWillingById(String willingId) {
        return willingDAO.getWillingById(willingId);
    }

    public Boolean isSignUpByUserIdAndActivityId(String userId, String activityId){
        return willingDAO.isSignUpByUserIdAndActivityId(userId, activityId) == 1;
    }

    @Override
    public void deleteWillingByUserIdAndActivityId(String userId, String activityId) {
        willingDAO.deleteWillingByUserIdAndActivityId(userId, activityId);
    }

    @Override
    public Willing getWillingByUserIdAndActivityId(String userId, String activityId) {
        return  willingDAO.getWillingByUserIdAndActivityId(userId, activityId);
    }
}
