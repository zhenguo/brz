package com.brz.http.service;

import com.brz.http.bean.Cmd;
import com.brz.http.bean.Response;
import com.brz.http.bean.Status;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by macro on 16/5/16.
 */
public interface TerminalService {
    @FormUrlEncoded
    @POST("TerminalService/getkey.do")
    Call<Response> getTerminalId(@Field("corpId") String corpId,
                                 @Field("workId") String workId,
                                 @Field("status") Status status,
                                 @Field("cmd") Cmd cmd);

    @FormUrlEncoded
    @POST("TerminalService/heartbeat.do")
    Call<Response> heartBeat(@Field("termId") String termId,
                             @Field("status") Status status,
                             @Field("cmd") Cmd cmd);

    @FormUrlEncoded
    @POST("TerminalService/screenshots.do")
    Call<Void> sendScreenShots(@Field("termId") String termId, @Field("cmd") Cmd cmd);
}
