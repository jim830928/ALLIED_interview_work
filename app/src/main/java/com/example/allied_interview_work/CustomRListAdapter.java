package com.example.allied_interview_work;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRListAdapter extends RecyclerView.Adapter<CustomRListAdapter.ViewHolder> {
    public TemperatureData[] temperatureDataset;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_element_test, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemText = (TextView) view.findViewById(R.id.itemText);
        holder.randomPic = (ImageView) view.findViewById(R.id.randomPic);
        return holder;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemText;
        public ImageView randomPic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public CustomRListAdapter(String[][] dat){
        temperatureDataset = new TemperatureData[dat.length*2-1];
        for(int i=0; i<dat.length; i++) { // ITEM A
            // Set clickable item
            temperatureDataset[i*2] = new TemperatureData(true, dat[i][0], dat[i][1], dat[i][2], dat[i][3]);
        }
        for(int i=0; i<dat.length-1; i++) { // ITEM B
            // Set non-clickable random picture
            temperatureDataset[i*2+1] = new TemperatureData(false, "", "", "", "");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Replace the contents of a view (invoked by the layout manager)
        if(temperatureDataset[position].Clickable){ // ITEM A
            // Set clickable item view (Image disable)
            holder.itemText.setText(temperatureDataset[position].printContent());
            holder.randomPic.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                // Response when clicked the clickable item
                @Override
                public void onClick(View view) { // ITEM B
                    Intent intent = new Intent(view.getContext(), ItemPage.class);
                    intent.putExtra("ItemContent", temperatureDataset[position].printContent());
                    view.getContext().startActivity(intent);
                }
            });
        }else{
            // Set non-clickable random picture view (text disable)
            holder.itemText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return temperatureDataset.length;
    }


}
