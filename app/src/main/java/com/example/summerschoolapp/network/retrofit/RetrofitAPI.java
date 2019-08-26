package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.editNews.ResponseEditNews;
import com.example.summerschoolapp.model.editRequest.ResponseEditRequest;
import com.example.summerschoolapp.model.editUser.ResponseEditUser;
import com.example.summerschoolapp.model.login.RequestLogin;
import com.example.summerschoolapp.model.login.ResponseLogin;
import com.example.summerschoolapp.model.newNews.ResponseNewNews;
import com.example.summerschoolapp.model.newRequest.ResponseNewRequest;
import com.example.summerschoolapp.model.newsList.ResponseNewsList;
import com.example.summerschoolapp.model.newuser.ResponseNewUser;
import com.example.summerschoolapp.model.requestsList.ResponseRequestList;
import com.example.summerschoolapp.model.signup.RequestSignup;
import com.example.summerschoolapp.model.signup.ResponseSignup;
import com.example.summerschoolapp.model.userslist.ResponseUsersList;
import com.example.summerschoolapp.utils.Const;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @POST(Const.Api.API_LOGIN)
    Single<ResponseLogin> login(@Body RequestLogin user);

    @POST(Const.Api.API_SIGNUP)
    Single<ResponseSignup> register(@Body RequestSignup register);

    @POST(Const.Api.API_CREATE_NEW_USER_EDIT_USER)
    Single<ResponseNewUser> createNewUser(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                          @Body RequestBody body);

    @PUT(Const.Api.API_CREATE_NEW_USER_EDIT_USER)
    Single<ResponseEditUser> editUser(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                      @Body RequestBody body);

    @GET(Const.Api.API_FETCH_USER_LIST)
    Single<ResponseUsersList> fetchUserList(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @GET(Const.Api.API_SEARCH_USER_QUERY)
    Single<ResponseUsersList> fetchSearchedUsers(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                                 @Path(Const.NetworkQuery.API_QUERY) String searchQuery);

    @POST(Const.Api.API_LOGOUT)
    Single<Object> logout(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @POST(Const.Api.API_CREATE_NEW_REQUEST)
    Single<ResponseNewRequest> createNewRequest(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                                @Body RequestBody body);

    @PUT(Const.Api.API_EDIT_REQUEST)
    Single<ResponseEditRequest> editRequest(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                            @Path(Const.NetworkQuery.API_ID) String id,
                                            @Body RequestBody body);

    @GET(Const.Api.API_FETCH_USER_REQUEST_LIST)
    Single<ResponseRequestList> fetchRequestList(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @GET(Const.Api.API_FETCH_ADMIN_REQUEST_LIST)
    Single<ResponseRequestList> fetchRequestListAdmin(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @GET(Const.Api.API_FILTER_REQUEST)
    Single<ResponseRequestList> fetchFilteredRequest(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                                     @Path(Const.NetworkQuery.API_REQUEST_TYPE) String type);

 @GET(Const.Api.API_FILTER_REQUEST_ADMIN)
    Single<ResponseRequestList> fetchFilteredRequestAdmin(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                                     @Path(Const.NetworkQuery.API_REQUEST_TYPE) String type);

    @POST(Const.Api.API_NEW_NEWS)
    Single<ResponseNewNews> createNewNews(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                          @Body RequestBody body);

    @GET(Const.Api.API_ALL_NEWS)
    Single<ResponseNewsList> fetchNewsList(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @PUT(Const.Api.API_EDIT_NEWS)
    Single<ResponseEditNews> editNews(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                      @Body RequestBody body,
                                      @Path(Const.NetworkQuery.API_ID) int id);
}
