package singh.rahul.example.com.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDownloadFolder;
    Button btnMusicFolder;
    Button btnDocumentsFolder;
    Button btnRingtonesFolder;
    Button btnPodcastsFolder;
    Button btnMoviesFolder;
    Button btnAlarmsFolder;
    Button btnPicturesFolder;

    Button btnSaveFile;
    EditText edtValue;

    Button btnRetrieveInfo;
    TextView txtValue;

    ImageView imgAnimal;

    Button btnAllowAccessPictures;

    LinearLayout linearLayoutHorizontal;
    ImageSwitcher imageSwitcher;


    ArrayList<String> filePathNames;
    File[] files;


    public static final int REQUEST_CODE = 1234;

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("LOG", "Permission is granted");
                return true;
            } else {

                Log.v("LOG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("LOG","Permission is granted");
            return true;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("LOG","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SDCARDchecker.checkWeatherExternalStorageAvailableOrNot(MainActivity.this);

        btnDocumentsFolder = (Button) findViewById(R.id.btnDownloadDirectoiry);
        btnMusicFolder = (Button) findViewById(R.id.btnMovieFolder);
        btnDocumentsFolder = (Button) findViewById(R.id.btndocumentfloder);
        btnRingtonesFolder = (Button) findViewById(R.id.btnRingToneFolder);
        btnPodcastsFolder = (Button) findViewById(R.id.btnProcastFolder);
        btnMoviesFolder = (Button) findViewById(R.id.btnMovieFolder);
        btnAlarmsFolder = (Button) findViewById(R.id.btnAlarmsFolder);
        btnPicturesFolder = (Button) findViewById(R.id.btnPictureFolder);
        btnRetrieveInfo = (Button) findViewById(R.id.btnRetriveTheInfo);
        btnSaveFile = (Button) findViewById(R.id.btnSaveFile);

        edtValue = (EditText) findViewById(R.id.edtText);
        txtValue =(TextView) findViewById(R.id.tetValue);
        imgAnimal = (ImageView) findViewById(R.id.imgAnimal);
        btnAllowAccessPictures = (Button) findViewById(R.id.btnAllowAccessPicture);
        linearLayoutHorizontal = (LinearLayout) findViewById(R.id.linearLayoutHorizontal);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory((ViewSwitcher.ViewFactory) MainActivity.this);
        imageSwitcher.setAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.slide_in_left));
        imageSwitcher.setAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.slide_out_right ));


btnAllowAccessPictures.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(isStoragePermissionGranted()){
            filePathNames = new ArrayList<String>();
            File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Animal Images");
            if (filePath.isDirectory() && filePath != null) {
                files = filePath.listFiles();
                for (int index = 0; index < files.length; index++) {
                    filePathNames.add(files[index].getAbsolutePath());
                }

            }

            for (int index = 0; index < filePathNames.size(); index++) {
                final ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageURI(Uri.parse(filePathNames.get(index)));
                imageView.setLayoutParams(new LinearLayout.LayoutParams(500, 500));

                final int i = index;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageSwitcher.setImageURI(Uri.parse(filePathNames.get(i)));

                    }
                });

                linearLayoutHorizontal.addView(imageView);
            }
        }
    }
});


        btnDownloadFolder.setOnClickListener(MainActivity.this);
        btnMusicFolder.setOnClickListener(MainActivity.this);
        btnDocumentsFolder.setOnClickListener(MainActivity.this);
        btnRingtonesFolder.setOnClickListener(MainActivity.this);
        btnPodcastsFolder.setOnClickListener(MainActivity.this);
        btnMoviesFolder.setOnClickListener(MainActivity.this);
        btnAlarmsFolder.setOnClickListener(MainActivity.this);
        btnPicturesFolder.setOnClickListener(MainActivity.this);
        btnSaveFile.setOnClickListener(MainActivity.this);
        btnRetrieveInfo.setOnClickListener(MainActivity.this);

        imgAnimal.setOnClickListener(MainActivity.this);

    }
    
    public View makeView() {


        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(1000,
                1000));

        return imageView;

    }

    public File returnStorageDirectoryForFolderName(String directoryName, String nameOfFolder) {

        File filePath = new File(Environment.getExternalStoragePublicDirectory(directoryName),
                nameOfFolder);
if(!filePath.mkdirs())
{
    letscreatetoast("there is no such directory is sd card");
} else
{
    letscreatetoast("there is directory is sd card" + nameOfFolder);
}
return filePath;
    }
    public  void letscreatetoast(String message)
    {
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    public void letsRetriveTheFileDataFromDocumentFolder()
    {
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "NiceFile.txt");
        try{
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String fileData= " ";
            String bufferData=" ";
            while((fileData = bufferedReader.readLine()) !=null)
            {
            bufferData = bufferData + fileData+"\n";
            }
            txtValue.setText(bufferData);
            bufferedReader.close();

        } catch (Exception e) {
            Log.i("LOG", e.toString());
            letscreatetoast("Exception occured check Log for more info");
        }
    }


    public void letsSavetheFileTheDocumentFolder()  {
        File filepath =new  File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"nice.txt");
    try{
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.append(edtValue.getText().toString());
        fileOutputStream.close();
        outputStreamWriter.close();
        letscreatetoast("Saved");

    } catch (FileNotFoundException e) {
        Log.i("LOG",e.toString());
        letscreatetoast("Exception occoured check the console");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public  void letsSaveTheImalgeToThePictureFolder(){
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.tiger);
            File  filepath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"tiger123.png");
            OutputStream outputStream  = new FileOutputStream(filepath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            letscreatetoast("Your Image is Sucessfully Saved");
        } catch (Exception e) {
            Log.i("Log",e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btnDownloadDirectoiry:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_DOWNLOADS, "Nice Download");
                break;

            case R.id.btnMusicDirectory:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_MUSIC, "Nice Music");
                break;
            case R.id.btndocumentfloder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_DOCUMENTS,"Nice Document");
                break;
            case R.id.btnRingToneFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_RINGTONES,"Nice RingTone");
                break;
            case R.id.btnProcastFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_PODCASTS,"nice procast");
                break;
            case R.id.btnMovieFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_MOVIES,"Nice Music");
                break;
            case R.id.btnPictureFolder:
                returnStorageDirectoryForFolderName(Environment.DIRECTORY_PICTURES,"nice picture");
        break;

            case R.id.btnSaveFile:
                letsSavetheFileTheDocumentFolder();
                break;
            case R.id.btnRetriveTheInfo:
                letsRetriveTheFileDataFromDocumentFolder();

        break;
            case R.id.imgAnimal:
                letsSaveTheImalgeToThePictureFolder();
                break;
        }
    }
}
