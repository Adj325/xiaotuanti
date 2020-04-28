package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Item;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ItemDAO")
public interface ItemDAO {

    void addItem(@Param("item") Item item);
    
    void updateItem(@Param("item") Item item);
    
    void deleteItem(@Param("item") Item item);

    void deleteItemById(@Param("itemId") String itemId);

    Item getItem(@Param("item") Item item);
    
    Item getItemById(@Param("itemId") String itemId);

    List<Item> getAllItems();

    List<Item> getItemsByTeamId(@Param("teamId") String teamId);

    List<Item> getItemsByKeyword(@Param("keywords") List<String> keywords);

    List<Item> getItemsByTeamIdAndKeyword(@Param("teamId") String teamId, @Param("keywords") List<String> keywords);

}
