package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.ItemDAO;
import com.adj.xiaotuanti.pojo.Item;
import com.adj.xiaotuanti.service.ItemService;

import com.adj.xiaotuanti.util.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;

    public void addItem(Item item) {
        itemDAO.addItem(item);
    }

    public void updateItem(Item item) {
        itemDAO.updateItem(item);
    }

    public void deleteItem(Item item) {
        itemDAO.deleteItem(item);
    }

    public void deleteItemById(String itemId){
        itemDAO.deleteItemById(itemId);
    }

    public Item getItem(Item item) {
        return itemDAO.getItem(item);
    }

    public Item getItemById(String itemId) {
        return itemDAO.getItemById(itemId);
    }

    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    public List<Item> getItemsByTeamId(String teamId) {
        return itemDAO.getItemsByTeamId(teamId);
    }

    public List<Item> getItemsByKeyword(String keyword){
        return itemDAO.getItemsByKeyword(WordUtils.getNlpSeg(keyword));
    }

    public List<Item> getItemsByTeamIdAndKeyword(String teamId, String keyword){
        return itemDAO.getItemsByTeamIdAndKeyword(teamId, WordUtils.getNlpSeg(keyword));
    }
}
