package com.afshan.android.photolab;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class PhotoLab extends AppCompatActivity implements CropFragment.ItemClicked, FilterFragment.Image, EffectsFragment.Effects, TextFragment.TextInterface, TextandDrawing.TextView, DrawFragment.draw, Drawing.transition {
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
    View frame;
    PhotoFragment mPhotoFragment;
    PhotoFragment photoFragment;
    TextFragment textFragment;
    DrawFragment drawFragment;
    View menuBar;

    public static int getOrientation(Context context, Uri photoUri) {

        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor == null || cursor.getCount() != 1) {
            return 90;  //Assuming it was taken portrait
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri, int maxwidth)
            throws IOException {

        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        assert is != null;
        is.close();

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            actualHeight = options.outHeight;
            actualWidth = options.outWidth;
        } else {
            actualWidth = options.outWidth;
            actualHeight = options.outHeight;
        }

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 912.0f;
        float maxWidth = 716.0f;
        float imgRatio = actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / (maxHeight);

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        srcBitmap = BitmapFactory.decodeStream(is, null, options);


        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            assert srcBitmap != null;
            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        return srcBitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_lab);
        intent = getIntent();
        uri = intent.getData();
        Toasty.Config.getInstance().setTextSize(10).apply();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        bitmaps.clear();
        setActionBar(R.layout.lab_navbar);
        System.out.println(uri);
        System.loadLibrary("NativeImageProcessor");
        // Initialisation of Views
        final ImageView crop = findViewById(R.id.crop);
        final ImageView filters = findViewById(R.id.filter);
        final ImageView fit = findViewById(R.id.fit);
        final ImageView effects = findViewById(R.id.effects);
        final ImageView text = findViewById(R.id.text);
        final ImageView draw = findViewById(R.id.draw);
         frame = findViewById(R.id.fragment_frame);


        photoFragment = new PhotoFragment();

        Bitmap bitmap = null;
        try {
            bitmap = getCorrectlyOrientedImage(this, uri, 0);
        } catch (IOException e) {
            System.out.println("here!");
            e.printStackTrace();
        }
        bitmaps.add(0, bitmap);

        // setting up fragment
        setUtilityFragment(photoFragment);

        // passing image to photo fragment
        PhotoFragment.setBitmap(bitmaps.get(0));
        menuBar = findViewById(R.id.navigation_menu);
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
                        setUtilityFragment(photoFragment);

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

                Toasty.custom(PhotoLab.this, R.string.filtermessage, null, R.color.blue_bright, Toasty.LENGTH_LONG, false, true).show();
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
                        photoFragment.removeOnFlingListener();

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

                        photoFragment.setRemoveFilter();
                        photoFragment.removeOnFlingListener();

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
                textFragment = new TextFragment();
                fragmentTransition(textFragment);

                setActionBar(R.layout.utility_nav_bar);
                actionBar = getSupportActionBar().getCustomView();
                TextView heading = actionBar.findViewById(R.id.heading);
                ImageView check = actionBar.findViewById(R.id.check);
                ImageView close = actionBar.findViewById(R.id.close);
                heading.setText("Text");

                final TextandDrawing textandDrawing = new TextandDrawing();
                setUtilityFragment(textandDrawing);
                textandDrawing.setBitmap(bitmaps.get(0));

                frame.setVisibility(View.VISIBLE);
                menuBar.setVisibility(View.GONE);

                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        TextandDrawing textandDrawing1 = (TextandDrawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
                        textandDrawing1.getBitmap();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        setUtilityFragment(photoFragment);

                        // post image setting work
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(textFragment);

                        // re-forming visibility
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);
                    }
                });
            }
        });
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                drawFragment = new DrawFragment();
                fragmentTransition(drawFragment);

                setActionBar(R.layout.utility_nav_bar);
                actionBar = getSupportActionBar().getCustomView();
                TextView heading = actionBar.findViewById(R.id.heading);
                ImageView check = actionBar.findViewById(R.id.check);
                ImageView close = actionBar.findViewById(R.id.close);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Drawing drawing = (Drawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
                        drawing.getBitmap();
                    }
                });

                close.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        setUtilityFragment(photoFragment);

                        // post image setting work
                        setActionBar(R.layout.lab_navbar);

                        // initialising fragment and hiding it
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().hide(photoFragment);

                        // re-forming visibility
                        menuBar.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.GONE);
                    }
                });

                heading.setText("Draw");

                final Drawing drawing = new Drawing();
                setUtilityFragment(drawing);
                drawing.setBitmap(bitmaps.get(0));

                frame.setVisibility(View.VISIBLE);
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

    @Override
    public void setOriginalImage() {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setRemoveFilter();
    }

    @Override
    public void setImage(Bitmap b) {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.setImage(b);
    }

    @Override
    public void addOnFlingListener() {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        photoFragment.addOnFlingListener();
    }

    @Override
    public void addText(int color, String Text, Typeface typeface, boolean isRequested) {
        TextandDrawing textandDrawing = (TextandDrawing) getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        textandDrawing.addText(color, Text, typeface, isRequested);
    }

    @Override
    public void requestNewText()
    {
      TextFragment textFragment = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
      textFragment.requestNewText();
    }
    public void setMyTransition()
    {
        setActionBar(R.layout.lab_navbar);

        // initialising fragment and hiding it
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().hide(textFragment);

        // re-forming visibility
        menuBar.setVisibility(View.VISIBLE);
        frame.setVisibility(View.GONE);

        setUtilityFragment(photoFragment);
        PhotoFragment.setBitmap(bitmaps.get(0));
    }
    public void brushSize(float size)
    {
        Drawing drawing = (Drawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        drawing.brushSize(size);
    }
    public void brushOpacity(int opacity)
    {
        Drawing drawing = (Drawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        drawing.brushOpacity(opacity);
    }
    public void brushColor(int color)
    {
        Drawing drawing = (Drawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        drawing.brushColor(color);
    }
    public void setEraserSize(int size)
    {
        Drawing drawing = (Drawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        drawing.setEraserSize(size);
    }
    public void undo()
    {
        Drawing drawing = (Drawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        drawing.undo();
    }
    public void redo()
    {
        Drawing drawing = (Drawing)getSupportFragmentManager().findFragmentById(R.id.fragmentImage);
        drawing.redo();
    }

    @Override
    public void setTransition()
    {
        setActionBar(R.layout.lab_navbar);

        // initialising fragment and hiding it
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().hide(drawFragment);

        // re-forming visibility
        menuBar.setVisibility(View.VISIBLE);
        frame.setVisibility(View.GONE);

        setUtilityFragment(photoFragment);
        PhotoFragment.setBitmap(bitmaps.get(0));
    }
}
