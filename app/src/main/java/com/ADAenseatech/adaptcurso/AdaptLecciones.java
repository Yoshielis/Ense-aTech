package com.ADAenseatech.adaptcurso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.R;
import com.ADAenseatech.modelos.Lecciones;
import java.util.List;

public class AdaptLecciones extends RecyclerView.Adapter<AdaptLecciones.LeccionViewHolder> {

    private List<Lecciones> listaLessons;
    private OnLeccionClickListener listener;

    public interface OnLeccionClickListener {
        void onLeccionClick(Lecciones leccion);
    }

    public AdaptLecciones(List<Lecciones> listaLessons, OnLeccionClickListener listener) {
        this.listaLessons = listaLessons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LeccionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leccion, parent, false);
        return new LeccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeccionViewHolder holder, int position) {
        Lecciones leccion = listaLessons.get(position);
        holder.bind(leccion, listener);
    }

    @Override
    public int getItemCount() {
        return listaLessons.size();
    }

    static class LeccionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTituloLeccion, tvDescripcionLeccion, tvProgresoLeccion;

        public LeccionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloLeccion = itemView.findViewById(R.id.tvTituloLeccion);
            tvDescripcionLeccion = itemView.findViewById(R.id.tvDescripcionLeccion);
            tvProgresoLeccion = itemView.findViewById(R.id.tvProgresoLeccion);
        }

        public void bind(Lecciones leccion, OnLeccionClickListener listener) {
            tvTituloLeccion.setText(leccion.getTitulo());
            tvDescripcionLeccion.setText(leccion.getDescripcion());
            tvProgresoLeccion.setText("Progreso: " + leccion.getProgreso() + "%");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLeccionClick(leccion);
                }
            });
        }
    }
}