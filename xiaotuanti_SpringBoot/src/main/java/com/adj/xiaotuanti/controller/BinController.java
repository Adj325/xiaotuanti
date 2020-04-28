package com.adj.xiaotuanti.controller;

import com.adj.xiaotuanti.pojo.Bin;
import com.adj.xiaotuanti.pojo.Token;
import com.adj.xiaotuanti.pojo.User;
import com.adj.xiaotuanti.service.BinService;
import com.adj.xiaotuanti.service.UserService;
import com.adj.xiaotuanti.util.RedisUtils;
import com.adj.xiaotuanti.util.TokenUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class BinController {

    private final BinService binService;
    private final UserService userService;
    private final TokenUtils tokenUtils;
    private final RedisUtils redisUtils;

    @Autowired
    public BinController(BinService binService, UserService userService, TokenUtils tokenUtils, RedisUtils redisUtils) {
        this.binService = binService;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
        this.redisUtils = redisUtils;
    }

    @GetMapping("api/bin/{binId}")
    public void getBin(HttpServletResponse response,
                       @PathVariable String binId,
                       @RequestParam String authorization) throws
            Exception {
        Bin bin = binService.getBinById(binId);
        System.out.println("binId: " + binId);
        if (bin == null)
            return;
        Token token = tokenUtils.unSign(authorization);
        User user = userService.getUserByOpenId(token.getOpenId());
        if (user != null
                && redisUtils.hasKey(user.getId())
                && authorization.equals(redisUtils.get(user.getId()))
        ) {
            String filename = bin.getName();
            // 第二步：设置响应体
            // 获取输入流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(bin.getPath())));
            // 转码，免得文件名中文乱码
            filename = URLEncoder.encode(filename, "UTF-8");
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            // 设置文件ContentType类型
            response.setContentType(bin.getType());

            // 使用缓冲数组加快上传速度
            byte buffer[] = new byte[1024 * 8];
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            int len;
            while ((len = bis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.close();
        }

    }


    @Value("${xiaotuanti.download.directory}")
    private String directory;

    @PostMapping(value = "api/bin")
    public String addBin(@RequestParam("file") MultipartFile file) {
        HashMap<String, Object> result = new HashMap<>();
        System.out.println(file.getOriginalFilename());
        Bin bin = new Bin();
        bin.setName(file.getOriginalFilename());
        bin.setType(file.getContentType());
        String uuid = UUID.randomUUID().toString();
        String binPath = directory + uuid + "-" + file.getOriginalFilename();
        bin.setPath(binPath);
        File binFile = new File(binPath);
        try {
            // 将内存中的数据写入磁盘
            file.transferTo(binFile);
            binService.addBin(bin);
            result.put("code", 1);
            result.put("binId", bin.getId());
            result.put("binName", bin.getName());
            result.put("binType", bin.getType().split("/")[0]);
            result.put("msg", "上传成功！");
        } catch (Exception e) {
            result.put("code", -1);
            result.put("binId", null);
            result.put("binName", null);
            result.put("binType", null);
            result.put("msg", "上传失败！");
        }
        return JSON.toJSONString(result);
    }

    @DeleteMapping(value = "api/api/{binId}")
    @ResponseBody
    public HashMap<String, Object> deleteBin(@PathVariable String binId) {
        Bin bin = binService.getBinById(binId);
        HashMap<String, Object> result = new HashMap<>();
        if (bin == null) {
            result.put("code", "-1");
            result.put("msg", "文件不存在");
        } else {
            result.put("code", "1");
            result.put("msg", "暂不允许删除");
            File xlsFile = new File(bin.getPath());
            if (xlsFile.delete()) {
                result.put("code", "1");
                result.put("msg", "删除成功");
            } else {
                result.put("code", "1");
                result.put("msg", "删除失败");
            }
        }
        return result;
    }
}
