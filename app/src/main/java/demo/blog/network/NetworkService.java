package demo.blog.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by naineshzaveri on 12/08/18.
 */

public interface NetworkService {
    @Headers("Content-Type: application/json")
    @GET("2018/01/22/life-as-an-android-engineer/")
    Call<String> getTrueCallerLife();
}
