package com.example.architectureexample.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureexample.model.Nota;
import com.example.architectureexample.R;

import java.util.ArrayList;
import java.util.List;

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaHolder> {
    private List<Nota> notas = new ArrayList<>();
    //Ver que esta distinto
    private OnItemClickListener listener;;

    @NonNull
    @Override
    public NotaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nota_item, parent, false);
        return new NotaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaHolder holder, int position) {
        Nota currentNota = notas.get(position);
        holder.textViewTitulo.setText(currentNota.getTitulo());
        holder.textViewDescripcion.setText(currentNota.getDescripcion());
        holder.textViewPrioridad.setText(String.valueOf(currentNota.getPrioridad()));
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void setNotas(List<Nota> notas){
        this.notas = notas;
        notifyDataSetChanged();
    }

    public Nota getNotaAt(int position){
        return notas.get(position);
    }
    class NotaHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitulo;
        private TextView textViewDescripcion;
        private TextView textViewPrioridad;

        //Cuando implemente no me parecia necesario - revisar
        public NotaHolder(View itemView){
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.text_view_titulo);
            textViewDescripcion = itemView.findViewById(R.id.text_view_descripcion);
            textViewPrioridad = itemView.findViewById(R.id.text_view_prioridad);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notas.get(position));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(Nota nota);
    }

    //Idem arriba
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
