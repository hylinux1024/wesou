package net.angrycode.wesou.api;

import android.os.Build;
import android.util.Pair;

import net.angrycode.wesou.BuildConfig;
import net.angrycode.wesou.utils.LogUtils;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lancelot on 2017/4/4.
 */

public abstract class BaseAPI {


    protected OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();

    public Pair<Integer, String> get(Map<String, String> params) {
        String requestUrl = getRequestUrl(params);
        try {
            Request request = commonHeaders()
                    .url(requestUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            int code = response.code();
            String body = response.body().string();
            Pair<Integer, String> pair = new Pair<>(code, body);
            return pair;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return new Pair<>(0, "");
    }

    public Pair<Integer, String> post(Map<String, String> params) {
        String requestUrl = getUrl();
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            if (params != null) {
                params.forEach(builder::add);
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();
            //创建一个请求
            Request request = commonHeaders()
                    .url(requestUrl)
                    .post(formBody)
                    .build();
            //创建一个Call
            final okhttp3.Call call = okHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            int code = response.code();
            String body = response.body().string();
            Pair<Integer, String> pair = new Pair<>(code, body);
            return pair;
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return new Pair<>(0, "");
    }

    public boolean isSuccessful(int code) {
        return code >= 200 && code < 300;
    }

    protected abstract String getUrl();

    private String getRequestUrl(Map<String, String> params) {
        StringBuilder paramBuilder = new StringBuilder();
        if (params == null) {
            return getUrl();
        }
        Set<String> keys = params.keySet();
        for (String key : keys) {
            try {
                if (paramBuilder.length() > 0) {
                    paramBuilder.append("&");
                }
                String format = String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8"));
                paramBuilder.append(format);
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
//        params.forEach((key, value) -> {
//
//        });
        String requestUrl = String.format("%s?%s", getUrl(), paramBuilder.toString());
        return requestUrl;
    }

    private Request.Builder commonHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("platform", "Android")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", BuildConfig.VERSION_NAME);
        return builder;
    }
}
