package com.ADAenseatech.adaptcurso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ADAenseatech.R;
import com.ADAenseatech.modelos.Curso;
import java.util.List;

public class AdaptCurso extends RecyclerView.Adapter<AdaptCurso.CursoViewHolder> {

    private List<Curso> listaCursos;
    private OnCursoClickListener listener;

    public interface OnCursoClickListener {
        void onCursoClick(Curso curso);
    }

    public AdaptCurso(List<Curso> listaCursos, OnCursoClickListener listener) {
        this.listaCursos = listaCursos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_curso, parent, false);
        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Curso curso = listaCursos.get(position);
        holder.bind(curso, listener);
    }

    @Override
    public int getItemCount() {
        return listaCursos != null ? listaCursos.size() : 0;
    }

    public void actualizarCursos(List<Curso> nuevosCursos) {
        this.listaCursos = nuevosCursos;
        notifyDataSetChanged();
    }

    static class CursoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTituloCurso;
        private TextView tvDescripcionCurso;
        private TextView tvProgreso;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloCurso = itemView.findViewById(R.id.tvTituloCurso);
            tvDescripcionCurso = itemView.findViewById(R.id.tvDescripcionCurso);
            tvProgreso = itemView.findViewById(R.id.tvProgreso);
        }

        public void bind(Curso curso, OnCursoClickListener listener) {
            if (curso != null) {
                tvTituloCurso.setText(curso.getTitulo() != null ? curso.getTitulo() : "");
                tvDescripcionCurso.setText(curso.getDescripcion() != null ? curso.getDescripcion() : "");
                tvProgreso.setText("Progreso: " + curso.getProgreso() + "%");

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onCursoClick(curso);
                        }
                    }
                });
            }
        }
    }
}