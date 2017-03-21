package roizot.com.outerspacemanager.outerspacemanager.netWork;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import roizot.com.outerspacemanager.outerspacemanager.models.Ship;
import roizot.com.outerspacemanager.outerspacemanager.models.UserInfos;

/**
 * Created by roizotf on 06/03/2017.
 */

public interface NetWorkManager {

    String BASE_URI = "https://outer-space-manager.herokuapp.com/api/v1/";

    @POST("auth/create")
    Call<UserConnection> createAccount(@Body Map<String, String> options);

    @POST("auth/login")
    Call<UserConnection> connectAccount(@Body Map<String, String> options);

    @GET("users/get")
    Call<UserInfos> getUserInfos(@Header("x-access-token") String token);

    @GET("buildings/list")
    Call<BuildingResponse> getBuildingsInfos(@Header("x-access-token") String token);

    @POST("buildings/create/{buildingId}")
    Call<PostResponse> buildBuilding(@Header("x-access-token") String token, @Path("buildingId") int id);

    @GET("searches/list")
    Call<ResearchResponse> getResearchesInfos(@Header("x-access-token") String token);

    @POST("searches/create/{searchId}")
    Call<PostResponse> makeResearch(@Header("x-access-token") String token, @Path("searchId") int id);

    @GET("users/{from}/{limit}")
    Call<UsersResponse> getUsersRank(@Header("x-access-token") String token, @Path("from") int from, @Path("limit") int limit);

    @POST("fleet/attack/{userName}")
    Call<PostResponse> attackUser(@Header("x-access-token") String token, @Path("userName") String userName, @Body Map<String, Object> ships);

    @GET("fleet/list")
    Call<ShipsResponse> getFleet(@Header("x-access-token") String token);

    @GET("ships/create/{shipId}")
    Call<PostResponse> buildShips(@Header("x-access-token") String token, @Path("shipId") int shipId, int amount);

    @GET("ships")
    Call<ShipsResponse> getShips(@Header("x-access-token") String token);

    @GET("ships/{shipId}")
    Call<Ship> getShip(@Header("x-access-token") String token, @Path("shipId") int shipId);

    // TODO : Créer l'objet Reports
    @GET("reports/{from}/{limit}")
    Call<Object> getReports(@Header("x-access-token") String token, @Path("from") int from, @Path("limit") int limit);
}
