package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.login.RequestLogin;
import com.example.summerschoolapp.model.login.ResponseLogin;
import com.example.summerschoolapp.model.newuser.RequestNewUser;
import com.example.summerschoolapp.model.newuser.ResponseNewUser;
import com.example.summerschoolapp.model.signup.RequestSignup;
import com.example.summerschoolapp.model.signup.ResponseSignup;
import com.example.summerschoolapp.model.userslist.ResponseUsersList;
import com.example.summerschoolapp.utils.Const;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @POST(Const.Network.API_LOGIN)
    Single<ResponseLogin> login(@Body RequestLogin user);

    @POST(Const.Network.API_SIGNUP)
    Single<ResponseSignup> register(@Body RequestSignup register);

    @POST(Const.Network.API_CREATE_NEW_USER)
    Single<ResponseNewUser> createNew(@Header("token") String token,
                                      @Body RequestNewUser requestNew);

    @GET(Const.Network.API_FETCH_USER_LIST)
    Single<ResponseUsersList> fetchUserList(@Header("token") String token);

    @GET("users/allUsers/{query}")
    Single<ResponseUsersList> fetchSearchedUsers(@Header("token") String token,
                                                @Path("query") String searchQuery);
}
