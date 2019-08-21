package com.example.summerschoolapp.utils;

public final class Const {

    private Const() {
    }


    public static class Api {
        public static final String BASE_URL = "https://intern2019dev.clover.studio/";
        public static final String API_LOGIN = "users/login";
        public static final String API_SIGNUP = "users/register";
        public static final String API_CREATE_NEW_USER_EDIT_USER = "users/newUser";
        public static final String API_FETCH_USER_LIST = "users/allUsers";
        public static final String API_SEARCH_USER_QUERY = "users/allUsers/{query}";
        public static final String API_LOGOUT = "users/logout";
        public static final String API_CREATE_NEW_REQUEST = "requests/new";
        public static final String API_EDIT_NEW_REQUEST = "requests/edit";
        public static final String API_FETCH_REQUEST_LIST = "requests/myRequests";
        public static final String API_FILTER_REQUEST = "requests/myRequests/{type}";
    }

    public static class NetworkQuery {
        public static final String API_TOKEN = "token";
        public static final String API_QUERY = "query";
        public static final String API_REQUEST_TYPE = "type";
    }

    public static class Preferences {
        public static final String BOOLEAN_SHARED_KEY = "BOOLEAN_SHARED_KEY";
        public static final String USER_SHARED_KEY = "USER_SHARED_KEY";
        public static final String BOOLEAN_REMEMBERME_SHARED_KEY = "BOOLEAN_REMEMBERME_SHARED_KEY";
        public static final String EDIT_USER_KEY = "EDIT_USER_KEY";
        public static final int ADMIN_ROLE = 1;
        public static final int USER_ROLE = 2;

    }

    public static class Fragments {
        public static final String FRAGMENT_TAG_FIRST_LOGIN = "FRAGMENT_TAG_FIRST_LOGIN";
        public static final String FRAGMENT_TAG_LOGIN = "FRAGMENT_TAG_LOGIN";
        public static final String FRAGMENT_TAG_SIGNUP = "FRAGMENT_TAG_SIGNUP";
    }

    public enum ProgressStatus {
        START_PROGRESS,
        STOP_PROGRESS
    }

    public static class Errors {
        public static final int EMAIL_IN_USE = 1003;
        public static final int OIB_IN_USE = 1002;
        public static final int WRONG_PASSWORD = 1001;
        public static final int WRONG_EMAIL = 1000;
    }

    public static class Intent {
        public static final String USER_DATA = "UserData";
    }
}
