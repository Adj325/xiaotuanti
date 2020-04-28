
package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Activity;
import com.adj.xiaotuanti.pojo.Part;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PartDAO")
public interface PartDAO {

    void addPart(@Param("part") Part part);

    void updatePart(@Param("part") Part part);

    void deletePart(@Param("part") Part part);

    void deletePartById(@Param("partId") String partId);

    void deletePartByActivity(@Param("activity") Activity activity);

    void deletePartByActivityId(@Param("activityId") String activityId);

    Part getPart(@Param("part") Part part);

    Part getPartById(@Param("partId") String partId);

    List<Part> getPartsByActivity(@Param("activity") Activity activity);

    List<Part> getPartsByActivityId(@Param("activityId") String activityId);

}
