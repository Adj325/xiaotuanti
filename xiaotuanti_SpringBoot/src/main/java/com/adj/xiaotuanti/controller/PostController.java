package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Post;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.pojo.Post;

import com.adj.xiaotuanti.service.PostService;
import com.adj.xiaotuanti.service.UserService;

import com.adj.xiaotuanti.util.DateUtils;
import com.adj.xiaotuanti.util.WordUtils;
import org.nlpcn.commons.lang.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;

import java.util.*;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    private HashMap toMap(Post post) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", post.getId());
        if (StringUtil.isNotBlank(post.getUserAuthor().getRealName())){
            hashMap.put("userName", post.getUserAuthor().getRealName());
        }else {
            hashMap.put("userName", post.getUserAuthor().getNickName());
        }
        hashMap.put("title", post.getTitle());
        hashMap.put("content", post.getContent());
        hashMap.put("postTime", DateUtils.Date2String(post.getPostTime()));
        hashMap.put("type", post.getType());
        return hashMap;
    }

    private HashMap[] toMapArray(List<Post> postList) {
        HashMap<String, Object>[] hashMaps = new HashMap[postList.size()];
        for (int index = 0; index < postList.size(); index++) {
            Post post = postList.get(index);
            hashMaps[index] = new HashMap<String, Object>();
            hashMaps[index].put("id", post.getId());
            if (StringUtil.isNotBlank(post.getUserAuthor().getRealName())){
                hashMaps[index].put("userName", post.getUserAuthor().getRealName());
            }else {
                hashMaps[index].put("userName", post.getUserAuthor().getNickName());
            }
            hashMaps[index].put("title", post.getTitle());
            hashMaps[index].put("content", post.getContent());
            hashMaps[index].put("postTime", DateUtils.Date2String(post.getPostTime()));
            hashMaps[index].put("type", post.getType());
        }
        return hashMaps;
    }

    @GetMapping(value = "api/posts/{postId}")
    @ResponseBody
    public String getPost(@RequestAttribute User user, @PathVariable Integer postId) {
        Map<String, Object> frontResultMap = new HashMap<String, Object>();
        Post post = postService.getPostById(postId);
        if (post == null) {
            frontResultMap.put("code", -1);
            frontResultMap.put("msg", "帖子不存在");
            return JSON.toJSONString(frontResultMap);
        }
        frontResultMap.put("code", 1);
        frontResultMap.put("data", toMap(post));
        frontResultMap.put("msg", "获取成功");
        return JSON.toJSONString(frontResultMap);
    }


    @GetMapping(value = "api/posts")
    @ResponseBody
    public String getPost(@RequestAttribute User user,
                          @RequestParam(required = true) String type,
                          @RequestParam(required = true) String keyword) {
        List<Post> posts = new ArrayList<>();
        switch (type) {
            case "all":
                if (StringUtil.isNotBlank(keyword)) {
                    List<String> keywords = WordUtils.getNlpSeg(keyword);
                    posts = postService.getAllPostsByKeywords(keywords);
                } else {
                    posts = postService.getAllPosts();
                }
                break;
            case "user":
                if (StringUtil.isNotBlank(keyword)) {
                    List<String> keywords = WordUtils.getNlpSeg(keyword);
                    posts = postService.getPostsByUserAndKeywords(user, keywords);
                } else {
                    posts = postService.getPostsByUser(user);
                }
                break;
            default:
                break;
        }
        Map<String, Object> frontResultMap = new HashMap<String, Object>();
        frontResultMap.put("data", toMapArray(posts));
        return JSON.toJSONString(frontResultMap);
    }

    @PostMapping(value = "api/posts")
    @ResponseBody
    public String addPost(
            @RequestAttribute User user, @RequestBody Post postDto) {
        Map<String, Object> frontResultMap = new HashMap<String, Object>();
        postDto.setUserAuthor(user);
        postDto.setPostTime(new Date());
        postService.addPost(postDto);
        frontResultMap.put("code", 1);
        frontResultMap.put("data", postDto);
        frontResultMap.put("msg", "成功发起讨论");
        return JSON.toJSONString(frontResultMap);
    }

    @PutMapping(value = "api/posts/{postId}")
    @ResponseBody
    public void updatePost(
            @RequestAttribute User user, @PathVariable Integer postId, @RequestBody Map<String, Object> map) {
        Post Post = new Post(map);
        postService.updatePost(Post);
    }

    @DeleteMapping(value = "api/posts/{postId}")
    @ResponseBody
    public void deletePost(
            @RequestAttribute User user, @PathVariable Integer postId, @RequestBody Map<String, Object> map) {
        Post Post = new Post(map);
        postService.deletePost(Post);
    }

}
