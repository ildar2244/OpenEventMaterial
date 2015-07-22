package ru.javaapp.openeventmaterial.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.javaapp.openeventmaterial.R;
import ru.javaapp.openeventmaterial.dao.Events;

/**
 * Created by User on 17.07.2015.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EventViewHolder> {

    List<Events> allEvents;
    private Context context;
    private LayoutInflater inflater;

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        //allEvents = new ArrayList<Events>();

        View v = inflater.inflate(R.layout.item_rv, parent, false);
        EventViewHolder evh = new EventViewHolder(v);
        return evh;
    }

    public RVAdapter(Context context, List<Events> allEvents) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.allEvents = allEvents;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {

        eventViewHolder.txt_name.setText(allEvents.get(i).getName());
        eventViewHolder.imageEvent.setImageBitmap(allEvents.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return allEvents.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;
        ImageView imageEvent;

        public EventViewHolder(View itemView) {
            super(itemView);

            txt_name = (TextView) itemView.findViewById(R.id.tv_title);
            imageEvent = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }
}
