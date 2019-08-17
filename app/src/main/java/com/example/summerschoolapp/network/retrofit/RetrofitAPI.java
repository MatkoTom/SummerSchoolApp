package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.editUser.RequestEditUser;
import com.example.summerschoolapp.model.editUser.ResponseEditUser;
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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @POST(Const.Network.API_LOGIN)
    Single<ResponseLogin> login(@Body RequestLogin user);

    @POST(Const.Network.API_SIGNUP)
    Single<ResponseSignup> register(@Body RequestSignup register);

    @POST(Const.Network.API_CREATE_NEW_USER_EDIT_USER)
    Single<ResponseNewUser> createNew(@Header(Const.Network.API_TOKEN) String token,
                                      @Body RequestNewUser requestNew);

    @GET(Const.Network.API_FETCH_USER_LIST)
    Single<ResponseUsersList> fetchUserList(@Header(Const.Network.API_TOKEN) String token);

    @GET(Const.Network.API_SEARCH_USER_QUERY)
    Single<ResponseUsersList> fetchSearchedUsers(@Header(Const.Network.API_TOKEN) String token,
                                                 @Path(Const.Network.API_QUERY) String searchQuery);

    @POST(Const.Network.API_LOGOUT)
    Single<Object> logout(@Header(Const.Network.API_TOKEN) String token);

    @PUT(Const.Network.API_CREATE_NEW_USER_EDIT_USER)
    Single<ResponseEditUser> editUser(@Header(Const.Network.API_TOKEN) String token,
                                      @Body RequestEditUser requestEditUser);
}
