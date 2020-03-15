package com.example.kobenhavn.ui.aktiviteter.tilmeldte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kobenhavn.R;
import com.example.kobenhavn.ui.aktiviteter.AktivitetModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
public class TilmeldteAdapter extends RecyclerView.Adapter<TilmeldteAdapter.ViewHolder> {

    //Member variables
    private List<AktivitetModel> aktivitetsData;
    private Context context;
    private OnItemClickListener listener;

    /**
     * Constructor that passes in the aktivitetes data and the context
     *
     * @param aktivitetsData ArrayList containing the sports data
     * @param context        Context of the application
     */
    public TilmeldteAdapter(Context context, ArrayList<AktivitetModel> aktivitetsData) {
        this.aktivitetsData = aktivitetsData;
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
    public TilmeldteAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.aktiviteter_tilmeldte_item, parent, false));
    }


    public void deleteItem(int position){
        aktivitetsData.remove(position);
        notifyDataSetChanged();
        Toast.makeText(context, "Fjernet fra tilmeldte aktiviteter.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(TilmeldteAdapter.ViewHolder holder, int position) {
        AktivitetModel currentAktivitet = aktivitetsData.get(position);
        //Populate the textviews with data
        holder.bindTo(currentAktivitet);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return aktivitetsData.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        //Member Variables for the TextViews
        private TextView dato_tekst;
        private TextView subtitle_tekst;
        private TextView title_tekst;
        private TextView tid_tekst;
        private TextView interesseret_tekst;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the aktiviteter_tilmeldte_item.xml_item.xml layout file
         */
        public ViewHolder(View itemView) {
            super(itemView);

            //Initialize the views
            dato_tekst = itemView.findViewById(R.id.tilmeldte_dato_tekst);
            subtitle_tekst = itemView.findViewById(R.id.tilmeldte_subtitle_tekst);
            title_tekst = itemView.findViewById(R.id.tilmeldte_title_tekst);
            tid_tekst = itemView.findViewById(R.id.tilmeldte_tid_tekst);
            interesseret_tekst = itemView.findViewById(R.id.tilmeldte_interesseret_tekst);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null & position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(aktivitetsData.get(position));
                    }
                }
            });
        }

        void bindTo(AktivitetModel aktivitet) {
            //Populate the textviews with data
            dato_tekst.setText(aktivitet.getDato());
            subtitle_tekst.setText(aktivitet.getSubtitle());
            title_tekst.setText(aktivitet.getTitle());
            tid_tekst.setText(aktivitet.getTid());
            interesseret_tekst.setText(aktivitet.getInteresseret());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AktivitetModel aktivitetModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
