package com.example.pawfinder.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pawfinder.MainActivity;
import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LostPetsListAdapter extends ArrayAdapter<LostPet> {

    public interface OnLoadMoreItemsListener{
        void onLoadMoreItems();
    }
    OnLoadMoreItemsListener mOnLoadMoreItemsListener;

    private static final String TAG = "LostPetsListAdapter";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private DatabaseReference mReference;

    private Boolean ellipses;


    public LostPetsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LostPet> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
        ellipses = true;
    }

    static class ViewHolder{
        TextView petStatus, timeDelta, petName, petArea;
        SquareImageView image;
        ImageView ellipses;
        LostPet lostPet;
        RelativeLayout layout;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();
            holder.petStatus = convertView.findViewById(R.id.pet_status);
            holder.image = convertView.findViewById(R.id.post_image);
            holder.petName = convertView.findViewById(R.id.pet_name);
            holder.petArea = convertView.findViewById(R.id.pet_area);
            holder.timeDelta = convertView.findViewById(R.id.image_time_posted);
            holder.ellipses = convertView.findViewById(R.id.ivEllipses);
            holder.layout = convertView.findViewById(R.id.relLayoutClick);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lostPet = getItem(position);

        Log.d(TAG, "position: " + position + " and lost pet: " + holder.lostPet);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Loading more pet information.");
                ((MainActivity)mContext).onLostPetSelected(getItem(position), "MAINACTIVITY");
                ((MainActivity)mContext).hideLayout();

            }
        });

        //set the time it was posted
        String timestampDifference = getTimestampDifference(getItem(position));
        if(!timestampDifference.equals("0")){
            if(timestampDifference.equals("1")) {
                holder.timeDelta.setText(timestampDifference + " DAY AGO");
            } else {
                holder.timeDelta.setText(timestampDifference + " DAYS AGO");
            }
        } else{
            holder.timeDelta.setText("TODAY");
        }

        // Hide or show ellipses
        if (!ellipses) {
            holder.ellipses.setVisibility(View.GONE);
        } else {
            holder.ellipses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Show menu for delete pet or edit pet
                }
            });
        }

        // Set the pet status
        holder.petStatus.setText(getItem(position).getStatus().toUpperCase());

        // Set the pet image
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        imageLoader.displayImage(getItem(position).getImage_path(), holder.image);

        // Set the pet name
        holder.petName.setText(getItem(position).getPet_name().toUpperCase());

        // Set the pet area
        holder.petArea.setText(getItem(position).getArea_missing());

        if(reachedEndOfList(position)){
            loadMoreData();
        }
        return convertView;
    }

    private boolean reachedEndOfList(int position){
        return position == getCount() - 1;
    }

    private void loadMoreData(){
        try{
            mOnLoadMoreItemsListener = (OnLoadMoreItemsListener) getContext();
        }catch (ClassCastException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }
        try{
            mOnLoadMoreItemsListener.onLoadMoreItems();
        }catch (NullPointerException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }
    }

    public void setEllipsesInvisible() {
        ellipses = false;
    }

    /**
     * Returns a string representing the number of days ago the post was made
     * @return
     * @param lostPet
     */
    private String getTimestampDifference(LostPet lostPet){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("US/Central"));
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = lostPet.getDate_posted();
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }

}
