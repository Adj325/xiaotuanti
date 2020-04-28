package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Bin;

public interface BinService {

    void addBin(Bin bin);

    void updateBin(Bin bin);

    void deleteBin(Bin bin);

    void deleteBinById(String binId);

    Bin getBin(Bin bin);

    Bin getBinById(String binId);

}
    