
package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Willing;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WillingService {

    void addWilling(Willing willing);
    
    void updateWilling(Willing willing);
    
    void deleteWilling(Willing willing);

    void deleteWillingByActivity( Activity activity);

    void deleteWillingByActivityId(String activityId);

    Willing getWilling(Willing willing);

    Willing getWillingById(String willingId);

    void deleteWillingByUserIdAndActivityId(String userId, String activityId);

    Boolean isSignUpByUserIdAndActivityId(String userId, String activityId);

    Willing getWillingByUserIdAndActivityId(String id, String activityId);
}
    