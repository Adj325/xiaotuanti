package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Relation_Activity_User;
import com.adj.xiaotuanti.pojo.Section;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("Relation_Activity_UserDAO")
public interface Relation_Activity_UserDAO {

    void addRecord(@Param("record") Relation_Activity_User record);

    void updateRecord(@Param("record") Relation_Activity_User record);

    void deleteRecord(@Param("record") Relation_Activity_User record);

    void deleteRecordByActivity(@Param("activity") Activity activity);

    void deleteRecordByActivityId(@Param("activityId") String activityId);

    Relation_Activity_User getRecord(@Param("record") Relation_Activity_User record);

    Relation_Activity_User getRecordById(@Param("recordId") String recordId);

    // 获取所有记录
    List<Section> getAllRecords();

    // 获取记录-根据用户id
    List<Section> getRecordNumberByUserId(@Param("userId") String userId);

    // 获取记录数量-根据成员id
    Integer getRecordNumberByMemberId(@Param("memberId") String memberId);

    // 获取记录数量-根据活动id
    Integer getRecordNumberByActivityId(@Param("activityId") String activityId);

    // 获取记录数量-根据活动id，用户id
    Integer getRecordByActivityIdAndUserId(@Param("activityId") String activityId, @Param("userId") String userId);
}
