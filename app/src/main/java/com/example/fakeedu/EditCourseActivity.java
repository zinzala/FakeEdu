package com.example.fakeedu;
import org.jetbrains.annotations.Nullable;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private TextInputEditText courseNameEdt,coursePricecEdt,courseSuitedForEdt,courseImgEdt,courseLinkEdt,courseDescEdt;
    private Button updateCourseBtn,deleteCourseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String courseID;
    private CourseRVModal courseRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        firebaseDatabase = FirebaseDatabase.getInstance();
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        coursePricecEdt = findViewById(R.id.idEdtCoursePrice);
        courseSuitedForEdt = findViewById(R.id.idEdtCourseSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        courseDescEdt = findViewById(R.id.idEdtCourseDesc);
        updateCourseBtn = findViewById(R.id.idBtnUpdateCourse);
        deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);
        loadingPB = findViewById(R.id.idPBLoading);

        // first i have to get data from my privious activity
        courseRVModal = getIntent().getParcelableExtra("course");
        if (courseRVModal!=null){
            courseNameEdt.setText(courseRVModal.getCourseName());
            coursePricecEdt.setText(courseRVModal.getCoursePrice());
            courseSuitedForEdt.setText(courseRVModal.getBestSuitedFor());
            courseImgEdt.setText(courseRVModal.getCourseImg());
            courseLinkEdt.setText(courseRVModal.getCourseLink());
            courseDescEdt.setText(courseRVModal.getCourseDescription());
            courseID = courseRVModal.getCourseID();
        }

        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);
        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting all the data from our editText
                String courseName = courseNameEdt.getText().toString();
                String coursePrice = coursePricecEdt.getText().toString();
                String suitedFor = courseSuitedForEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();

                // passing data to our database
                Map<String, Object> map = new HashMap<>();
                // now inside this map i have to put my data with help of key and value-pair
                map.put("courseName", courseName);
                map.put("courseDescription", courseDesc);
                map.put("coursePrice", coursePrice);
                map.put("bestSuitedFor", suitedFor);
                map.put("courseImg", courseImg);
                map.put("courseLink", courseLink);
                map.put("courseID", courseID);

                // Update the data directly in the databaseReference
                databaseReference.updateChildren(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        loadingPB.setVisibility(View.GONE);
                        if (error == null) {
                            // Data was updated successfully
                            Toast.makeText(EditCourseActivity.this, "Course updated..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditCourseActivity.this, MainActivity2.class));
                            finishAffinity();
                        } else {
                            // Handle the error, if any
                            Toast.makeText(EditCourseActivity.this, "Failed to update Course.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCourse();
            }
        });

    }
    private void deleteCourse(){
        databaseReference.removeValue();
        Toast.makeText(this, "Course Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this,MainActivity2.class));
        finishAffinity();
    }
}