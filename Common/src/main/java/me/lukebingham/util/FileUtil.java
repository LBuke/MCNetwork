package me.lukebingham.util;

import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by LukeBingham on 06/04/2017.
 */
public class FileUtil {

    private List<String> fileList;

    public static void zip(File source, File output) throws IOException {
        FileUtil fileUtil = new FileUtil();
        fileUtil.fileList = Lists.newArrayList();

        fileUtil.generateFileList(fileUtil, source);

        byte[] buffer = new byte[1024];
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(output))) {
            for(String file : fileUtil.fileList) {
                System.out.println("# " + fileUtil.replace(file.replace(source.getCanonicalPath(), "")));
                ZipEntry entry = new ZipEntry(fileUtil.replace(file.replace(source.getCanonicalPath(), "")));
                zos.putNextEntry(entry);

                FileInputStream inputStream = new FileInputStream(new File(file));
                int length;
                while((length = inputStream.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                inputStream.close();
            }

            zos.close();
        }
    }

    public static void unzip(File source, File output) throws IOException {
        FileUtil fileUtil = new FileUtil();
        fileUtil.fileList = Lists.newArrayList();
        byte[] buffer = new byte[1024];
        fileUtil.createDirectory(output.getCanonicalPath());

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String fileName = entry.getName();
                File file = new File(output, fileName);
                System.out.println(fileName);

                new File(file.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(file);
                int length;
                while ((length = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
                entry = zis.getNextEntry();
                fos.close();
            }

            zis.closeEntry();
        }
    }

    private void generateFileList(FileUtil fileUtil, File node) throws IOException {
        if(node.isDirectory()) {
            String[] subNode = node.list();
            assert subNode != null;
            for(String name : subNode) {
                generateFileList(fileUtil, new File(node, name));
            }
        }
        else fileUtil.fileList.add(node.getCanonicalPath());
    }

    private void createDirectory(String dir) {
        File file = new File(dir);
        if(!file.exists()) file.mkdirs();
    }

    private String replace(String input) {
        return input.replaceFirst(Pattern.quote("\\"), "").replaceFirst(Pattern.quote("/"), "");
    }
}
