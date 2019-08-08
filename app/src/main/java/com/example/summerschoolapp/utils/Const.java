package com.example.summerschoolapp.utils;

public final class Const {

    private Const() {
    }

    public static class Network {
        public static final String BASE_URL = "https://172.26.14.119/";

        public static final String API_LOGIN = "users/login";
        public static final String API_REGISTER = "users/register";
    }

    public static class Preferences {
        public static final String BOOLEAN_SHARED_KEY = "BOOLEAN_SHARED_KEY";
        public static final String SLEEP_DONE = "SLEEP_DONE";
        public static final String STRING_SHARED_KEY = "STRING_SHARED_KEY";
        public static final String STRING_USER_EMAIL = "STRING_USER_EMAIL";
        public static final String STRING_USER_PASSWORD = "STRING_USER_PASSWORD";
    }

    public enum ProgressStatus {
        START_PROGRESS,
        STOP_PROGRESS
    }
}
