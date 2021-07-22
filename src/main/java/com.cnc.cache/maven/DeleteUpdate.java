package com.cnc.cache.maven;

import java.io.File;
import java.io.IOException;

/**
 * @author shenss
 * @create 2021-06-29 11:18
 **/
public class DeleteUpdate {
    public static void main(String[] args) throws IOException {
        File dir = new File("D:\\maven\\apache-maven-3.6.0\\repo");
        listDirectory(dir);
    }

    public static void listDirectory(File dir) throws IOException {
        if (!dir.exists())
            throw new IllegalArgumentException("目录：" + dir + "不存在.");
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + " 不是目录。");
        }
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory())
                    //递归
                    listDirectory(file);
                else { // 删除以 lastUpdated 结尾的文件
                    String fileName = file.getName();
                    boolean isLastupdated = fileName.toLowerCase().endsWith("lastupdated");
                    if (isLastupdated) {
                        boolean is_delete = file.delete();
                        System.out.println("删除的文件名 => " + file.getName() + "  || 是否删除成功？ ==> " + is_delete);
                    }
                }
            }
        }
    }
}
