
package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.Relation_Activity_UserDAO;
import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Relation_Activity_User;
import com.adj.xiaotuanti.pojo.Section;
import com.adj.xiaotuanti.service.Relation_Activity_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("recordService")
public class RelationActivityUserServiceImpl implements Relation_Activity_UserService {

    private final Relation_Activity_UserDAO recordDAO;

    @Autowired
    public RelationActivityUserServiceImpl(Relation_Activity_UserDAO recordDAO) {
        this.recordDAO = recordDAO;
    }

    public void addRecord(Relation_Activity_User record) {
        recordDAO.addRecord(record);
    }

    public void updateRecord(Relation_Activity_User record) {
        recordDAO.updateRecord(record);
    }

    public void deleteRecord(Relation_Activity_User record) {
        recordDAO.deleteRecord(record);
    }

    public void deleteRecordByActivity(Activity activity) {
        recordDAO.deleteRecordByActivity(activity);
    }

    public void deleteRecordByActivityId(String activityId) {
        recordDAO.deleteRecordByActivityId(activityId);
    }

    public Relation_Activity_User getRecord(Relation_Activity_User record) {
        return recordDAO.getRecord(record);
    }

    public Relation_Activity_User getRecordById(String recordId) {
        return recordDAO.getRecordById(recordId);
    }

    public Integer getRecordByActivityIdAndUserId(String activityId, String userId) {
        return recordDAO.getRecordByActivityIdAndUserId(activityId, userId);
    }

    @Override
    public List<Section> getRecordNumberByUserId(String userId) {
        return recordDAO.getRecordNumberByUserId(userId);
    }

    @Override
    public Integer getRecordNumberByMemberId(String memberId) {
        return recordDAO.getRecordNumberByMemberId(memberId);
    }

    @Override
    public Integer getRecordNumberByActivityId(String activityId) {
        return recordDAO.getRecordNumberByActivityId(activityId);
    }

    public Boolean isParticipated(String activityId, String userId) {
        return recordDAO.getRecordByActivityIdAndUserId(activityId, userId) != 0;
    }
}
