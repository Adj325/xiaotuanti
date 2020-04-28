package com.adj.xiaotuanti.service.impl;

import com.adj.xiaotuanti.dao.PostDAO;
import com.adj.xiaotuanti.pojo.Post;import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.Post;
import com.adj.xiaotuanti.service.PostService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("postService")
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDAO;

	public void addPost(Post post) {
		postDAO.addPost(post);
	}

	public void updatePost(Post post) {
		postDAO.updatePost(post);
	}

	public void deletePost(Post post) {
		postDAO.deletePost(post);
	}
    
	public void deletePostById(Integer postId) {
		postDAO.deletePostById(postId);
	}
    
	public Post getPost(Post post) {
		return postDAO.getPost(post);
	}
    
	public Post getPostById(Integer postId) {
		return postDAO.getPostById(postId);
	}

	public List<Post> getAllPosts() {
		return postDAO.getAllPosts();
	}

	public Post getPostByUser(User user) {
		return postDAO.getPostByUser(user);
	}

	public void deletePostByUser(User user) {
		postDAO.getPostByUser(user);
	}

	public void deletePostsByUser(User user) {
		postDAO.getPostsByUser(user);
	}

	@Override
	public List<Post> getAllPostsByKeywords(List<String> keywords) {
		return postDAO.getAllPostsByKeywords(keywords);
	}

	@Override
	public List<Post> getPostsByUserAndKeywords(User user, List<String> keywords) {
		return postDAO.getPostsByUserAndKeywords(user, keywords);
	}

	@Override
	public List<Post> getPostsByUser(User user) {
		return postDAO.getPostsByUser(user);
	}
}