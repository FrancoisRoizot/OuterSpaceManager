package roizot.com.outerspacemanager.outerspacemanager;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by roizotf on 06/03/2017.
 */

public interface NetWorkManager {

    static String BASE_URI = "https://outer-space-manager.herokuapp.com/api/v1/";

    @POST(BASE_URI + "auth/create")
    Call<User> createAccount(@QueryMap Map<String, String> options);

    @POST(BASE_URI + "auth/create")
    Call<User> connectAccount(@QueryMap Map<String, String> options);
}
