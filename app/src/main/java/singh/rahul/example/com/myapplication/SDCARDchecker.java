package singh.rahul.example.com.myapplication;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class SDCARDchecker {

    public static void checkWeatherExternalStorageAvailableOrNot(Context context)
    {
        boolean isExternaStorageIsAvilable = false;
        boolean isExternalStorageIsWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            isExternaStorageIsAvilable = isExternalStorageIsWriteable = true;
            Toast.makeText(context,"read and write",Toast.LENGTH_SHORT).show();
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            isExternaStorageIsAvilable=true;
            isExternalStorageIsWriteable=false;
            Toast.makeText(context,"read only",Toast.LENGTH_SHORT).show();
        }
else
        {
            isExternalStorageIsWriteable=false;
            isExternaStorageIsAvilable=false;
            Toast.makeText(context,"nither read nither write",Toast.LENGTH_SHORT).show();
        }
    }
}
