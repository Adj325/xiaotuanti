package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.*;
import com.adj.xiaotuanti.service.*;
import com.adj.xiaotuanti.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class CommentController {

    private final Relation_Activity_UserService relationActivityUserService;
    private final Relation_Post_CommentService relationPostCommentService;
    private final Relation_Activity_CommentService relationActivityCommentService;
    private final ActivityService activityService;
    private final CommentService commentService;
    private final MemberService memberService;

    @Autowired
    public CommentController(Relation_Activity_UserService relationActivityUserService, PostService postService, Relation_Post_CommentService relationPostCommentService, Relation_Activity_CommentService relationActivityCommentService, ActivityService activityService, CommentService commentService, MemberService memberService) {
        this.relationActivityUserService = relationActivityUserService;
        this.relationPostCommentService = relationPostCommentService;
        this.relationActivityCommentService = relationActivityCommentService;
        this.activityService = activityService;
        this.commentService = commentService;
        this.memberService = memberService;
    }

    private HashMap[] toMapArray(List<Comment> commentList) {
        HashMap<String, Object>[] hashMaps = new HashMap[commentList.size()];
        for (int index = 0; index < commentList.size(); index++) {
            Comment comment = commentList.get(index);
            hashMaps[index] = new HashMap<String, Object>();
            hashMaps[index].put("id", comment.getId());
            hashMaps[index].put("userName", comment.getUserAuthor().getRealName());
            hashMaps[index].put("userAvatarUrl", comment.getUserAuthor().getAvatarUrl());
            hashMaps[index].put("content", comment.getContent());
            hashMaps[index].put("postTime", DateUtils.Date2String(comment.getPostTime()));
        }
        return hashMaps;
    }

    @Transactional
    @PostMapping(value = "api/comments")
    @ResponseBody // 添加Comment
    public HashMap addComment(@RequestAttribute("user") User user, @RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> result = new HashMap<>();
        String type = (String) map.get("type");
        switch (type) {
            case "activityId":
                String activityId = map.get("activityId").toString();
                Activity activity = activityService.getActivityById(activityId);
                if (activity == null) {
                    result.put("code", -1);
                    result.put("msg", "activityId无效");
                    return result;
                } else {
                    Boolean isCommented = commentService.isCommented(activityId, user.getId());
                    Boolean isParticipated = relationActivityUserService.isParticipated(activityId, user.getId());
                    if (!isParticipated) {
                        result.put("code", -1);
                        result.put("msg", "未参与活动，无权评论");
                        return result;
                    } else {
                        if (!isCommented) {
                            Comment comment = new Comment();
                            comment.setUserAuthor(user);
                            comment.setContent(map.get("content").toString());
                            comment.setPostTime(new Date());
                            commentService.addComment(comment);
                            relationActivityCommentService.addRelation(new Relation_Activity_Comment(activity, comment));
                            result.put("code", 1);
                            result.put("msg", "成功评论");
                            return result;
                        } else {
                            result.put("code", 1);
                            result.put("msg", "早已评论");
                            return result;
                        }
                    }
                }
            case "post":
                Integer postId = Integer.parseInt((String) map.get("postId"));
                Post post = new Post();
                post.setId(postId);
                Comment comment = new Comment();
                comment.setUserAuthor(user);
                comment.setContent(map.get("content").toString());
                comment.setPostTime(new Date());
                commentService.addComment(comment);
                relationPostCommentService.addRelation(new Relation_Post_Comment(post, comment));
                result.put("code", 1);
                result.put("msg", "成功评论");
                return result;
            default:
                result.put("code", -1);
                result.put("msg", "type 有误");
                return result;
        }
    }

    @GetMapping(value = "api/comments")
    @ResponseBody // 获取Comment-根据activityId
    public HashMap getCommentsByActivityId(@RequestAttribute("user") User user,
                                           @RequestParam String type,
                                           @RequestParam(required = false) Integer postId,
                                           @RequestParam(required = false) String activityId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Comment> commentList = new ArrayList<>();
        switch (type) {
            case "post":
                commentList = commentService.getCommentsByPostId(postId);
                result.put("data", toMapArray(commentList));
                break;
            case "activity":
                Activity activity = activityService.getActivityById(activityId);
                Member member = memberService.getMemberByUserIdAndTeamId(user.getId(), activity.getTeam().getId());
                if (member == null) {
                    result.put("permission", -1);
                } else {
                    result.put("permission", member.getLevel().ordinal());
                    commentList = commentService.getCommentsByActivityId(activityId);
                    result.put("data", toMapArray(commentList));
                }
                break;
            default:
                result.put("code", -1);
                result.put("data", commentList);
                result.put("msg", "缺少type");
        }
        return result;
    }

}
