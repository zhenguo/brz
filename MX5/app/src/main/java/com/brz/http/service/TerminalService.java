package com.brz.http.service;

import com.brz.http.bean.Cmd;
import com.brz.http.bean.Response;
import com.brz.http.bean.Status;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
        @FormUrlEncoded
        @POST("TerminalService/getkey.do")
        Call<Response> getTerminalId(@Field("termId") String termId,
                                     @Field("status") Status status,
                                     @Field("cmd") Cmd cmd);

        @POST("TerminalService/transmission.do")
        Call<Response> postTransmission(@Field("termId") String termId, @Field("seq") String seq, @Field("status") Status status, @Field("cmd") Cmd cmd);

        @FormUrlEncoded
        @POST("TerminalService/heartbeat.do")
        Call<Response> heartBeat(@Field("termId") String termId, @Field("status") Status status,
                                 @Field("cmd") Cmd cmd);

        @FormUrlEncoded
        @POST("TerminalService/screenshots.do")
        Call<Void> sendScreenShots(@Field("termId") String termId, @Field("cmd") Cmd cmd);
    }

    public void heartBeat(String termId, Status status, Cmd cmd, Callback<Response> callback) {
        Terminal terminal = mRetrofit.create(Terminal.class);
        Call<Response> call = terminal.heartBeat(termId, status, cmd);
        call.enqueue(callback);
    }
}
