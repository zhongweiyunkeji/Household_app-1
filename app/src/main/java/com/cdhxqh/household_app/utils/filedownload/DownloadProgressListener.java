package com.cdhxqh.household_app.utils.filedownload;

public interface DownloadProgressListener {
     /**
      * @param downloadsize  �������ļ���С
      * @param filesize       �ļ��ܴ�С
      */
     public void onDownloadSize(int downloadsize, int filesize);
}  
