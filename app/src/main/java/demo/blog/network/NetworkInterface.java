package demo.blog.network;



import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface NetworkInterface {

    @Headers("Content-Type: application/json")
    @GET("2018/01/22/life-as-an-android-engineer/")
    Observable<String> getTrueCallerLife();
}
