package com.codepath.couple;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Like")

public class Like extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_POSTER = "Poster";
    public static final String KEY_LIKER = "Liker";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static String KEY_STRING_LIKER = "LikerString";
    public static String KEY_STRING_POSTER = "PosterString";


    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getPoster() {
        return getParseUser(KEY_POSTER);
    }

    public void setPoster(ParseUser Poster) {
        put(KEY_POSTER, Poster);
    }

    public ParseUser getLiker() {
        return getParseUser(KEY_LIKER);
    }


    public void setLiker(ParseUser Liker) {
        put(KEY_LIKER, Liker);
    }

    public void setLikerString(String likerString){
        put(KEY_STRING_LIKER, likerString);
    }

    public void setPosterString(String posterString){
        put(KEY_STRING_POSTER, posterString);
    }

    public String getLikerString(){
        return getString(KEY_STRING_LIKER);
    }

    public String getPosterString(){
        return getString(KEY_STRING_POSTER);
    }
}