package com.codepath.couple;

import static com.codepath.couple.SignupActivity.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.couple.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();

    }

    public void addAll(List<Post> postsList) {
        posts.addAll(postsList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvCaption;
        private ImageView btnLike;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "You Liked this post!", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "You Liked this post!");
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    savePost2(currentUser);

                }
            });
        }


        private void savePost2(ParseUser currentUser) {
            int position = getAdapterPosition();
            Post post = posts.get(position);
            Like like = new Like();
            like.setPoster(post.getUser());
            like.setLiker(currentUser);

            like.setLikerString(currentUser.getObjectId());
            like.setPosterString(post.getUser().getObjectId());

            like.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        //Log.e(TAG, "Error while saving", e);
                        //Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "Post save was successful");
                }
            });

        }

        public void bind(Post post) {


            tvCaption.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
//                Glide.with(context).load(image.getUrl()).transform(new RoundedCorners(100)).into(ivImage);
                Glide.with(context)
                        .load(image.getUrl())
                        .transform(new CenterCrop(),new RoundedCorners(25))
                        .into(ivImage);
            }

        }

    }
}
