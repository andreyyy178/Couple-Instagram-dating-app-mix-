package com.codepath.couple.fragments;

import static android.content.Intent.getIntent;
import static com.codepath.couple.R.id.flContainer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;

import com.codepath.couple.FilterActivity;
import com.codepath.couple.LoginActivity;
import com.codepath.couple.Post;
import com.codepath.couple.PostsAdapter;
import com.codepath.couple.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    protected List<Post> filterpost;
    private SwipeRefreshLayout swipeContainer;
    private Button btnFilter;
    public String createdAt;
    ActivityResultLauncher<Intent> getFilter;
    public String x = "everyone";
    public Integer minAge = 18;
    public Integer maxAge = 100;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFilter = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (Activity.RESULT_OK == result.getResultCode()) {
                            Intent data = result.getData();
                            x = data.getStringExtra("gender");
                            minAge = data.getIntExtra("minAge", 18);
                            maxAge = data.getIntExtra("maxAge", 100);

                            //queryPost();
                            queryPost2();
                        }
                    }
                }
        );
        String value = getActivity().getIntent().getStringExtra("value");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_posts, container, false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);


        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);


        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                try {
                    queryPost();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);


        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            queryPost();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnFilter = view.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                getFilter.launch(new Intent(getActivity(), FilterActivity.class));

            }
        });

    }


    protected void queryPost() throws ParseException {

        ParseUser user = ParseUser.getCurrentUser();

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);

        //gender filter
        if (!(x.equals("everyone")))
            query.whereEqualTo("gender", x);

        query.addDescendingOrder(Post.KEY_CREATED_KEY);

        //age filter. doesn't load any posts

        // query.whereLessThanOrEqualTo("age",maxAge);
        //query.whereGreaterThanOrEqualTo("age",minAge);


        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                swipeContainer.setRefreshing(false);
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser().getUsername());

                }

                for (Post post : posts) {

                }
                allPosts.clear();
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();

            }
        });
    }

    protected void queryPost2() {

        ParseUser user = ParseUser.getCurrentUser();

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);

        //gender filter
        if (!(x.equals("everyone")))
            query.whereEqualTo("gender", x);

        query.addDescendingOrder(Post.KEY_CREATED_KEY);

        //age filter. doesn't load any posts

        //query.whereLessThanOrEqualTo("age",maxAge);
        //query.whereGreaterThanOrEqualTo("age",minAge);


        query.findInBackground(new FindCallback<Post>() {

            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                swipeContainer.setRefreshing(false);
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser().getUsername());

                }

               /* for (Post post : posts) {
                    if (post.getAge() >= minAge && post.getAge() <= maxAge)
                        //allPosts.addAll();
                }*/

                adapter.notifyDataSetChanged();

            }
        });
    }

}