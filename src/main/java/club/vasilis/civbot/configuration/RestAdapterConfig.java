package club.vasilis.civbot.configuration;

import club.vasilis.civbot.network.jx3.RetrofitUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

@Configuration
public class RestAdapterConfig {

    @Autowired
    private Retrofit retrofit;

    @Bean
    public Retrofit getRestAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8081")
                .build();
        return retrofit;
    }

    @Bean
    public RetrofitUrl getHotelAPI() {
        return retrofit.create(RetrofitUrl.class);
    }

}
