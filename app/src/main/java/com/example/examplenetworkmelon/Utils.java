package com.example.examplenetworkmelon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.util.ByteArrayBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dongja94 on 2015-12-08.
 */
public class Utils {
    public static Bitmap getBitmap(InputStream is, int width, int height) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, opts);
        int scale = adjustSampleSize(width, height, opts);
        opts = new BitmapFactory.Options();
        opts.inSampleSize = scale;
        try {
            is.reset();
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);
            if (bitmap != null) {
                Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                bitmap.recycle();
                return resizeBitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getSupportResetInputStream(InputStream is) {
        if (is.markSupported()) return is;
        ByteArrayBuffer baf = new ByteArrayBuffer(0);
        byte[] buffer = new byte[8096];
        int len;
        try {
            while((len = is.read(buffer)) > 0) {
                baf.append(buffer, 0, len);
            }
            return new ByteArrayInputStream(baf.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int adjustSampleSize(int width, int height, BitmapFactory.Options options) {
        int widthScale = options.outWidth / width;
        int heightScale = options.outHeight / height;
        int scale = widthScale < heightScale ? widthScale : heightScale;
        if (scale == 0) {
            scale = 1;
        }
        return scale;
    }
}
