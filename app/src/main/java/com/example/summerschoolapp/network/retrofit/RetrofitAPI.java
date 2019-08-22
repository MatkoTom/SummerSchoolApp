package com.example.summerschoolapp.network.retrofit;

import com.example.summerschoolapp.model.editUser.ResponseEditUser;
import com.example.summerschoolapp.model.login.RequestLogin;
import com.example.summerschoolapp.model.login.ResponseLogin;
import com.example.summerschoolapp.model.newRequest.RequestNewRequest;
import com.example.summerschoolapp.model.newRequest.ResponseNewRequest;
import com.example.summerschoolapp.model.newuser.ResponseNewUser;
import com.example.summerschoolapp.model.requestsList.ResponseRequestList;
import com.example.summerschoolapp.model.signup.RequestSignup;
import com.example.summerschoolapp.model.signup.ResponseSignup;
import com.example.summerschoolapp.model.userslist.ResponseUsersList;
import com.example.summerschoolapp.utils.Const;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface RetrofitAPI {

    //TODO if sending picture, change to multipart

    @POST(Const.Api.API_LOGIN)
    Single<ResponseLogin> login(@Body RequestLogin user);

    @POST(Const.Api.API_SIGNUP)
    Single<ResponseSignup> register(@Body RequestSignup register);

    @Multipart
    @Streaming
    @POST(Const.Api.API_CREATE_NEW_USER_EDIT_USER)
    Single<ResponseNewUser> createNewUser(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                          @Part("oib") String oib,
                                          @Part("firstName") String firstName,
                                          @Part("lastName") String lastName,
                                          @Part("email") String email,
                                          @Part("password") String password,
                                          @Part MultipartBody.Part photo);

    @GET(Const.Api.API_FETCH_USER_LIST)
    Single<ResponseUsersList> fetchUserList(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @GET(Const.Api.API_SEARCH_USER_QUERY)
    Single<ResponseUsersList> fetchSearchedUsers(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                                 @Path(Const.NetworkQuery.API_QUERY) String searchQuery);

    @POST(Const.Api.API_LOGOUT)
    Single<Object> logout(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @Multipart
    @Streaming
    @PUT(Const.Api.API_CREATE_NEW_USER_EDIT_USER)
    Single<ResponseEditUser> editUser(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                      @Part("ID") String id,
                                      @Part("oib") String oib,
                                      @Part("firstName") String firstName,
                                      @Part("lastName") String lastName,
                                      @Part("email") String email,
                                      @Part("password") String password,
                                      @Part MultipartBody.Part file);

    @Multipart
    @POST(Const.Api.API_CREATE_NEW_REQUEST)
    Single<ResponseNewRequest> createNewRequest(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                                @Part("Title") String title,
                                                @Part("Request_type") String type,
                                                @Part("message") String message,
                                                @Part("location_longitude") String longitude,
                                                @Part("location_latitude") String latitude,
                                                @Part("Address") String address);


    @PUT(Const.Api.API_EDIT_NEW_REQUEST)
    Single<ResponseNewRequest> editRequest(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                           @Body RequestNewRequest requestNewRequest);

    @GET(Const.Api.API_FETCH_REQUEST_LIST)
    Single<ResponseRequestList> fetchRequestList(@Header(Const.NetworkQuery.API_TOKEN) String token);

    @GET(Const.Api.API_FILTER_REQUEST)
    Single<ResponseRequestList> fetchFilteredRequest(@Header(Const.NetworkQuery.API_TOKEN) String token,
                                                     @Path(Const.NetworkQuery.API_REQUEST_TYPE) String type);
}
