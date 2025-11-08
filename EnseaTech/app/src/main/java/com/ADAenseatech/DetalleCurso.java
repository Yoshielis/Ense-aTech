package com.ADAenseatech;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.educapp.adapters.LessonAdapter;
import com.educapp.models.Course;
import com.educapp.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public class DetalleCurso extends AppCompatActivity {
    private TextView tvCourseTitle, tvCourseDescription, tvProgress;
    private RecyclerView rvLessons;
    private LessonAdapter lessonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initializeViews();
        loadCourseData();
    }

    private void initializeViews() {
        tvCourseTitle = findViewById(R.id.tvCourseTitle);
        tvCourseDescription = findViewById(R.id.tvCourseDescription);
        tvProgress = findViewById(R.id.tvProgress);
        rvLessons = findViewById(R.id.rvLessons);
    }

    private void loadCourseData() {
        String courseId = getIntent().getStringExtra("COURSE_ID");
        Course course = getCourseById(courseId);

        if (course != null) {
            displayCourseInfo(course);
            setupLessonsList(course.getLessons());
        }
    }

    private Course getCourseById(String courseId) {
        // Simulación de obtención de curso
        List<Course> allCourses = generateSampleCourses();
        return Course.findCourseById(allCourses, courseId);
    }

    private void displayCourseInfo(Course course) {
        tvCourseTitle.setText(course.getTitle());
        tvCourseDescription.setText(course.getDescription());
        tvProgress.setText("Progreso: " + course.getProgress() + "%");
    }

    private void setupLessonsList(List<Lesson> lessons) {
        lessonAdapter = new LessonAdapter(lessons, this::onLessonSelected);
        rvLessons.setLayoutManager(new LinearLayoutManager(this));
        rvLessons.setAdapter(lessonAdapter);
    }

    private List<Course> generateSampleCourses() {
        List<Course> courses = new ArrayList<>();

        Course physicsCourse = new Course("C1", "Física Básica",
                "Aprende los fundamentos de la física con experimentos caseros",
                "Física", "T1");

        // Agregar lecciones de ejemplo
        List<Lesson> physicsLessons = new ArrayList<>();

        Lesson lesson1 = new Lesson();
        lesson1.setId("L1");
        lesson1.setTitle("Introducción a la Física");
        lesson1.setDescription("Conceptos básicos y método científico");
        lesson1.setExperimentDescription("Experimento: Medición de densidad con objetos caseros");
        lesson1.setProgress(0.0);

        physicsLessons.add(lesson1);
        physicsCourse.setLessons(physicsLessons);
        courses.add(physicsCourse);

        return courses;
    }

    private void onLessonSelected(Lesson lesson) {
        Intent intent = new Intent(this, LessonActivity.class);
        intent.putExtra("LESSON_ID", lesson.getId());
        startActivity(intent);
    }
}
