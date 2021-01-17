package simra.androidtest.asadpour.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public interface WebService {


    class Creator {
        public static String ENDPOINT = "http://www.omdbapi.com/";
        static Long CONNECT_TIMEOUT = 20L;
        static Long READ_TIMEOUT = 20L;

        private final static String QUERY_API_KEY = "apiKey";
        private final static String API_KEY = "9af01761";

        public static WebService newWebService() {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            else loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder()
                                .addQueryParameter(QUERY_API_KEY, API_KEY)
                                .build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    })
                    .build();

            Gson gson = new GsonBuilder().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(WebService.class);
        }
    }

}
