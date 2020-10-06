package id.adyatma.consumerapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import id.adyatma.consumerapp.R;
import id.adyatma.consumerapp.model.Favorite;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public FavAdapter(Context context) {
        this.context = context;
    }

    public void setUserCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    public Favorite getUserItems(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Invalid");
        }
        return new Favorite(cursor);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Favorite user = getUserItems(position);

        final String q1 = user.getLogin();
        holder.nama.setText(q1);


        Glide.with(holder.itemView.getContext())
                .load(user.getAvatar_url())
                .apply(new RequestOptions().override(460, 460))
                .into(holder.imgUser);
    }


    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama;
        ImageView imgUser;

        ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.username_list);
            imgUser = itemView.findViewById(R.id.imgUser);
        }
    }

}

