package com.kd.appeegn;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HttpAPI {
    @GET("{path}")
   Call<Info> getInfo(@Path("path") String path);
}
