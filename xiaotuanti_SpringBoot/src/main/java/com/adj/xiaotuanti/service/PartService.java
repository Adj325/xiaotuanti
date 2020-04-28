
package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Part;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartService {

    void addPart(Part part);
    
    void updatePart(Part part);
    
    void deletePart(Part part);
    
    void deletePartById(String partId);

    void deletePartByActivity(Activity activity);

    void deletePartByActivityId(String activityId);

    Part getPart(Part part);
    
    Part getPartById(String partId);

    List<Part> getPartsByActivity(Activity activity);

    List<Part> getPartsByActivityId(String activityId);

}
    