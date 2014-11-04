package com.example.oldrain.player;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Untold on 2014/8/24.
 */
public class StickIn {

    String content;
    public StickIn(String content){
        this.content = content;
    }
    public void writeToSDcardFile(String file, String destDirStr,
                                  String szOutText) {

        // 获取扩展SD卡设备状态
        String sDStateString = android.os.Environment.getExternalStorageState();
        szOutText = ToolClass.getTime() + " : " + szOutText;

        File myFile = null;
        // 拥有可读可写权限
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {

            try {

                // 获取扩展存储设备的文件目录
                File SDFile = android.os.Environment.getExternalStorageDirectory();

                File destDir = new File(SDFile.getAbsolutePath() + "/" + destDirStr);//文件目录

                if (!destDir.exists()){//判断目录是否存在，不存在创建
                    destDir.mkdir();//创建目录
                }
                // 打开文件
                myFile = new File(destDir + File.separator + file);

                // 判断文件是否存在,不存在则创建
                if (!myFile.exists()) {
                    myFile.createNewFile();//创建文件
                }

                // 写数据   注意这里，两个参数，第一个是写入的文件，第二个是指是覆盖还是追加，
                //默认是覆盖的，就是不写第二个参数，这里设置为true就是说不覆盖，是在后面追加。
                FileOutputStream outputStream = new FileOutputStream(myFile,true);
                outputStream.write(szOutText.getBytes());//写入内容
                outputStream.close();//关闭流

            } catch (Exception e) {
                // TODO: handle exception
                e.getStackTrace();
            }

        }
    }
}
