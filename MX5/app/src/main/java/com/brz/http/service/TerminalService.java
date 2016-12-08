package com.brz.http.service;

import com.brz.http.bean.Cmd;
import com.brz.http.bean.RequestBody;
import com.brz.http.bean.Response;
import com.brz.http.bean.Status;
import java.util.logging.Logger;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by macro on 16/5/16.
 */
public class TerminalService extends HttpService {

  private static final String TAG = "TerminalService";
  private Logger mLogger = Logger.getLogger(TAG);

  /**
   * 只要在该类加载时才会初始化instance变量
   */
  private static class SingletonHolder {
    public static TerminalService instance = new TerminalService();
  }

  public static TerminalService getInstance() {
    return SingletonHolder.instance;
  }

  private TerminalService() {
    super(HttpService.BSQ_URL);
  }

  public interface Terminal {
    @Headers({
        "content-type: application/json"
    }) @POST("TerminalService/getkey.do") Call<Response> getTerminalId(
        @Body RequestBody requestBody);

    @POST("TerminalService/transmission.do") Call<Response> postTransmission(
        @Field("termId") String termId, @Field("seq") String seq, @Field("status") Status status,
        @Field("cmd") Cmd cmd);

    @Headers({
        "content-type: application/json"
    }) @POST("TerminalService/heartbeat.do") Call<Response> heartBeat(@Body RequestBody body);

    @FormUrlEncoded @POST("TerminalService/screenshots.do") Call<Void> sendScreenShots(
        @Field("termId") String termId, @Field("cmd") Cmd cmd);

    @Streaming @GET Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);
  }

  public void getTermId(RequestBody requestBody, Callback<Response> callback) {
    Terminal terminal = mRetrofit.create(Terminal.class);
    Call<Response> call = terminal.getTerminalId(requestBody);
    call.enqueue(callback);
  }

  public void heartBeat(RequestBody requestBody, Callback<Response> callback) {
    Terminal terminal = mRetrofit.create(Terminal.class);
    Call<Response> call = terminal.heartBeat(requestBody);
    call.enqueue(callback);
  }
}
