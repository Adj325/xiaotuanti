
package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Bin;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("BinDAO")
public interface BinDAO {

    void addBin(@Param("bin") Bin bin);

    void updateBin(@Param("bin") Bin bin);

    void deleteBin(@Param("bin") Bin bin);

    void deleteBinById(@Param("binId") String binId);

    Bin getBin(@Param("bin") Bin bin);

    Bin getBinById(@Param("binId") String binId);

}
