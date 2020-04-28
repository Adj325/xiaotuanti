
package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.BinDAO;
import com.adj.xiaotuanti.pojo.Bin;
import com.adj.xiaotuanti.service.BinService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("binService")
public class BinServiceImpl implements BinService {

    private final BinDAO binDAO;

    @Autowired
    public BinServiceImpl(BinDAO binDAO) {
        this.binDAO = binDAO;
    }

    public void addBin(Bin bin) {
        binDAO.addBin(bin);
    }

    public void updateBin(Bin bin) {
        binDAO.updateBin(bin);
    }

    public void deleteBin(Bin bin) {
        File file = new File(bin.getPath());
        if(file.delete()){
            binDAO.deleteBin(bin);
        }
    }

    public void deleteBinById(String binId) {
        File file = new File(getBinById(binId).getPath());
        if(file.delete()){
            binDAO.deleteBinById(binId);
        }
    }

    public Bin getBin(Bin bin) {
        return binDAO.getBin(bin);
    }

    public Bin getBinById(String binId) {
        return binDAO.getBinById(binId);
    }
}
