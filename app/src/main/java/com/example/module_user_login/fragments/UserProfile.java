package com.example.module_user_login.fragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.module_user_login.EditProfile;
import com.example.module_user_login.R;
import com.example.module_user_login.models.Users;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfile extends Fragment {

    CircleImageView profile_image;
    TextView username,email,contact,usertype,name;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUrl;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;
    Button btn_edit_profile;

    public UserProfile() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        profile_image = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);
        name = view.findViewById(R.id.valueName);
        email = view.findViewById(R.id.valueEmail);
        contact = view.findViewById(R.id.valueContact);
        usertype = view.findViewById(R.id.valueUserType);
        btn_edit_profile = view.findViewById(R.id.btn_edit_profile);


        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(isAdded()) {
                    Users user = snapshot.getValue(Users.class);
                    assert user != null;
                    username.setText(user.getUsername());
                    name.setText(user.getUsername());
                    email.setText(user.getEmail());
                    contact.setText(user.getContact());
                    usertype.setText(user.getUsertype());
                    if(user.getImageURL().equals("default")) {
                        profile_image.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        Glide.with(Objects.requireNonNull(getContext())).load(user.getImageURL()).into(profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profile_image.setOnClickListener(view1 -> openImage());

        btn_edit_profile.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        });
        return view;
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = requireContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("uploading..");
        pd.show();

        if(imageUrl != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageUrl));
            uploadTask = fileReference.putFile(imageUrl);
            uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if(!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String muri = downloadUri.toString();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageURL",muri);
                    reference.updateChildren(map);

                    pd.dismiss();
                } else {
                    Toast.makeText(getContext(),"Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            });
        } else{
            Toast.makeText(getContext(),"No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUrl = data.getData();

            if(uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getContext(),"Upload in progress", Toast.LENGTH_SHORT).show();
            } else uploadImage();
        }
    }
}