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

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoLab extends AppCompatActivity implements CropFragment.ItemClicked
{
    static final int MAX_HEIGHT = 900;
    Intent intent;
    View parent;
    int height = 0;
    int width = 0;
    private Uri uri;
    View actionBar;
    ImageView check;
    static Bitmap mBitmap;
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

        Filter filter = SampleFilters.getStarLitFilter();
        Bitmap out = filter.processFilter(bitmap);
        bitmaps.add(0, out);

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

                //setting up action bar
                setActionBar(R.layout.utility_nav_bar);
                actionBar = getSupportActionBar().getCustomView();
                check = actionBar.findViewById(R.id.check);

                //setting up fragment
                CropImageFragment cropImageFragment = new CropImageFragment();
                setUtilityFragment(cropImageFragment);
                fragmentTransition(cropFragment);

                //passing image to crop image fragment
                CropImageFragment.setBitmap(bitmaps.get(0));

                //setting up visibility
                menuBar.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);

                // In onclick listener
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // returning image
                        CropImageFragment cropImageFragment = (CropImageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
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

                        // initialising fragment
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

                //setting new action bar
                setActionBar(R.layout.utility_nav_bar);

                //changing text
                actionBar = getSupportActionBar().getCustomView();
                TextView heading = actionBar.findViewById(R.id.heading);
                heading.setText(R.string.filter);

                // transitioning fragment
                fragmentTransition(filterFragment);

                // visibility decisions
                menuBar.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
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
            fragmentTransaction.show(cropFragment);
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
    private Bitmap adjustImageBoundsMin( )
    {
        Bitmap resizedImage = uriToBitmap(uri);
        if (height < MAX_HEIGHT)
        {
            resizedImage = Bitmap.createScaledBitmap(resizedImage, (int) (width * 2), (int) (height * 2), true);
        } else
            resizedImage = Bitmap.createScaledBitmap(resizedImage, (int) (resizedImage.getWidth() * 1.01), (int) (resizedImage.getHeight() * 1.01), true);
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

    @Override
    public void rotateClicked(float degrees) {
        CropImageFragment cropImageFragment = (CropImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        cropImageFragment.setRotation(degrees);
    }
}
