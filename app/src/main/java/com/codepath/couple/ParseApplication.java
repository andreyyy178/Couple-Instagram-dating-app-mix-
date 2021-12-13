package com.codepath.couple;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Like.class);
        ParseObject.registerSubclass(Message.class);


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("82ELcI5Ar2spfZYyYGf5nSeXm6euY6a3Mg1eoxFc")
                .clientKey("3JV82Mmp8HqN2bidfsA70NG085tqdgEA1PlJfjub")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
