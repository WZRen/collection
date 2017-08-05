package com.cyril.collection.crashcatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.cyril.collection.sendmail.NewMail;
import com.cyril.collection.utils.DateUtil;
import com.cyril.collection.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cyril on 2017/5/9.
 * 全局异常捕获
 * 当程序发生Uncaught异常的时候,有该类来接管程序,并记录错误日志
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler1 implements Thread.UncaughtExceptionHandler {
    public static String TAG = "MyCrash";

    private Thread.UncaughtExceptionHandler exceptionHandler;
    private static CrashHandler1 instance = new CrashHandler1();
    private Context mContext;

    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //保证只有一个实例
    private CrashHandler1() {

    }

    public static CrashHandler1 getInstance() {
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        autoClear(1);
    }


    /*
    当UncaughtExeception发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && exceptionHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            exceptionHandler.uncaughtException(thread, throwable);
        } else {
            SystemClock.sleep(3000);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成
     *
     * @param throwable
     * @return true:如果处理了该异常信息;否则返回false
     */
    public boolean handleException(Throwable throwable) {
        if (throwable == null)
            return false;
        try {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "程序出现错误", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();
            //收集设备参数信息
            collectDeviceInfo(mContext);
            //保存日志
            saveCrashInfoFile(throwable);
//            SystemClock.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    public void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param throwable
     * @return 返回文件名称, 便于将文件传送到服务端
     * @throws Exception
     */
    private String saveCrashInfoFile(Throwable throwable) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            //从异常栈获取异常的类名和方法名
            StackTraceElement[] st = throwable.getStackTrace();
            String exclass = st[0].getClassName();
            String method = st[0].getMethodName();
            sb.append("[类 " + exclass + " ]调用 " + method + " 时在第 " + st[0].getLineNumber()
                    + " 行代码处发生异常!异常类型:" + throwable.getClass().getName() + "\n");
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            String fileName = writeFile(sb.toString());

            //发送邮件
            new Thread(new NewMail("crash-" + new Date(), sb.toString(), FileUtil.getSourceCache(mContext) + "crash" + File.separator + fileName, fileName)).start();

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            sb.append("an error occured while writing file...\r\n");
            writeFile(sb.toString());
        }
        return null;
    }


    private String writeFile(String sb) throws Exception {
        String time = dateFormat.format(new Date());
        String fileName = "crash-" + time + ".log";
        String path = FileUtil.getSourceCache(mContext) + "crash" +
                File.separator;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(path + fileName, true);
        fos.write(sb.getBytes());
        fos.flush();
        fos.close();

        return fileName;
    }

    public static void setTag(String tag) {
        TAG = tag;
    }

    public void autoClear(final int autoClearDay) {
        FileUtil.delete(FileUtil.getSourceCache(mContext) + "crash" +
                File.separator, new FilenameFilter() {
            @Override
            public boolean accept(File file, String filename) {
                String s = FileUtil.getFileNameWithoutExtension(filename);
                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
                String date = "crash-" + DateUtil.getOtherDay(day);
                return date.compareTo(s) >= 0;
            }
        });
    }


}
