package com.ADAenseatech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.educapp.adapters.CourseAdapter;
import com.educapp.models.Course;
import com.educapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class HomeEstudiante extends AppCompatActivity {
    private TextView tvWelcome;
    private RecyclerView rvCourses;
    private Button btnNotes, btnForum, btnNews, btnAskTutor;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        initializeViews();
        loadUserData();
        setupCourseList();
        setupButtons();
    }

    private void initializeViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        rvCourses = findViewById(R.id.rvCourses);
        btnNotes = findViewById(R.id.btnNotes);
        btnForum = findViewById(R.id.btnForum);
        btnNews = findViewById(R.id.btnNews);
        btnAskTutor = findViewById(R.id.btnAskTutor);
    }

    private void loadUserData() {
        // Cargar datos del usuario desde SharedPreferences
        String userName = getSharedPreferences("UserSession", MODE_PRIVATE)
                .getString("userName", "Estudiante");
        tvWelcome.setText("Bienvenido, " + userName);
    }

    private void setupCourseList() {
        courseList = generateSampleCourses();

        // Algoritmo: Filtrar cursos con progreso mayor a 0%
        List<Course> activeCourses = filterActiveCourses(courseList);

        courseAdapter = new CourseAdapter(activeCourses, this::onCourseSelected);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        rvCourses.setAdapter(courseAdapter);
    }

    private List<Course> filterActiveCourses(List<Course> courses) {
        List<Course> activeCourses = new ArrayList<>();
        // Usando ciclo for-each
        for (Course course : courses) {
            if (course.getProgress() > 0) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    private List<Course> generateSampleCourses() {
        List<Course> courses = new ArrayList<>();

        // Ejemplo de uso de ciclo while
        int i = 0;
        while (i < 5) {
            Course course = new Course(
                    "C" + i,
                    "Curso " + (i + 1),
                    "Descripción del curso " + (i + 1),
                    i % 2 == 0 ? "Física" : "Cálculo",
                    "T1"
            );
            course.setProgress(i * 20.0); // Progreso de ejemplo
            courses.add(course);
            i++;
        }

        return courses;
    }

    private void setupButtons() {
        btnNotes.setOnClickListener(v ->
                startActivity(new Intent(this, NotesActivity.class)));

        btnForum.setOnClickListener(v ->
                startActivity(new Intent(this, ForumActivity.class)));

        btnNews.setOnClickListener(v ->
                showMessage("Sección de Noticias - Próximamente"));

        btnAskTutor.setOnClickListener(v ->
                showMessage("Formulario de Preguntas a Tutores - Próximamente"));
    }

    private void onCourseSelected(Course course) {
        Intent intent = new Intent(this, DetalleCurso.class);
        intent.putExtra("COURSE_ID", course.getId());
        startActivity(intent);
    }

    private void showMessage(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}