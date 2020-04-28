package com.adj.xiaotuanti.dao;

import com.adj.xiaotuanti.pojo.Post;
import com.adj.xiaotuanti.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PostDAO")
public interface PostDAO {

    void addPost(@Param("post") Post post);

    void updatePost(@Param("post") Post post);

    void deletePost(@Param("post") Post post);

    void deletePostById(@Param("postId") Integer postId);

    Post getPost(@Param("post") Post post);

    Post getPostById(@Param("postId") Integer postId);

    List<Post> getAllPosts();

    Post getPostByUser(@Param("user") User user);

    void deletePostByUser(@Param("user") User user);

    void deletePostsByUser(@Param("user") User user);

    Post getPostByUserId(@Param("userId") Integer userId);

    List<Post> getPostsByUserId(@Param("userId") Integer userId);

    void deletePostByUserId(@Param("userId") Integer userId);

    void deletePostsByUserId(@Param("userId") Integer userId);

    List<Post> getAllPostsByKeywords(@Param("keywords") List<String> keywords);

    List<Post> getPostsByUserAndKeywords(@Param("user") User user, @Param("keywords") List<String> keywords);

    List<Post> getPostsByUser(@Param("user") User user);

}
