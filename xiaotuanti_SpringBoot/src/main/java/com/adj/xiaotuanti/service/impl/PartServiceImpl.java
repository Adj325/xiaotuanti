
package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.PartDAO;
import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Part;
import com.adj.xiaotuanti.service.PartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("partService")
public class PartServiceImpl implements PartService {

    private final PartDAO partDAO;

    @Autowired
    public PartServiceImpl(PartDAO partDAO) {
        this.partDAO = partDAO;
    }

    public void addPart(Part part) {
        partDAO.addPart(part);
    }

    public void updatePart(Part part) {
        partDAO.updatePart(part);
    }

    public void deletePart(Part part) {
        partDAO.deletePart(part);
    }

    public void deletePartById(String partId) {
        partDAO.deletePartById(partId);
    }

    public void deletePartByActivity(Activity activity) {
        partDAO.deletePartByActivity(activity);
    }

    public void deletePartByActivityId(String activityId) {
        partDAO.deletePartByActivityId(activityId);

    }

    public Part getPart(Part part) {
        return partDAO.getPart(part);
    }

    public Part getPartById(String partId) {
        return partDAO.getPartById(partId);
    }

    public List<Part> getPartsByActivity(Activity activity) {
        return partDAO.getPartsByActivity(activity);
    }

    public List<Part> getPartsByActivityId(String activityId) {
        return partDAO.getPartsByActivityId(activityId);
    }
}
