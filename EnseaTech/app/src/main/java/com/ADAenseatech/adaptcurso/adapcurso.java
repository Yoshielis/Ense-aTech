package com.ADAenseatech.adaptcurso;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.educapp.R;
import com.ADAenseatech.modelos.Curso;

import java.util.List;

public class Adaptcurso extends RecyclerView.Adapter<Adaptcurso.CourseViewHolder> {
    private List<Course> courseList;
    private OnCourseClickListener listener;

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }

    public Adaptcurso(List<Course> courseList, OnCourseClickListener listener) {
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.bind(course, listener);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCourseTitle, tvCourseDescription, tvProgress;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseTitle = itemView.findViewById(R.id.tvCourseTitle);
            tvCourseDescription = itemView.findViewById(R.id.tvCourseDescription);
            tvProgress = itemView.findViewById(R.id.tvProgress);
        }

        public void bind(Course course, OnCourseClickListener listener) {
            tvCourseTitle.setText(course.getTitle());
            tvCourseDescription.setText(course.getDescription());
            tvProgress.setText("Progreso: " + course.getProgress() + "%");

            itemView.setOnClickListener(v -> listener.onCourseClick(course));
        }
    }
}
