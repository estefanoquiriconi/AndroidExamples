package com.estefanoquiriconi.proyecto021;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaisAdapter extends RecyclerView.Adapter<PaisAdapter.PaisAdapterHolder> {

    ArrayList<Pais> paises;
    MainActivity context;

    public PaisAdapter(MainActivity context, ArrayList<Pais> paises) {
        this.context = context;
        this.paises = paises;
    }

    @NonNull
    @Override
    public PaisAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaisAdapterHolder(context.getLayoutInflater().inflate(R.layout.layout_pais, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaisAdapterHolder holder, int position) {
        holder.imageView.setImageResource(paises.get(position).getImagen());
        holder.textView.setText(paises.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        if(paises == null){
            return 0;
        }
        return paises.size();
    }

    static class PaisAdapterHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public PaisAdapterHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.tvPais);
        }
    }
}
