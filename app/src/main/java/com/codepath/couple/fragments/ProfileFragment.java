package com.codepath.couple.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.couple.LoginActivity;
import com.codepath.couple.Post;
import com.codepath.couple.PostsAdapter;
import com.codepath.couple.ProfileURLString;
import com.codepath.couple.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends Fragment {

    public static final String TAG="ProfileFragments";
    private Button btnLogout;
    private Button btnEdit;
    private ImageView ivProfileImage;
    private TextView tvProfileDescription;
    String imageUrl =ProfileURLString.url;


    ParseUser user = ParseUser.getCurrentUser();
    String test = user.get("Gender").toString();




    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    public ProfileFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        queryPost();


        super.onViewCreated(view, savedInstanceState);
        btnLogout= view.findViewById(R.id.btnLogOut);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        tvProfileDescription = view.findViewById(R.id.tvProfileDescription);
        btnEdit = view.findViewById(R.id.button);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i = new Intent(getContext(), ComposeActivity.class);
//                startActivity(i);

                ComposeFragment nextFrag= new  ComposeFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(ProfileFragment.this)
                        .replace(((ViewGroup)getView().getParent()).getId(), nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });


        Log.i(TAG, "my gender " + test);
        Glide.with(this).load(imageUrl).transform(new CircleCrop()).override(300,300).into(ivProfileImage);
    }



    protected void queryPost() {
        ParseQuery<Post> query=ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(1);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e!=null){
                    return;
                }
                for(Post post:posts){
                    tvProfileDescription.setText(post.getDescription());
                    ParseFile image=post.getImage();
                    imageUrl = image.getUrl();
                    Log.i(TAG,"Post: "+post.getDescription()+" , username: "+post.getUser().getUsername() + image.getUrl() + post.getUser().getObjectId());
                }

            }

        });
        queryUserImgUrl();
    }



    private void queryUserImgUrl() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(1);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    return;
                }
                for (Post post : posts) {

                    ParseFile image = post.getImage();
//                    ProfileURLString.url = image.getUrl();
                    Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser().getUsername() + image.getUrl());
                }

            }

        });
    }

//    protected void queryGender() {
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> scoreList, ParseException e) {
//                if (e == null) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//    }


}
