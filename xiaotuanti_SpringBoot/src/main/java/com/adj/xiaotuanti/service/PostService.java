package com.adj.xiaotuanti.service;

import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.Post;

import java.util.List;

public interface PostService {

	void addPost(Post post);

	void updatePost(Post post);

	void deletePost(Post post);

	void deletePostById(Integer postId);

	Post getPost(Post post);

	Post getPostById(Integer postId);

	List<Post> getAllPosts();

	Post getPostByUser(User user);

	void deletePostByUser(User user);

	void deletePostsByUser(User user);

    List<Post> getAllPostsByKeywords(List<String> keywords);

    List<Post> getPostsByUserAndKeywords(User user, List<String> keywords);

	List<Post> getPostsByUser(User user);
}