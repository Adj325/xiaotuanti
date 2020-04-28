package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.Item;

import java.util.List;

public interface ItemService {

    void addItem(Item item);
    
    void updateItem(Item item);
    
    void deleteItem(Item item);

    void deleteItemById(String itemId);

    Item getItem(Item item);
    
    Item getItemById(String itemId);

    List<Item> getAllItems();

    List<Item> getItemsByTeamId(String teamId);

    List<Item> getItemsByKeyword(String keyword);

    List<Item> getItemsByTeamIdAndKeyword(String teamId, String keyword);
}
    