package com.example.summerschoolapp.utils;

public final class Const {

    private Const() {
    }

    public static class Network {
        public static final String BASE_URL = "https://intern2019dev.clover.studio/";

        public static final String API_LOGIN = "users/login";
        public static final String API_REGISTER = "users/register";
        public static final String API_CREATE_NEW_USER = "users/newUser";
    }

    public static class Preferences {
        public static final String BOOLEAN_SHARED_KEY = "BOOLEAN_SHARED_KEY";
        public static final String SLEEP_DONE = "SLEEP_DONE";
        public static final String STRING_SHARED_KEY = "STRING_SHARED_KEY";
        public static final String STRING_USER_EMAIL = "STRING_USER_EMAIL";
        public static final String STRING_USER_PASSWORD = "STRING_USER_PASSWORD";
        public static final String USER_SHARED_KEY = "USER_SHARED_KEY";
        public static final String BOOLEAN_REMEMBERME_SHARED_KEY = "BOOLEAN_REMEMBERME_SHARED_KEY";
        public static final String ERROR_REGISTER_SHARED_KEY = "ERROR_REGISTER_SHARED_KEY";
        public static final String BOOLEAN_USERCANREGISTER_KEY = "BOOLEAN_USERCANREGISTER_KEY";

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
}
