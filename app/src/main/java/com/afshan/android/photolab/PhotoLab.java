package com.afshan.android.photolab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoLab extends AppCompatActivity implements CropFragment.ItemClicked
{
    static final int REQUEST = 50;
    static final int MIN_HEIGHT = 200;
    static final int MAX_HEIGHT = 900;
    Intent intent;
    View parent;
    int height = 0;
    int width = 0;
    private Uri uri;
    View actionBar;
    ImageView check;
    static ArrayList<Bitmap> bitmaps = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_lab);
        intent = getIntent();
        uri = intent.getData();

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        setActionBar(R.layout.lab_navbar);
        final ImageView crop = findViewById(R.id.crop);
        final ImageView fit = findViewById(R.id.fit);
        final ImageView effects = findViewById(R.id.effects);
        final ImageView text = findViewById(R.id.text);
        final View frame = findViewById(R.id.fragment_frame);
        final PhotoFragment photoFragment = new PhotoFragment();
        setUtilityFragment(photoFragment);
        final Bitmap bitmap = adjustImageBoundsMin();
        bitmaps.add(0, bitmap);
        PhotoFragment.setBitmap(bitmaps.get(0));
        final View menuBar = findViewById(R.id.navigation_menu);
        parent = findViewById(R.id.relativeLayout);
        crop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final CropFragment cropFragment = new CropFragment();
                setActionBar(R.layout.utility_nav_bar);
                actionBar = getSupportActionBar().getCustomView();
                check = actionBar.findViewById(R.id.check);
                CropImageFragment cropImageFragment = new CropImageFragment();
                setUtilityFragment(cropImageFragment);
                fragmentTransition(cropFragment);
                CropImageFragment.setBitmap(bitmaps.get(0));
                menuBar.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        CropImageFragment cropImageFragment = (CropImageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
                        Bitmap bitmap = cropImageFragment.getCroppedImage();
                        bitmaps.add(0, bitmap);
                        PhotoFragment photoFragment1 = new PhotoFragment();
                        setUtilityFragment(photoFragment1);
                        PhotoFragment.setBitmap(bitmaps.get(0));
                        setActionBar(R.layout.lab_navbar);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(cropFragment);
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);

                    }
                });

            }
        });
        fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FitFragment fitFragment = new FitFragment();
                fragmentTransition(fitFragment);
                menuBar.setVisibility(View.GONE);
            }
        });
        effects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EffectsFragment effectsFragment = new EffectsFragment();
                fragmentTransition(effectsFragment);
                menuBar.setVisibility(View.GONE);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextFragment textFragment = new TextFragment();
                fragmentTransition(textFragment);
                menuBar.setVisibility(View.GONE);
            }
        });
    }
    private void setActionBar(int layoutId)
    {
        try
        {
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(layoutId);
            getSupportActionBar().setElevation(3.0f);
        }
        catch (NullPointerException n)
        {
            Toast.makeText(PhotoLab.this, R.string.settingActionBar, Toast.LENGTH_SHORT).show();
        }
    }
    private void fragmentTransition(Fragment cropFragment)
    {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_frame, cropFragment);
            fragmentTransaction.commit();
        }
        catch (IllegalStateException i)
        {
            Toast.makeText(this, R.string.creatingFragment, Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap uriToBitmap(Uri selectedFileUri)
    {
        Bitmap image = null;
        try
        {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedFileUri, "r");
            assert parcelFileDescriptor != null;
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
        } catch (IOException e)
        {
            Toast.makeText(this, R.string.loadingImage, Toast.LENGTH_SHORT).show();
        }
        assert image != null;
        height = image.getHeight();
        width = image.getWidth();
        return image;
    }


    private void adjustImageBounds(View view)
    {
        //Toast.makeText(this, parent.getHeight() + " " + view.getHeight() + " " + height, Toast.LENGTH_SHORT).show();
        if (parent.getHeight() -  MIN_HEIGHT < height) {
            Bitmap resizedImage = Bitmap.createScaledBitmap(uriToBitmap(uri), (int) (width / 1.5), (int) (height / 1.5), true);
            CropImageFragment.setBitmap(resizedImage);
        }
    }
    private Bitmap adjustImageBoundsMin( )
    {
        Bitmap resizedImage = uriToBitmap(uri);
        if (height < MAX_HEIGHT)
        {
            resizedImage = Bitmap.createScaledBitmap(resizedImage, (int) (width * 2), (int) (height * 2), true);
        }
        return resizedImage;
    }
    private void setUtilityFragment(Fragment fragment)
    {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentImage, fragment);
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
        catch (IllegalStateException i)
        {
            Toast.makeText(this, R.string.creatingFragment, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void itemClicked(int AspectRatioA, int AspectRatioB)
    {
        CropImageFragment cropImageFragment = (CropImageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        cropImageFragment.setAspectRatio(AspectRatioA, AspectRatioB);

    }

}
