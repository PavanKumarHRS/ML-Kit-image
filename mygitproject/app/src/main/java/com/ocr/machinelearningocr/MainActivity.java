package com.ocr.machinelearningocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView txtResult;
    private Bitmap bitmap;
    private ExcelHelper excelHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        txtResult = findViewById(R.id.txtResult);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);

        excelHelper = new ExcelHelper();

        btnSelectImage.setOnClickListener(v -> selectImage());
    }

    private void selectImage() {
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                bitmap = rotateBitmapIfNeeded(bitmap);
                imageView.setImageBitmap(bitmap);
                detectTextAndSave();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap rotateBitmapIfNeeded(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(0); // Adjust if needed
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void detectTextAndSave() {
        if (bitmap == null) {
            Toast.makeText(this, "Select an image first!", Toast.LENGTH_SHORT).show();
            return;
        }

        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(result -> {
                    StringBuilder detectedText = new StringBuilder();
                    List<Text.TextBlock> blocks = result.getTextBlocks();
                    for (Text.TextBlock block : blocks) {
                        detectedText.append(block.getText()).append("\n");
                    }

                    if (detectedText.length() > 0) {
                        excelHelper.saveTextToExcel(this, detectedText.toString());
                        String savedData = excelHelper.readFromExcel(this);
                        txtResult.setText(savedData);
                        Toast.makeText(this, "Text saved & retrieved successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No text detected!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Log.e("OCR", "Failed: " + e.getMessage()));
    }
}
