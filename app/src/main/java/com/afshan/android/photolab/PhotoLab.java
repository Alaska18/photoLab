package com.afshan.android.photolab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;


public class PhotoLab extends AppCompatActivity implements CropFragment.ItemClicked, FilterFragment.Image, EffectsFragment.Effects {
    static final int MAX_HEIGHT = 900;
    static int height = 0;
    static int width = 0;
    static Uri uri;
    static Bitmap mBitmap;
    static ArrayList<Bitmap> bitmaps = new ArrayList<>();
    Intent intent;
    View parent;
    View actionBar;
    ImageView check;
    PhotoFragment mPhotoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_lab);
        intent = getIntent();
        uri = intent.getData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        bitmaps.clear();
        setActionBar(R.layout.lab_navbar);
        System.loadLibrary("NativeImageProcessor");
        // Initialisation of Views
        final ImageView crop = findViewById(R.id.crop);
        final ImageView filters = findViewById(R.id.filter);
        final ImageView fit = findViewById(R.id.fit);
        final ImageView effects = findViewById(R.id.effects);
        final ImageView text = findViewById(R.id.text);
        final View frame = findViewById(R.id.fragment_frame);
        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.photo_reference);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, 400, 400, false);


        final PhotoFragment photoFragment = new PhotoFragment();

        // processing Input Image (Needs some more work)
        final Bitmap bitmap = adjustImageBoundsMin();
        bitmaps.add(0, bitmap);

        // setting up fragment
        setUtilityFragment(photoFragment);

        // passing image to photo fragment
        PhotoFragment.setBitmap(bitmaps.get(0));
        final View menuBar = findViewById(R.id.navigation_menu);
        parent = findViewById(R.id.relativeLayout);
        // Onclick listeners
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CropFragment cropFragment = new CropFragment();
                fragmentTransition(cropFragment);

                //setting up action bar
                setActionBar(R.layout.utility_nav_bar);
                actionBar = getSupportActionBar().getCustomView();
                TextView heading = actionBar.findViewById(R.id.heading);
                heading.setText(R.string.Crop_rotate);

                check = actionBar.findViewById(R.id.check);

                //setting up fragment
                CropImageFragment cropImageFragment = new CropImageFragment();
                setUtilityFragment(cropImageFragment);

                //passing image to crop image fragment
                CropImageFragment.setBitmap(bitmaps.get(0));

                //setting up visibility
                menuBar.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                ImageView close = actionBar.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        // only post image setting work
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(cropFragment);

                        PhotoFragment photoFragment = new PhotoFragment();
                        setUtilityFragment(photoFragment);


                        // re-forming visibility
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);

                    }
                });

                // In onclick listener
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // returning image
                        CropImageFragment cropImageFragment = (CropImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
                        Bitmap bitmap = cropImageFragment.getCroppedImage();

                        // adding to the list of bitmaps to keep track
                        bitmaps.add(0, bitmap);

                        // re initialising photo fragment
                        PhotoFragment photoFragment1 = new PhotoFragment();
                        setUtilityFragment(photoFragment1);

                        // passing new image
                        PhotoFragment.setBitmap(bitmaps.get(0));

                        //setting old action bar
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it.
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(cropFragment);

                        // re-forming visibility
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);

                    }
                });

            }
        });
        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // initialising filter fragment
                final FilterFragment filterFragment = new FilterFragment();

                // transitioning fragment
                fragmentTransition(filterFragment);

                // visibility decisions
                menuBar.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                //setting new action bar
                setActionBar(R.layout.utility_nav_bar);

                //changing text
                actionBar = getSupportActionBar().getCustomView();
                TextView heading = actionBar.findViewById(R.id.heading);
                heading.setText(R.string.filter);

                // setting on click listeners for the okay and close.
                ImageView check = actionBar.findViewById(R.id.check);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bitmap bitmap = PhotoFragment.buffer;
                        bitmaps.add(0, bitmap);

                        //setting new image
                        PhotoFragment.setBitmap(bitmaps.get(0));

                        // post image setting work
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(filterFragment);

                        // re-forming visibility
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);

                    }
                });

                ImageView close = actionBar.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PhotoFragment photoFragment1 = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
                        photoFragment1.setRemoveFilter();

                        // only post image setting work
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(filterFragment);

                        // re-forming visibility
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

                final EffectsFragment effectsFragment = new EffectsFragment();
                fragmentTransition(effectsFragment);

                PhotoFragment photoFragment1 = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
                photoFragment1.setImages();

                setActionBar(R.layout.utility_nav_bar);

                //changing text
                actionBar = getSupportActionBar().getCustomView();
                TextView heading = actionBar.findViewById(R.id.heading);
                heading.setText(R.string.effects);
                menuBar.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);


                ImageView check = actionBar.findViewById(R.id.check);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bitmap bitmap = PhotoFragment.buffer;
                        bitmaps.add(0, bitmap);

                        //setting new image
                        PhotoFragment.setBitmap(bitmaps.get(0));

                        // post image setting work
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(effectsFragment);

                        // re-forming visibility
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);

                    }
                });

                ImageView close = actionBar.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PhotoFragment photoFragment1 = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
                        photoFragment1.setRemoveFilter();

                        // only post image setting work
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(effectsFragment);

                        // re-forming visibility
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);

                    }
                });

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

    private void setActionBar(int layoutId) {
        try {
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(layoutId);
            getSupportActionBar().setElevation(3.0f);
        } catch (NullPointerException n) {
            Toast.makeText(PhotoLab.this, R.string.settingActionBar, Toast.LENGTH_SHORT).show();
        }
    }

    private void fragmentTransition(Fragment cropFragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_frame, cropFragment);
            fragmentTransaction.setCustomAnimations(R.anim.animations, R.anim.slide_out, R.anim.animations, R.anim.slide_out);
            fragmentTransaction.show(cropFragment);
            fragmentTransaction.commit();

        } catch (IllegalStateException i) {
            Toast.makeText(this, R.string.creatingFragment, Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap uriToBitmap(Uri selectedFileUri) {
        Bitmap image = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedFileUri, "r");
            assert parcelFileDescriptor != null;
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
        } catch (IOException e) {
            Toast.makeText(this, R.string.loadingImage, Toast.LENGTH_SHORT).show();
        }
        assert image != null;
        height = image.getHeight();
        width = image.getWidth();
        return image;
    }

    private Bitmap adjustImageBoundsMin() {
        Bitmap resizedImage = uriToBitmap(uri);
        if (height < MAX_HEIGHT) {
            resizedImage = Bitmap.createScaledBitmap(resizedImage, width * 2, height * 2, true);
        } else
            resizedImage = Bitmap.createScaledBitmap(resizedImage, (int) (resizedImage.getWidth() * 1.01), (int) (resizedImage.getHeight() * 1.01), true);
        return resizedImage;
    }

    private void setUtilityFragment(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentImage, fragment);
            fragmentTransaction.setCustomAnimations(R.anim.animations, R.anim.slide_out, R.anim.animations, R.anim.slide_out);
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        } catch (IllegalStateException i) {
            Toast.makeText(this, R.string.creatingFragment, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void itemClicked(int AspectRatioA, int AspectRatioB) {
        CropImageFragment cropImageFragment = (CropImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        cropImageFragment.setAspectRatio(AspectRatioA, AspectRatioB);

    }

    @Override
    public void rotateClicked(float degrees) {
        CropImageFragment cropImageFragment = (CropImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        cropImageFragment.setRotation(degrees);
    }


    /*@Override
    public void setSaturation(int progress)
    {
        PhotoFragment photoFragment = (PhotoFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setImageSaturation(progress);
    }*/

    @Override
    public void setOriginalImage() {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setRemoveFilter();
    }

    @Override
    public void saturationChanged(float progress) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setSaturation(progress);
    }


    @Override
    public void contrastChanged(float progress) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setContrast(progress);
    }

    @Override
    public void brightnessChanged(float brightness) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setBrightness(brightness);
    }

    @Override
    public void sharpnessChanged(float sharpness) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setSharpness(sharpness);
    }

    @Override
    public void shadowsChanged(float shadows) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setShadows(shadows);
    }

    @Override
    public void highlightsChanged(float highlight) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setHighlight(highlight);
    }

    @Override
    public void exposureChanged(float exposure) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setExposure(exposure);
    }

    @Override
    public void okayClicked() {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.okayClicked();
    }

}
