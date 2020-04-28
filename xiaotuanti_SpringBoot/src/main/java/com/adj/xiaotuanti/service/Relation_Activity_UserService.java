package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Relation_Activity_User;
import com.adj.xiaotuanti.pojo.Section;

import java.util.List;

public interface Relation_Activity_UserService {

    void addRecord(Relation_Activity_User record);

    void updateRecord(Relation_Activity_User record);

    void deleteRecord(Relation_Activity_User record);

    void deleteRecordByActivity(Activity activity);

    void deleteRecordByActivityId(String activityId);

    Relation_Activity_User getRecord(Relation_Activity_User record);

    Relation_Activity_User getRecordById(String recordId);

    Integer getRecordByActivityIdAndUserId(String activityId, String userId);

    List<Section> getRecordNumberByUserId(String userId);

    // 获取记录数量-根据成员id
    Integer getRecordNumberByMemberId(String memberId);

    // 获取记录数量-根据活动id
    Integer getRecordNumberByActivityId(String activityId);

    Boolean isParticipated(String activityId, String userId);
}
