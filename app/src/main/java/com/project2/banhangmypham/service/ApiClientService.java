package com.project2.banhangmypham.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClientService {
    public static final String TAG = "ApiClientService";
    private final OkHttpClient client;
    private Gson gson;

    public ApiClientService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }

    public <T> Single<T> makeRequest(String url, ApiMethod method, Class<T> responseClass, RequestBody requestBody) {
        return Single.create(emitter -> {
            Request.Builder request = new Request.Builder()
                    .url(url);
            if (Objects.requireNonNull(method) == ApiMethod.POST) {
                request.post(requestBody);
            } else {
                request.get();
            }
            Call call = client.newCall(request.build());
            // Enqueue the call for asynchronous execution
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: e = "+e.getMessage());
                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                    }
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            T result = gson.fromJson(response.body().string(), responseClass);
                            Log.d(TAG, "onResponse: ====> result "+result);

                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(result);
                            }
                        } catch (JsonSyntaxException e) {
                            Log.d(TAG, "onResponse: ====> error "+e.getMessage());
                            if (!emitter.isDisposed()) {
                                emitter.onError(new IOException("Failed to parse response", e));
                            }
                        }
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new IOException("Request failed with code: " + response.code()));
                        }
                    }
                }
            });

        });
    }
}
