package com.ymt.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class FileUtils {

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024 * 10];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath
                            + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 获得可编辑的文件列表
     *
     * @param files
     * @param path
     */
    public static void getName(List<String> files, String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] dirFile = file.listFiles();
            for (File f : dirFile) {
                if (f.isDirectory())
                    getName(files, f.getAbsolutePath());
                else {
                    if (f.getName().endsWith(".java")
                            || f.getName().endsWith(".xml")
                            || f.getName().endsWith(".bat"))
                        files.add(f.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 读取文件中内容
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readFileToString(String path) throws IOException {
        String resultStr = null;
        FileInputStream fis = null;
        BufferedReader buf = null;
        try {
            fis = new FileInputStream(path);
            String line = null;
            buf = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            if (fis != null)
                fis.close();
        }
    }

    public static String ReadTxtFile(String FileName) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(
                    new FileInputStream(FileName));
            ByteArrayOutputStream memStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                memStream.write(buffer, 0, len);
            }
            byte[] data = memStream.toByteArray();
            bufferedInputStream.close();
            memStream.close();
            bufferedInputStream.close();
            return new String(data, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void deleteDir(String path, boolean bDelRoot) {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            File[] tmp = dir.listFiles();
            if (null != tmp) {
                for (int i = 0; i < tmp.length; i++) {
                    if (tmp[i].isDirectory()) {
                        deleteDir(path + "/" + tmp[i].getName(), true);
                    } else {
                        tmp[i].delete();
                    }
                }
            }
            if (bDelRoot) {
                dir.delete();
            }
        }
    }

    public static void deleteFile(String file) {
        File f = new File(file);
        f.delete();
    }

    public static BufferedOutputStream getOutputStream(File file)
            throws IOException {
        return new BufferedOutputStream(new FileOutputStream(file), 64 * 1024);
    }

    public static BufferedOutputStream getOutputStream(String file)
            throws IOException {
        return getOutputStream(new File(file));
    }

    public static void close(OutputStream out) {
        if (null != out) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out = null;
        }
    }

    public static void close(InputStream in) {
        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            in = null;
        }
    }

    private static void createDirIfNotExist(File file) {
        if (null == file) {
            return;
        }
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
    }

    public static void createDirIfNotExist(String filePath) {
        File file = new File(filePath);
        createDirIfNotExist(file);
    }

    public static void writeFile(String str, String descFile, boolean append)
            throws Exception {
        if ((null == str) || (null == descFile)) {
            return;
        }

        createDirIfNotExist(descFile);

        BufferedOutputStream out = null;

        try {
            byte[] src = str.getBytes("UTF-8");
            out = new BufferedOutputStream(new FileOutputStream(descFile,
                    append), 1024 * 64);
            out.write(src);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(out);
        }
    }

    public static void appendFile(String str, String descFile) throws Exception {
        writeFile(str, descFile, true);
    }

}