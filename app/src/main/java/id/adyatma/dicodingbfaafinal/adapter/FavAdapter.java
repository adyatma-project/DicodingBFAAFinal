package id.adyatma.dicodingbfaafinal.adapter;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.adyatma.dicodingbfaafinal.R;
import id.adyatma.dicodingbfaafinal.fragment.FollowersFragment;
import id.adyatma.dicodingbfaafinal.fragment.FollowingFragment;
import id.adyatma.dicodingbfaafinal.model.Favorite;
import id.adyatma.dicodingbfaafinal.view.DetailActivity;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private ArrayList<Favorite> dataList;

    public FavAdapter(ArrayList<Favorite> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final String q1 = dataList.get(position).getLogin();
        holder.nama.setText(q1);
        Glide.with(holder.itemView.getContext())
                .load(dataList.get(position).getAvatar())
                .apply(new RequestOptions().override(460, 460))
                .into(holder.imgUser);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_USER, dataList.get(position).getLogin());
                intent.putExtra(FollowersFragment.EXTRA_FOLLOWER, dataList.get(position).getLogin());
                intent.putExtra(FollowingFragment.EXTRA_FOLLOWING, dataList.get(position).getLogin());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama;
        ImageView imgUser;

        ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.username_list);
            imgUser = itemView.findViewById(R.id.imgUser);
        }
    }

}
