package com.smw.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * function : 存储及文件工具类.
 *
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 *
 * app目录：("/mnt/storage0/Android/data/packagename") if the phone has SD card,else return path("data/data/packagename/cache/")
 *
 * <p><p/>
 * Created by lzj on 2015/12/31.
 */
@SuppressWarnings({"unused", "deprecation", "ResultOfMethodCallIgnored", "JavaDoc", "UnusedReturnValue", "WeakerAccess"})
public class FileStorageUtil {
    private static final String TAG = FileStorageUtil.class.getSimpleName();

    /**
     * 日志记录目录
     */
    public static final String PATH_BASE_LOG = "/.crashLog/";
    /**
     * 日志记录目录
     */
    public static final String PATH_BASE_EVENT = "/event/";

    /**
     * 临时文件目录
     */
    public static final String PATH_BASE_TEMP = "/temp/";
    /**
     * 公共图片文件目录
     */
    public static final String PATH_BASE_PICTURE = "/picture/";
    /**
     * 公共图片文件缓存目录
     */
    public static final String PATH_BASE_PICTURE_CACHE = PATH_BASE_PICTURE + "/cache";
    /**
     * 视频文件目录
     */
    public static final String PATH_BASE_VIDEO = "/video/";
    /**
     * 聊天文件
     */
    public static final String PATH_BASE_FILE = "/ file/";

    /**
     * 初始化App的文件目录，创建好所需要的目录文件
     */
    public static void initAppDir(Context context) {
        getAppCacheDirPath(context);
        getLogDirPath(context);
        getTempDirPath(context);
        getPictureDirPath(context);
        getPictureCacheDirPath(context);
        getVideoDirPath(context);
        clearTempDir(context);//清空临时文件夹
        ignoreSystemScanMedia(context, FileStorageUtil.getAppRootDirPath(context));
    }


    /** 清除临时文件夹中的所有文件 */
    public static void clearTempDir(Context context) {
        File[] files = new File(getTempDirPath(context)).listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    /**
     * 忽略指定目录下的所有文件不被系统媒体自动扫描
     *
     * @param context     上下文
     * @param rootDirPath 指定目录路径
     * @return 是否成功
     */
    public static boolean ignoreSystemScanMedia(Context context, String rootDirPath) {
        boolean result = false;
        try {
            if (!rootDirPath.endsWith(File.separator)) rootDirPath += File.separator;
            if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                File noMediaFile = new File(rootDirPath + ".nomedia");
                result = noMediaFile.exists() || (noMediaFile.mkdirs() && noMediaFile.createNewFile());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /** 获取默认的外部存储目录 */
    public static File getExternalStorageDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    /**
     * 获取APP根目录
     *
     * @return File(" / mnt / storage0 / Android / data / packagename ") if the phone has SD card,else return File("data/data/packagename/cache")
     */
    public static File getAppRootDir(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (null == getExternalStorageDirectory() || externalCacheDir == null) {
            return context.getCacheDir();
        } else {
            File externalAppRootDir = externalCacheDir.getParentFile();
            if (!externalAppRootDir.exists()) {
                externalAppRootDir.mkdirs();
            }
            return externalAppRootDir;
        }
    }

    /**
     * 获取APP根目录路径
     *
     * @return path(" / mnt / storage0 / Android / data / packagename / ") if the phone has SD card,else return path("data/data/packagename/")
     */
    public static String getAppRootDirPath(Context context) {
        String path = getAppRootDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        return path;
    }

    /**
     * 获取APP缓存目录
     *
     * @return File(" / mnt / storage0 / Android / data / packagename / cache ") if the phone has SD card,else return File("data/data/packagename/cache")
     */
    public static File getAppCacheDir(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (null == getExternalStorageDirectory() || externalCacheDir == null) {
            return context.getCacheDir();
        } else {
            if (!externalCacheDir.exists()) {
                externalCacheDir.mkdirs();
            }
            return externalCacheDir;
        }
    }

    /**
     * 获取APP缓存目录路径
     *
     * @return path(" / mnt / storage0 / Android / data / packagename / cache / ") if the phone has SD card,else return path("data/data/packagename/cache/")
     */
    public static String getAppCacheDirPath(Context context) {
        String path = getAppCacheDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        return path;
    }


    /**
     * 获取日志记录目录文件
     *
     * @return File(" / mnt / storage0 / packagename / .crashLog ") if the phone has SD card,else return File("data/data/packagename/cache/.crashLog")
     */
    public static File getLogDir(Context context) {
        File dir = new File(getAppRootDir( context), PATH_BASE_LOG);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.v(TAG, "getLogDir = " + dir.getAbsolutePath());
        return dir;
    }

    /**
     * 获取日志记录目录文件
     *
     * @return File(" / mnt / storage0 / packagename / .crashLog / ") if the phone has SD card,else return File("data/data/packagename/cache/.crashLog/")
     */
    public static String getLogDirPath(Context context) {
        String path = getLogDir( context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        Log.v(TAG, "getLogDirPath = " + path);
        return path;
    }

    /**
     * 获取埋点事件上报文件
     *
     * @return File(" / mnt / storage0 / packagename / .event ") if the phone has SD card,else return File("data/data/packagename/cache/.event")
     */
    public static File getEventDir(Context context) {
        File dir = new File(getAppRootDir( context), PATH_BASE_EVENT);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.v(TAG, "getEventDir = " + dir.getAbsolutePath());
        return dir;
    }

    /**
     * 获取日志记录目录文件
     *
     * @return File(" / mnt / storage0 / packagename / .event / ") if the phone has SD card,else return File("data/data/packagename/cache/.event/")
     */
    public static String getEventDirPath(Context context) {
        String path = getEventDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        Log.v(TAG, "getEventDirPath = " + path);
        return path;
    }


    /**
     * 获取临时目录文件
     *
     * @return File(" / mnt / storage0 / packagename / temp ") if the phone has SD card,else return File("data/data/packagename/cache/temp")
     */
    public static File getTempDir(Context context) {
        File dir = new File(getAppRootDir(context), PATH_BASE_TEMP);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.v(TAG, "getTempDir = " + dir.getAbsolutePath());
        return dir;
    }

    public static File getFileDir(Context context) {
        File dir = new File(getAppRootDir(context), PATH_BASE_FILE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.v(TAG, "getChatFileDir = " + dir.getAbsolutePath());
        return dir;
    }

    /**
     * 获取临时文件夹路径
     *
     * @return path(" / mnt / storage0 / packagename / temp / ") if the phone has SD card,else return path("data/data/packagename/cache/temp/")
     */
    public static String getTempDirPath(Context context) {
        String path = getTempDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        Log.v(TAG, "getTempDirPath = " + path);
        return path;
    }

    public static String getFilePath(Context context, String fileName) {
        File dir = new File(getAppRootDir(context), PATH_BASE_FILE);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = getFileDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        return path+ fileName;
    }


    /**
     * 获取图片目录
     *
     * @return File(" / mnt / storage0 / packagename / picture ") if the phone has SD card,else return File("data/data/packagename/cache/picture")
     */
    public static File getPictureDir(Context context) {
        File dir = new File(getAppRootDir(context), PATH_BASE_PICTURE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.v(TAG, "getPictureDir = " + dir.getAbsolutePath());
        return dir;
    }

    /**
     * 获取 图片目录路径
     *
     * @return path(" / mnt / storage0 / packagename / picture / ") if the phone has SD card,else return path("data/data/packagename/cache/picture/")
     */
    public static String getPictureDirPath(Context context) {
        String path = getPictureDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        Log.d(TAG, "getPictureDirPath = " + path);
        return path;
    }

    /**
     * 获取图片缓存目录
     *
     * @return File(" / mnt / storage0 / packagename / picture / cache / ") if the phone has SD card,else return File("data/data/packagename/cache/picture/cache/")
     */
    public static File getPictureCacheDir(Context context) {
        File dir = new File(getAppRootDir(context), PATH_BASE_PICTURE_CACHE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.d(TAG, "getPictureCacheDir = " + dir.getAbsolutePath());
        return dir;
    }

    /**
     * 获取 图片缓存目录路径
     *
     * @return path(" / mnt / storage0 / packagename / picture / cache / ") if the phone has SD card,else return path("data/data/packagename/cache/picture/cache/")
     */
    public static String getPictureCacheDirPath(Context context) {
        String path = getPictureCacheDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        Log.v(TAG, "getPictureCacheDirPath = " + path);
        return path;
    }

    /**
     * 获取视频目录
     *
     * @return File(" / mnt / storage0 / packagename / video / ") if the phone has SD card,else return File("data/data/packagename/cache/video/")
     */
    public static File getVideoDir(Context context) {
        File dir = new File(getAppRootDir(context), PATH_BASE_VIDEO);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Log.v(TAG, "getVideoDir = " + dir.getAbsolutePath());
        return dir;
    }

    /**
     * 获取视频目录路径
     *
     * @return path(" / mnt / storage0 / packagename / video / ") if the phone has SD card,else return path("data/data/packagename/cache/video/")
     */
    public static String getVideoDirPath(Context context) {
        String path = getVideoDir(context).getAbsolutePath();
        if (!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path + File.separator;
        }
        Log.v(TAG, "getVideoDirPath = " + path);
        return path;
    }

    /**
     * 获取应用根目录下的文件(eg : "/mnt/storage0/packagename/abc.txt)
     *
     * @param fileName 需要获取的文件名称 eg:abc.txt
     * @return 存在则返回该文件，否则返回null
     */
    public static File getFileInRootDir(Context context,final String fileName) {
        String filePath = getAppRootDirPath(context) + fileName;
        File   file     = new File(filePath);

        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 获取临时目录下的文件(eg : "/mnt/storage0/packagename/temp/abc.txt)
     *
     * @param fileName 需要获取的文件名称 eg:abc.txt
     * @return 存在则返回该文件，否则返回null
     */
    public static File getFileInTempDir(Context context,final String fileName) {
        String filePath = getTempDirPath(context) + fileName;
        File   file     = new File(filePath);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 获取图片目录下的文件(eg : "/mnt/storage0/packagename/picture/abc.txt)
     *
     * @param fileName 需要获取的文件名称 eg:abc.txt
     * @return 存在则返回该文件，否则返回null
     */
    public static File getFileInPictureDir(Context context,final String fileName) {
        String filePath = getTempDirPath(context) + fileName;
        File   file     = new File(filePath);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    /////////////////////////////////////////////File 操作/////////////////////////////////////////

    /**
     * 获取以/分割的路径文件名
     *
     * @param path eg: content://storage/data/abc.txt
     * @return 文件名 eg: abc.txt
     */
    public static String getFileNameOnUrl(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 将asset资产目录下的文件拷贝到内存中
     *
     * @param context       上下文
     * @param targetDir     拷贝目标目录
     * @param assetFileName 拷贝文件名
     * @param force         是否强制拷贝,true : 忽略文件是否存在，false : 存在则不拷贝
     */
    public static File copyFileFromAsset(Context context, final String targetDir, final String assetFileName, boolean force) {
        File file = new File(targetDir, assetFileName);
        if (file.exists() && file.length() > 0) {
            if (force) {
                file.delete();
            } else {
                return file;
            }
        }
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            InputStream      is     = context.getAssets().open(assetFileName);
            FileOutputStream fos    = new FileOutputStream(file);
            byte[]           buffer = new byte[1024 * 10];
            int              len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将文件拷贝到指定目录
     *
     * @param targetDir  拷贝目标目录
     * @param sourceFile 拷贝文件名
     * @param force      是否强制拷贝,true : 忽略文件是否存在，false : 存在则不拷贝
     */
    public static boolean copyFile(String targetDir, File sourceFile, boolean force) {
        if (targetDir == null || targetDir.length() == 0) {
            return false;
        }
        if (sourceFile == null || !sourceFile.isFile()) {
            return false;
        }

        File targetFile = new File(targetDir, sourceFile.getName());
        if (targetFile.exists() && targetFile.length() > 0) {
            if (force) {
                targetFile.delete();
            } else {
                return true;
            }
        }
        try {
            InputStream      is     = new FileInputStream(sourceFile);
            FileOutputStream fos    = new FileOutputStream(targetFile);
            byte[]           buffer = new byte[1024 * 10];
            int              len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定路径的文件
     *
     * @param filePath 文件路径
     */
    public static boolean deleteFile(String filePath) {
        boolean result = false;
        File    file   = new File(filePath);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /**
     * 删除目录下所有文件包括子目录(不删除根目录)
     *
     * @param dir 当前需要删除的目录文件
     */
    public static boolean deleteDirsAndFile(File dir) {
        return deleteDirsAndFile(dir, false);
    }

    /**
     * 删除目录下所有文件包括子目录
     *
     * @param dir        当前需要删除的目录文件
     * @param selfDelete 是否删除根目录
     */
    public static boolean deleteDirsAndFile(File dir, boolean selfDelete) {
        if (dir == null) return true;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirsAndFile(file, true);
                }
                file.delete();
            }
        }
        if (selfDelete) dir.delete();
        return true;
    }


    /**
     * 计算文件夹目录下所有文件的大小
     */
    public static long sizeFiles(File dir) {
        long result = 0;
        if (dir == null) return result;
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return result;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                result += sizeFiles(file);
            }
            result += file.length();
        }
        return result;
    }

    /**
     * 获取指定文件大小
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
            return size;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param filePath 文件路径
     * @param encoding 写文件编码
     */
    public static String readFileByLines(String filePath, String encoding) {
        BufferedReader reader = null;
        StringBuilder  sb     = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 读取指定目录文件的文件内容
     *
     * @param filePath 包含文件名称的文件路径
     * @return 文件内容 or null
     */
    public static String readFile(String filePath) {
        byte[]                data      = null;
        FileInputStream       inStream  = null;
        ByteArrayOutputStream outStream = null;
        try {
            inStream = new FileInputStream(filePath);
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 10];
            int    len;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            data = outStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inStream) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outStream) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (null != data) {
            return new String(data);
        } else {
            return null;
        }
    }

    /**
     * 读取raw目录的文件内容
     *
     * @param rawFileResId raw文件名资源id
     */
    public static String readValueInRaw(Context context,int rawFileResId) {
        String result = null;
        try {
            InputStream is     = context.getResources().openRawResource(rawFileResId);
            int         len    = is.available();
            byte[]      buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context  内容上下文
     * @param fileName 文件名称，包含扩展名
     */
    public static String readValueInAssets(Context context, String fileName) {
        String      result      = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
            int    len    = inputStream.available();
            byte[] buffer = new byte[len];
            inputStream.read(buffer);
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 指定编码保存内容
     *
     * @param filePath 文件路径
     * @param content  保存的内容
     */
    public static void writeStringToFile(String filePath, String content) {
        writeStringToFile(filePath, content, "UTF-8");
    }

    /**
     * 指定编码保存内容
     *
     * @param filePath 文件路径
     * @param content  保存的内容
     * @param encoding 写文件编码
     */
    public static void writeStringToFile(String filePath, String content, String encoding) {
        BufferedWriter writer = null;
        File           file   = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), encoding));
                writer.write(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void appendStringToFile(String content, File file) {
        appendStringToFile(content, file, "UTF-8");
    }

    /**
     * 向文件中追加文本
     *
     * @param content  需要追加的内容
     * @param file     待追加文件源
     * @param encoding 文件编码
     */
    public static void appendStringToFile(String content, File file, String encoding) {
        BufferedWriter writer = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), encoding));
                writer.write(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 规范file地址为Uri格式(追加file://)
     *
     * @param path 文件路径 eg: /sdcard/0/aa.jpg;
     * @return uri文件路径 eg；file:///sdcard/0/aa.jpg.
     */
    public static String formatFileUri(String path) {
        if (!StringUtils.isEmpty(path) && !path.startsWith("file://")) {
            path = "file://" + path;
        }
        return path;
    }

    /**
     * 规范file地址为Uri格式(去掉file://)
     *
     * @return uri文件路径 eg；file:///sdcard/0/aa.jpg.
     */
    public static String formatFileUri2Normal(String uri) {
        if (!StringUtils.isEmpty(uri) && uri.startsWith("file://")) {
            uri.replace("file://", "");
        }
        return uri;
    }

    public static File writeInputStream(InputStream inputStream, String filePath) {
        OutputStream outputStream = null;
        // 在指定目录创建一个空文件并获取文件对象
        File mFile = new File(filePath);
        if (!mFile.getParentFile().exists())
            mFile.getParentFile().mkdirs();
        try {
            outputStream = new FileOutputStream(mFile);
            byte buffer[] = new byte[4 * 1024];
            int  lenght;
            while ((lenght = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, lenght);
            }
            outputStream.flush();
        } catch (IOException e) {
            Log.e(TAG, "writeInputStram fail, cause ：" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException ：" + e.getMessage());
            }
        }
        return mFile;
    }

    /**
     * 拷贝所有代码到一个word文件中
     *
     * @param dir        代码根目录
     * @param resultFile 生成的文件
     */
    public static void copyAllCodeIntoWord(File dir, File resultFile) {
        if (dir == null || !dir.isDirectory()) return;
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) return;
        for (File file : files) {
            if (file.isDirectory()) {
                copyAllCodeIntoWord(file, resultFile);
                continue;
            }
            String fileName      = file.getName();
            String fileNameInput = "========== " + fileName + " ==========" + "\n";
            appendStringToFile(fileNameInput, resultFile);
            appendStringToFile(readFile(file.getAbsolutePath()), resultFile);
            appendStringToFile("\n", resultFile);
        }
    }

    /** 创建文件，父目录不存在则创建父目录 */
    public static void createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) file.delete();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 移动文件
     *
     * @param fromFile 源文件
     * @param toFile   目标文件
     */
    public static void moveFileTo(File fromFile, File toFile) throws IOException {
        FileInputStream  ins   = new FileInputStream(fromFile);
        FileOutputStream out   = new FileOutputStream(toFile);
        byte[]           bytes = new byte[1024 * 4];
        int              len;
        while ((len = ins.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        ins.close();
        out.close();
        fromFile.delete();
    }


    private void requestPermission(Activity activity) {

    }

    //保存文件到指定路径
    public static boolean saveImageToGallery(Activity context, Bitmap bmp) {
        // 首先保存图片
        String storePath = getPictureDirPath(context);
        File   appDir    = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File   file     = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        String suffix = "";
        try {
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        }catch (Exception e){}
        return suffix;
    }

    /**
     * 转化文件大小
     *
     * @param size
     * @return
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    public static boolean judeFileExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

}
