package com.hackstudio.passengerguide.LostAndFound;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackstudio.passengerguide.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReportLostItemActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private EditText stationNameEditText;
    private Button uploadDataButton;
    private TextView selectedDateTextView;
    private ImageView imageView, selectDateImageView, captureOrSelectImageView;

    private Calendar selectedDate = Calendar.getInstance();
    private Uri selectedImageUri;

    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_lost_item);

        stationNameEditText = findViewById(R.id.stationNameEditText);
        captureOrSelectImageView = findViewById(R.id.captureOrSelectImageView);
        selectDateImageView = findViewById(R.id.selectDateImageView);
        uploadDataButton = findViewById(R.id.uploadDataButton);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        imageView = findViewById(R.id.imageView);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        captureOrSelectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Choose between capture and select from gallery
                showImageSourceDialog();
            }
        });

        selectDateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        uploadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDataToFirestore();
            }
        });
    }

    private void showImageSourceDialog() {
        // Check if the app has camera permission
        if (checkCameraPermission()) {
            // If permission is granted, show the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Image Source")
                    .setItems(new CharSequence[]{"Capture Photo", "Choose from Gallery"},
                            (dialog, which) -> {
                                switch (which) {
                                    case 0:
                                        // Capture photo
                                        dispatchTakePictureIntent();
                                        break;
                                    case 1:
                                        // Choose from gallery
                                        openGallery();
                                        break;
                                }
                            });
            builder.show();
        } else {
            // If permission is not granted, request it
            requestCameraPermission();
        }
    }
    private boolean checkCameraPermission() {
        // Check if the app has camera permission
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        // Request camera permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }




    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
    }

    private void uploadDataToFirestore() {
        final String stationName = stationNameEditText.getText().toString().trim();
        final String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.getTime());

        if (stationName.isEmpty() || formattedDate.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            return;
        }

        final StorageReference imageRef = storageRef.child("lost_items/" + formattedDate + ".jpg");
        Bitmap imageBitmap;
        try {
            imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
        } catch (IOException e) {
            Log.e("Error", "Error getting image bitmap", e);
            Toast.makeText(this, "Error getting image", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Map<String, Object> data = new HashMap<>();
                data.put("stationName", stationName);
                data.put("date", formattedDate);
                data.put("imageURL", uri.toString());

                firestore.collection("lost_items")
                        .add(data)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(ReportLostItemActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Error adding document", e);
                            Toast.makeText(ReportLostItemActivity.this, "Error uploading data", Toast.LENGTH_SHORT).show();
                        });
            });
        });
    }

    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateSelectedDateTextView();
            }
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void updateSelectedDateTextView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(selectedDate.getTime());
        selectedDateTextView.setText(formattedDate);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Image captured from camera
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImageUri = getImageUri(imageBitmap);
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Image selected from gallery
            selectedImageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}
