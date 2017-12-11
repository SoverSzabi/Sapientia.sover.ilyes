package com.ilyes.sover.sapientiasoverilyes;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewAdvertisementActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextLocation;
    private Button buttonAdd, buttonAddImage;
    private ImageView imageView;
    private Uri imagePath;

    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;

    private final int PICK_IMAGE_REQUEST = 2;
    private final String STORAGE_PATH = "images/";
    private final String DATABASE_PATH = "advertisments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_advertisment);

        mStorageReference   = FirebaseStorage.getInstance().getReference();
        mDatabaseReference  = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

        editTextTitle       = findViewById(R.id.title);
        editTextDescription = findViewById(R.id.description);
        editTextLocation    = findViewById(R.id.location);

        buttonAdd           = findViewById(R.id.add_button);
        buttonAddImage      = findViewById(R.id.add_image_button);

        imageView           = findViewById(R.id.imageView);

        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAdvertisment();
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            imagePath = data.getData();
            Glide.with(getApplicationContext()).load(imagePath).into(imageView);
        }
    }

    public String getImageExt(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadAdvertisment()
    {
        if(imagePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference reference = mStorageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imagePath));
            reference.putFile(imagePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(NewAdvertisementActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            String title = editTextTitle.getText().toString().trim();
                            String description = editTextDescription.getText().toString().trim();
                            String location = editTextLocation.getText().toString().trim();
                            String downloadURL = taskSnapshot.getDownloadUrl().toString();

                            FirebaseUser loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
                            String advertiserId = loggedInUser.getUid().toString();

                            Advertisment advertisment = new Advertisment(advertiserId, title, description, location, downloadURL);

                            String key = mDatabaseReference.push().getKey();
                            mDatabaseReference.child(key).setValue(advertisment);
                            advertisment.setId(key);

                            startActivity(new Intent(getBaseContext(), ListScreenActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewAdvertisementActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }

    }
}
