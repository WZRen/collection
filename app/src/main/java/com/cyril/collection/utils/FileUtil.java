package com.cyril.collection.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.cyril.collection.MyApplication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by cyril on 2017/1/17.
 */
public class FileUtil {


    /*
    分隔符.
     */
    public final static String FILE_EXTENSION_SEPARATOR = ".";
    /*
    "/"
     */
    public final static String SEP = File.separator;

    /*
    SD卡根目录
     */
    public static final String SDPATH = Environment.getExternalStorageDirectory() + File.separator;

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(status);
    }

    /**
     * 读取文件的内容
     * 默认utf-8编码
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        return readFile(filePath, "utf-8");
    }

    /**
     * 读取文件的内容
     *
     * @param filePath
     * @param charsetName
     * @return
     * @throws IOException
     */
    public static String readFile(String filePath, String charsetName) throws IOException {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        if (TextUtils.isEmpty(charsetName))
            charsetName = "utf-8";
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile())
            return null;
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent.toString();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文本文件到List字符串集合中(默认utf-8编码)
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> readFileToList(String filePath) throws IOException {
        return readFileToList(filePath, "utf-8");
    }

    /**
     * 读取文本文件到List字符串集合中
     *
     * @param filePath
     * @param charsetName
     * @return
     * @throws IOException
     */
    public static List<String> readFileToList(String filePath, String charsetName) throws IOException {
        if (TextUtils.isEmpty(filePath))
            return null;
        if (TextUtils.isEmpty(charsetName))
            charsetName = "utf-8";
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<>();
        if (file == null || !file.isFile()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 向文件中写入数据
     *
     * @param filePath
     * @param content
     * @param append
     * @return
     * @throws IOException
     */
    public static boolean writeFile(String filePath, String content, boolean append) throws IOException {
        if (TextUtils.isEmpty(filePath))
            return false;
        if (TextUtils.isEmpty(content))
            return false;
        FileWriter fileWriter = null;
        try {
            createFile(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.flush();
            return true;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 向文件中写入数据
     * 默认在文件开始处重新写入数据
     *
     * @param filePath
     * @param stream   字节输入流
     * @return
     * @throws IOException
     */
    public static boolean writeFile(String filePath, InputStream stream) throws IOException {
        return writeFile(filePath, stream, false);
    }

    /**
     * 向文件中写入数据
     *
     * @param filePath
     * @param stream
     * @param append   如果为true,则将数据写入文件末尾处;
     *                 为false,清空原来的数据,从头开始写
     * @return
     * @throws IOException
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) throws IOException {
        if (TextUtils.isEmpty(filePath))
            throw new NullPointerException("filePath is Empty");
        if (stream == null)
            throw new NullPointerException("InputStream is null");
        return writeFile(new File(filePath), stream, append);
    }

    /**
     * 向文件中写入数据
     * 默认在文件开始处重新写入数据
     *
     * @param file
     * @param stream
     * @return
     * @throws IOException
     */
    public static boolean writeFile(File file, InputStream stream) throws IOException {
        return writeFile(file, stream, false);
    }

    /**
     * 向文件中写入数据
     *
     * @param file
     * @param stream
     * @param append 为true时,在文件开始处重新写入数据;
     *               为false时,清空原来的数据,从头开始写
     * @return
     * @throws IOException
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) throws IOException {
        if (file == null) {
            throw new NullPointerException("file is null");
        }
        OutputStream out = null;
        try {
            createFile(file.getAbsolutePath());
            out = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                out.write(data, 0, length);
            }
            out.flush();
            return true;
        } finally {
            if (out != null) {
                try {
                    out.close();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制文件
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return
     * @throws IOException
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) throws IOException {
        InputStream inputStream = null;
        inputStream = new FileInputStream(sourceFilePath);
        return writeFile(destFilePath, inputStream);
    }

    //获取缓存文件夹的绝对路径
    public static String getRootCache() {
        File cacheDir = MyApplication.getInstance().getExternalCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getAbsolutePath();
    }

    /**
     * 获取某个目录下的文件名
     *
     * @param dirPath
     * @param filenameFilter 过滤器
     * @return
     */
    public static List<String> getFileNameList(String dirPath, FilenameFilter filenameFilter) {
        if (filenameFilter == null)
            return getFileNameList(dirPath);
        if (TextUtils.isEmpty(dirPath))
            return Collections.emptyList();
        File dir = new File(dirPath);

        File[] files = dir.listFiles(filenameFilter);
        if (files == null)
            return Collections.emptyList();

        List<String> conList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile())
                conList.add(file.getName());
        }
        return conList;
    }

    /**
     * 获取某个目录下的文件名
     *
     * @param dirPath
     * @return
     */
    public static List<String> getFileNameList(String dirPath) {
        if (TextUtils.isEmpty(dirPath))
            return Collections.emptyList();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null)
            return Collections.emptyList();
        List<String> conList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                conList.add(file.getName());
            }
        }
        return conList;
    }

    /**
     * 获取某个目录下的指定扩展名的文件名称
     *
     * @param dirPath
     * @param extension
     * @return
     */
    public static List<String> getFileNameList(String dirPath, final String extension) {
        if (TextUtils.isEmpty(dirPath))
            return Collections.emptyList();
        File dir = new File(dirPath);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if (s.indexOf("." + extension) > 0)
                    return true;
                return false;
            }
        });
        if (files == null) {
            return Collections.emptyList();
        }
        List<String> conList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                conList.add(file.getName());
            }
        }
        return conList;
    }

    /**
     * 获取文件的扩展名
     *
     * @param filePath
     * @return 如果没有扩展名返回""
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return filePath;
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * 创建文件
     *
     * @param path 文件的绝对路径
     * @return
     */
    public static boolean createFile(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        return createFile(new File(path));
    }

    /**
     * 创建文件
     *
     * @param file
     * @return 创建成功返回true
     */
    public static boolean createFile(File file) {
        if (file == null || !makeDirs(getFolderName(file.getAbsolutePath())))
            return false;
        if (!file.exists())
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        return false;
    }

    /**
     * 创建目录（可以是多个）
     *
     * @param filePath 目录路径
     * @return 如果路径为空时，返回false；如果目录创建成功，则返回true，否则返回false
     */
    public static boolean makeDirs(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File folder = new File(filePath);
        return (folder.exists() && folder.isDirectory()) ? true : folder
                .mkdirs();
    }

    /**
     * 创建目录（可以是多个）
     *
     * @param dir 目录
     * @return 如果目录创建成功，则返回true，否则返回false
     */
    public static boolean makeDirs(File dir) {
        if (dir == null)
            return false;
        return (dir.exists() && dir.isDirectory()) ? true : dir.mkdirs();
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 如果路径为空或者为空白字符串，就返回false；如果文件存在，且是文件，
     * 就返回true；如果不是文件或者不存在，则返回false
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * 获得不带扩展名的文件名称
     *
     * @param filePath 文件路径
     * @return
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * 获得文件名
     *
     * @param filePath 文件路径
     * @return 如果路径为空或空串，返回路径名；不为空时，返回文件名
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * 获得所在目录名称
     *
     * @param filePath 文件的绝对路径
     * @return 如果路径为空或空串，返回路径名；不为空时，如果为根目录，返回"";
     * 如果不是根目录，返回所在目录名称，格式如：C:/Windows/Boot
     */
    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 判断目录是否存在
     *
     * @param directoryPath目录路径
     * @return 如果路径为空或空白字符串，返回false；如果目录存在且，确实是目录文件夹，
     * 返回true；如果不是文件夹或者不存在，则返回false
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * 删除指定文件或指定目录内的所有文件
     *
     * @param path 文件或目录的绝对路径
     * @return 路径为空或空白字符串，返回true；文件不存在，返回true；文件删除返回true；
     * 文件删除异常返回false
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        return deleteFile(new File(path));
    }

    /**
     * 删除指定文件或指定目录内的所有文件
     *
     * @param file
     * @return 路径为空或空白字符串，返回true；文件不存在，返回true；文件删除返回true；
     * 文件删除异常返回false
     */
    public static boolean deleteFile(File file) {
        if (file == null)
            throw new NullPointerException("file is null");
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }

        File[] files = file.listFiles();
        if (files == null)
            return true;
        for (File f : files) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * 删除指定目录中特定的文件
     *
     * @param dir
     * @param filter
     */
    public static void delete(String dir, FilenameFilter filter) {
        if (TextUtils.isEmpty(dir))
            return;
        File file = new File(dir);
        if (!file.exists())
            return;
        if (file.isFile())
            file.delete();
        if (!file.isDirectory())
            return;

        File[] lists = null;
        if (filter != null)
            lists = file.listFiles(filter);
        else
            lists = file.listFiles();

        if (lists == null)
            return;
        for (File f : lists) {
            if (f.isFile()) {
                f.delete();
            }
        }
    }

    /**
     * 获得文件或文件夹的大小
     *
     * @param path 文件或目录的绝对路径
     * @return 返回当前目录的大小 ，注：当文件不存在，为空，或者为空白字符串，返回 -1
     */
    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }


    //获取app缓存路径
    public static File getRootCache(Context context) {
        File rootFile = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            rootFile = context.getExternalCacheDir();
        } else {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                rootFile = new File(Environment.getExternalStorageDirectory() + File.separator + context.getPackageName());
            } else {
                rootFile = new File(Environment.getDataDirectory() + File.separator + context.getPackageName());
            }
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
        }
        return rootFile;
    }

    //获取资源文件缓存地址
    public static String getSourceCache(Context context) {
        String sourcePath = getRootCache(context) + File.separator;
        if (!new File(sourcePath).exists()) {
            new File(sourcePath).mkdirs();
        }
        return sourcePath;
    }

    /*
    InputStream 转byte[]
     */
    public static byte[] readStreamToBytes(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int length = -1;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
        out.flush();
        byte[] result = out.toByteArray();
        in.close();
        out.close();
        return result;
    }

    /**
     * 写文件
     *
     * @param in
     * @param file
     * @throws IOException
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        //创建父目录
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file != null && file.exists()) {
            file.delete();
        }
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();
    }

    /*
    得到Bitmap的byte
     */
    public static byte[] bmpToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, output);

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ?
                            Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 根据view来生成bitmap图片,可用于截图功能
     *
     * @param v
     * @return
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        //能画缓存就返回false
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }

        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();

        if (bitmap == null) {
            return null;
        }

        Bitmap bitmap1 = Bitmap.createBitmap(bitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap1;
    }

    /**
     * 解压缩
     *
     * @param zipFilePath   压缩资源文件的路径
     * @param unzipFilePath 资源文件解压缩的路径
     * @throws Exception
     */
    public static void unZipFolder(String zipFilePath, String unzipFilePath) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(unzipFilePath + File.separator + szName);
                folder.mkdirs();
            } else {

                File file = new File(unzipFilePath + szName);
                file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }


    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    private static boolean deleteFile1(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除指定文件夹的路径
     *
     * @param folder
     * @return
     */
    private static boolean deleteDirectory(String folder) {
        boolean flag = false;
        if (folder.endsWith(File.separator)) {
            folder = folder + File.separator;
        }
        File dirFile = new File(folder);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile1(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        return dirFile.delete();
    }


    /**
     * 删除指定路径的文件夹
     *
     * @param folder
     * @return
     */
    public static boolean deleteFolder(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile1(folder);
            } else {
                return deleteDirectory(folder);
            }
        }
    }

    /**
     * @param context
     * @param path
     * @param offlineResources
     */
    //遍历文件夹,获取所有文件
    public static void deepFiles(Context context, String path, HashSet offlineResources) {
        try {
            String str[] = context.getAssets().list(path);
            if (str.length > 0) {//如果是目录
                for (String string : str) {
                    path = path + "/" + string;
                    if (path.contains(".")) {
                        Collections.addAll(offlineResources, path.substring(path.indexOf("/") + 1));
                    }
                    deepFiles(context, path, offlineResources);
                    path = path.substring(0, path.lastIndexOf("/"));
                }
            } else {
                //如果是文件
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
