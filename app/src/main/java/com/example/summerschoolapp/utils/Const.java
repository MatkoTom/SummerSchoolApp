package com.example.summerschoolapp.utils;

public final class Const {

    private Const() {
    }

    public static class Network {
        public static final String BASE_URL = "https://intern2019dev.clover.studio/";

        public static final String API_LOGIN = "users/login";
        public static final String API_REGISTER = "users/register";
        public static final String API_CREATE_NEW_USER = "users/newUser";
        public static final String API_FETCH_USER_LIST = "users/allUsers/1233";
    }

    public static class Preferences {
        public static final String BOOLEAN_SHARED_KEY = "BOOLEAN_SHARED_KEY";
        public static final String SLEEP_DONE = "SLEEP_DONE";
        public static final String STRING_SHARED_KEY = "STRING_SHARED_KEY";
        public static final String STRING_USER_EMAIL = "STRING_USER_EMAIL";
        public static final String STRING_USER_PASSWORD = "STRING_USER_PASSWORD";
        public static final String USER_SHARED_KEY = "USER_SHARED_KEY";
        public static final String BOOLEAN_REMEMBERME_SHARED_KEY = "BOOLEAN_REMEMBERME_SHARED_KEY";

    }

    public static class Fragments {
        public static final String FRAGMENT_TAG_FIRST_LOGIN = "FRAGMENT_TAG_FIRST_LOGIN";
        public static final String FRAGMENT_TAG_LOGIN = "FRAGMENT_TAG_LOGIN";
        public static final String FRAGMENT_TAG_REGISTER = "FRAGMENT_TAG_REGISTER";
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
}
