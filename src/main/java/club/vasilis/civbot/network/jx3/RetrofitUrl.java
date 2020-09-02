package club.vasilis.civbot.network.jx3;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitUrl {
    @FormUrlEncoded
    @POST("https://ws.xoyo.com/_daily_flower/flower/get_data")
    Call<ResponseBody> getFlower(@Field("serv")String serverName, @Field("f_id")Integer code);


}