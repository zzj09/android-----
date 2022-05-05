package com.example.mymusic.Utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

public class ChangeBitmapSize {
    public Bitmap changeBitmapSize(Bitmap bm,int width1,int height1){
        Bitmap bitmap = bm;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //设置想要的大小
        int newWidth=height1;
        int newHeight=300;

        //计算压缩的比率
        float scaleWidth=((float)newWidth)/width;
        float scaleHeight=((float)newHeight)/height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);

        //获取新的bitmap
        bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        bitmap.getWidth();
        bitmap.getHeight();
        Log.e("newWidth","newWidth"+bitmap.getWidth());
        Log.e("newHeight","newHeight"+bitmap.getHeight());
        return bitmap;
    }
}
