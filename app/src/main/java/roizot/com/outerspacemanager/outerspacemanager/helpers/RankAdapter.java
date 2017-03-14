package roizot.com.outerspacemanager.outerspacemanager.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.models.Research;
import roizot.com.outerspacemanager.outerspacemanager.models.UserInfos;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.PostResponse;

/**
 * Created by mac4 on 14/03/2017.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ResearchViewHolder> {

    private final ArrayList<UserInfos> users;
    private final Context context;

    public RankAdapter(ArrayList<UserInfos> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public RankAdapter.ResearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_research, parent, false);
        RankAdapter.ResearchViewHolder viewHolder = new RankAdapter.ResearchViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RankAdapter.ResearchViewHolder holder, int position) {
        UserInfos user = users.get(position);

        Log.i("Name :", user.getUsername());

        holder.userName.setText(user.getUsername() + "");
        holder.userPoints.setText(String.valueOf( (long) user.getPoints()));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ResearchViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView userPoints;

        public ResearchViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.rankUsername);
            userPoints = (TextView) itemView.findViewById(R.id.rankPoints);
        }
    }
}
