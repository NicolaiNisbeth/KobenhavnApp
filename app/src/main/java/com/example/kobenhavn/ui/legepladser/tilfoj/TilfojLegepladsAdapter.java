package com.example.kobenhavn.ui.legepladser.tilfoj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.aktiviteter.AktivitetModel;
import com.example.kobenhavn.ui.legepladser.LegepladsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
public class TilfojLegepladsAdapter extends RecyclerView.Adapter<TilfojLegepladsAdapter.ViewHolder> {

    //Member variables
    private List<LegepladsModel> legepladsData;
    private Context context;
    private OnItemClickListener listener;

    /**
     * Constructor that passes in the aktivitetes data and the context
     *
     * @param legepladsData ArrayList containing the sports data
     * @param context        Context of the application
     */
    public TilfojLegepladsAdapter(Context context, ArrayList<LegepladsModel> legepladsData) {
        this.legepladsData = legepladsData;
        this.context = context;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create ViewHolder.
     */
    @NotNull
    @Override
    public TilfojLegepladsAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.legepladser_tilfoj_item, parent, false));
    }


    public void addItem(int position, LegepladsModel legepladsmodel){
        legepladsData.remove(position);
        notifyDataSetChanged();
        Toast.makeText(context, "Legeplads er tilføjet", Toast.LENGTH_SHORT).show();
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(TilfojLegepladsAdapter.ViewHolder holder, int position) {
        LegepladsModel nuvaerendeLegeplads = legepladsData.get(position);
        //Populate the textviews with data
        holder.bindTo(nuvaerendeLegeplads);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return legepladsData.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        //Member Variables for the TextViews
        private TextView title_tekst;
        private TextView adresse_tekst;
        private ImageButton imageButton;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the aktiviteter_kommende_item.xml_item.xml layout file
         */
        public ViewHolder(View itemView) {
            super(itemView);

            //Initialize the views
            title_tekst = itemView.findViewById(R.id.legepladser_tilfoj_item_title);
            adresse_tekst = itemView.findViewById(R.id.legepladser_tilfoj_item_adresse);
            imageButton = itemView.findViewById(R.id.legepladser_tilfoj_item_addbtn);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        addItem(position, legepladsData.get(position));
                    }
                }
            });
        }

        void bindTo(LegepladsModel legepladsMode) {
            //Populate the textviews with data
            title_tekst.setText(legepladsMode.getTitel());
            adresse_tekst.setText(legepladsMode.getAdresse());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AktivitetModel aktivitetModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
