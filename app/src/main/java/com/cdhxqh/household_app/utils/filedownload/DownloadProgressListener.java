package com.cdhxqh.household_app.utils.filedownload;

public interface DownloadProgressListener {
     /**
      * @param downloadsize  已下载文件大小
      * @param filesize       文件总大小
      */
     public void onDownloadSize(int downloadsize, int filesize);
}  
