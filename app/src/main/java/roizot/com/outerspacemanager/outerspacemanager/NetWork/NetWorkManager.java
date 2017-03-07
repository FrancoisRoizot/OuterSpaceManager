package roizot.com.outerspacemanager.outerspacemanager.NetWork;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import roizot.com.outerspacemanager.outerspacemanager.Models.UserInfos;

/**
 * Created by roizotf on 06/03/2017.
 */

public interface NetWorkManager {

    static String BASE_URI = "https://outer-space-manager.herokuapp.com/api/v1/";

    @POST("auth/create")
    Call<UserConnection> createAccount(@Body Map<String, String> options);

    @POST("auth/login")
    Call<UserConnection> connectAccount(@Body Map<String, String> options);

    @GET("users/get")
    Call<UserInfos> getUserInfos(@Header("x-access-token") String token);

    @GET("buildings/list")
    Call<BuildingResponse> getBuildingsInfos(@Header("x-access-token") String token);
}
