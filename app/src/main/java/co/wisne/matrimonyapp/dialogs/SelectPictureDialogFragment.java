package co.wisne.matrimonyapp.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.wisne.matrimonyapp.R;

import static android.content.ContentValues.TAG;

public class SelectPictureDialogFragment extends DialogFragment{

    static final int CHOOSE_PICTURE_FROM_GALLERY = 125;
    static final int REQUEST_IMAGE_CAPTURE = 127;


    String mCurrentPhotoPath;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select a picture");


        builder.setItems(R.array.dialog_choose_picture, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0 ){
                    Log.d(TAG, "onClick: Take a picture");
                    takePictureIntent();
                }else if(i == 1){
                    Log.d(TAG, "onClick: Choose picture from gallery");
                    chooseFromGallery();
                }
            }
        });


        return builder.create();
    }

    private void chooseFromGallery(){
        if(getActivity()!= null){

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                getActivity().startActivityForResult(intent,CHOOSE_PICTURE_FROM_GALLERY);
            }

        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void takePictureIntent(){
        if(getActivity()!= null){

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {

                File photoFile = null;

                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.e(TAG, "takePictureIntent: ",ex );
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            "co.wisne.matrimonyapp.fileprovider",
                            photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    getActivity().startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }

            }

        }
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }
}
