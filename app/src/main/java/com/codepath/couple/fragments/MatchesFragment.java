package com.codepath.couple.fragments;

import android.util.Log;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codepath.couple.Like;
import com.codepath.couple.LoginActivity;
import com.codepath.couple.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MatchesFragment extends PostsFragment {
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void queryPost() throws ParseException {
        HashMap<String, Integer> ids = new HashMap();

        ParseQuery<Like> everyoneIlike = ParseQuery.getQuery(Like.class);
        Log.i(TAG, "User:" + ParseUser.getCurrentUser().getObjectId());
        everyoneIlike.whereEqualTo(Like.KEY_STRING_LIKER, ParseUser.getCurrentUser().getObjectId());
        List<Like> likes = everyoneIlike.find();
        for (Like like : likes) {
            String poster = like.getPosterString();
            Log.i(TAG, "LIKER:" + like.getLikerString()+ " ,  POSTER: " + like.getPosterString());
            ids.put(poster, 0);
        }
        Log.i(TAG, "Contents of ids map:" + ids.toString());


        ParseQuery<Like> everyoneWholikedMe = ParseQuery.getQuery(Like.class);
        Log.i(TAG, "Poster:" + ParseUser.getCurrentUser().getObjectId());
        everyoneWholikedMe.whereEqualTo(Like.KEY_STRING_POSTER, ParseUser.getCurrentUser().getObjectId());
        likes = everyoneWholikedMe.find();
        for (Like like : likes) {
            String liker = like.getLikerString();
            Log.i(TAG, "LIKER:" + like.getLikerString()+ " ,  POSTER: " + like.getPosterString());
            if (ids.containsKey(liker)){
                ids.put(liker, 1);
            }
        }
        Log.i(TAG, "Contents of everyone I like map:" + ids.toString());

        ParseQuery<Post> query=ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        List<Post> posts = query.find();
//        swipeContainer.setRefreshing(false);
        for(Post post:posts){
            String id = post.getUser().getObjectId();
            if(ids.containsKey(id) && ids.get(id).equals(1)) {
                Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser().getUsername() + "user id: " + post.getUser().getObjectId());
                allPosts.add(post);
            }
        }

        adapter.notifyDataSetChanged();

        if(adapter.getItemCount() == 0){
            Toast.makeText(getContext(),"No matches yet",Toast.LENGTH_SHORT).show();
        }
    }
}
