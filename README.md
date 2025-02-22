📌 OCR Text Recognition Android App
A powerful Android application for extracting text from images using Google's ML Kit and saving the extracted text into an Excel file. This project uses Image Picker for image selection and integrates Apache POI for handling Excel files.

🚀 Features
✔ Image Selection: Users can select an image from the gallery or capture one using the camera.
✔ Text Recognition: Uses Google ML Kit's Text Recognition API to detect and extract text from images.
✔ Excel File Management: Saves extracted text into an Excel (.xlsx) file using Apache POI.
✔ Retrieve & Display Text: Reads the saved text from Excel and displays it in the app.
✔ Real-time Processing: Performs text recognition in real time after selecting an image.
✔ User-Friendly UI: Simple and intuitive user interface for smooth interaction.

🛠 Technologies & Libraries Used
Component	Technology
Programming Language	Java
Text Recognition	Google ML Kit (TextRecognition API)
Image Handling	ImagePicker Library
Excel File Handling	Apache POI
Permissions	AndroidX (for runtime permissions)
📂 Project Structure
bash
Copy
Edit
📦 OCR-Text-Recognition
 ┣ 📂 app/src/main/java/com/ocr/machinelearningocr/
 ┃ ┣ 📜 MainActivity.java   # Handles image selection & text recognition
 ┃ ┣ 📜 ExcelHelper.java   # Handles Excel file creation & data storage
 ┣ 📂 app/src/main/res/layout/
 ┃ ┣ 📜 activity_main.xml  # UI Layout
 ┣ 📜 AndroidManifest.xml  # Permissions & app metadata
 ┣ 📜 build.gradle         # Dependencies & configurations
📥 Setup & Installation
1️⃣ Clone the Repository
sh
Copy
Edit
git clone https://github.com/YOUR_USERNAME/OCR-Text-Recognition.git
cd OCR-Text-Recognition
2️⃣ Open in Android Studio
Open Android Studio.
Click "Open an existing project" and select the cloned folder.
3️⃣ Install Dependencies
Ensure Google ML Kit and Apache POI are added to build.gradle:
gradle
Copy
Edit
dependencies {
    implementation 'com.google.mlkit:text-recognition:16.0.0'
    implementation 'org.apache.poi:poi-ooxml:5.2.3'
    implementation 'com.github.dhaval2404:imagepicker:2.1'
}
4️⃣ Add Permissions
Add the following permissions to AndroidManifest.xml:

xml
Copy
Edit
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.CAMERA"/>
5️⃣ Run the App
Connect your Android device or use an emulator.
Click Run (▶) in Android Studio.
🔥 Usage Guide
1️⃣ Select an Image: Click the "Select Image" button to choose a photo.
2️⃣ Text Detection: The app will automatically detect and extract text.
3️⃣ Save to Excel: Extracted text is saved into an Excel file.
4️⃣ Retrieve Data: The app reads and displays saved text from the Excel file.

📸 Screenshots
Image Selection	Text Recognition	Excel File Saving
🛠 Code Explanation
MainActivity.java (Core OCR Processing)
Handles image selection using ImagePicker.
Processes the image using ML Kit and extracts text.
Saves the text into an Excel file using ExcelHelper.java.
java
Copy
Edit
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
            for (Text.TextBlock block : result.getTextBlocks()) {
                detectedText.append(block.getText()).append("\n");
            }

            if (detectedText.length() > 0) {
                excelHelper.saveTextToExcel(this, detectedText.toString());
                txtResult.setText(excelHelper.readFromExcel(this));
                Toast.makeText(this, "Text saved & retrieved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No text detected!", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(e -> Log.e("OCR", "Failed: " + e.getMessage()));
}
📜 License
This project is licensed under the MIT License – you are free to use, modify, and distribute it.

🤝 Contributing
Want to improve this project? Feel free to:

Fork the repository
Create a Pull Request
Report Bugs & Issues
📬 Contact
📧 Email: your.email@example.com
🐦 Twitter: @yourusername
🌐 Website: yourwebsite.com

