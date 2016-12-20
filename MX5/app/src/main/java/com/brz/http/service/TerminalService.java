package com.brz.http.service;

import com.brz.http.bean.Cmd;
import com.brz.http.bean.RequestBody;
import com.brz.http.bean.Response;
import com.brz.http.bean.Status;
import java.util.logging.Logger;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    }) @POST("TerminalService/getkey.do") Observable<Response> getTerminalId(
        @Body RequestBody requestBody);

    @POST("TerminalService/transmission.do") Call<Response> postTransmission(
        @Field("termId") String termId, @Field("seq") String seq, @Field("status") Status status,
        @Field("cmd") Cmd cmd);

    @Headers({
        "content-type: application/json"
    }) @POST("TerminalService/heartbeat.do") Observable<Response> heartBeat(@Body RequestBody body);

    @FormUrlEncoded @POST("TerminalService/screenshots.do") Call<Void> sendScreenShots(
        @Field("termId") String termId, @Field("cmd") Cmd cmd);

    @Streaming @GET Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);
  }

  public void getTermId(RequestBody requestBody, Subscriber<String> subscriber) {
    Terminal terminal = mRetrofit.create(Terminal.class);
    terminal.getTerminalId(requestBody).map(new Func1<Response, String>() {
      @Override public String call(Response response) {
        return response.getCmd().getCmdData().getTermId();
      }
    }).subscribeOn(Schedulers.io()).observeOn(Schedulers.immediate()).subscribe(subscriber);
  }

  public void heartBeat(RequestBody requestBody, Subscriber<Cmd> subscriber) {
    Terminal terminal = mRetrofit.create(Terminal.class);
    terminal.heartBeat(requestBody).map(new Func1<Response, Cmd>() {
      @Override public Cmd call(Response response) {
        return response.getCmd();
      }
    }).observeOn(Schedulers.io()).observeOn(Schedulers.immediate()).subscribe(subscriber);
  }
}
