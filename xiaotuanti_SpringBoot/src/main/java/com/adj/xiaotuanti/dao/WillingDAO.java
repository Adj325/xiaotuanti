
package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Willing;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("WillingDAO")
public interface WillingDAO {

    void addWilling(@Param("willing") Willing willing);

    void updateWilling(@Param("willing") Willing willing);

    void deleteWilling(@Param("willing") Willing willing);

    void deleteWillingByActivity(@Param("activity") Activity activity);

    void deleteWillingByActivityId(@Param("activityId") String activityId);

    void deleteWillingByUserIdAndActivityId(@Param("userId") String userId, @Param("activityId") String activityId);

    Willing getWilling(@Param("willing") Willing willing);

    Willing getWillingById(@Param("willingId") String willingId);

    // 获取志愿数量-根据活动id
    Integer getWillingNumberByActivityId(@Param("activityId") String activityId);

    Integer isSignUpByUserIdAndActivityId(@Param("userId") String userId, @Param("activityId") String activityId);

    Willing getWillingByUserIdAndActivityId(String userId, String activityId);
}
