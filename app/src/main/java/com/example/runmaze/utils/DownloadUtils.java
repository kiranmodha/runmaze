package com.example.runmaze.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class DownloadUtils {

    private static DownloadUtils sInstance;

    private OkHttpClient client;

    private ExecutorService downloadService;

    private DownloadUtils() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        downloadService = Executors.newCachedThreadPool();
    }

    public static DownloadUtils getInstance() {
        if (sInstance == null)
        {
            sInstance = new DownloadUtils();
        }
        return sInstance;
    }

    public Bitmap downloadImage(@NotNull String url)
    {
        final Bitmap[] result = new Bitmap[1];
        if (url.startsWith("https://")  || url.startsWith("http://")) {
            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            final CountDownLatch latch = new CountDownLatch(1);
            downloadService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response response = client.newCall(request).execute();
                        result[0] = BitmapFactory.decodeStream(response.body().byteStream());

                        latch.countDown();
                    } catch (IOException e)
                    {
                        latch.countDown();
                    }
                }
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result[0];
        } else {
            return null;
        }
    }

}
