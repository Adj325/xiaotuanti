package com.adj.xiaotuanti.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class PrepareConfigure {

    @Value("${xiaotuanti.download.directory}")
    private String directory;

    @PostConstruct
    public void mkdir() {
        System.out.println("预备操作: 创建文件目录");
        String[] dirs = {directory};
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.exists()) {
                System.out.println(dir + " 未创建");
                if (file.mkdirs()) {
                    System.out.println(dir + " 创建成功");
                } else {
                    System.out.println(dir + " 创建失败");
                }
            } else {
                System.out.println(dir + " 已创建");
            }
        }
    }
}
