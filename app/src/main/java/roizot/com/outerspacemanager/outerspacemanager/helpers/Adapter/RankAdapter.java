package roizot.com.outerspacemanager.outerspacemanager.helpers.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.models.UserInfos;

/**
 * Created by mac4 on 14/03/2017.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private final ArrayList<UserInfos> users;
    private final Context context;

    public RankAdapter(ArrayList<UserInfos> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public RankAdapter.RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_rank, parent, false);
        RankAdapter.RankViewHolder viewHolder = new RankAdapter.RankViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RankAdapter.RankViewHolder holder, int position) {
        UserInfos user = users.get(position);

        long points = (long) user.getPoints();

        holder.userName.setText(user.getUsername());
        if ( points == 0) {
           holder.userName.setTextColor(Color.YELLOW);
        } else {
            holder.userName.setTextColor(Color.LTGRAY);
        }
        holder.userPoints.setText(NumberFormat.getNumberInstance(Locale.FRANCE).format(points));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class RankViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView userPoints;

        public RankViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.rankUsername);
            userPoints = (TextView) itemView.findViewById(R.id.rankPoints);
        }
    }
}
