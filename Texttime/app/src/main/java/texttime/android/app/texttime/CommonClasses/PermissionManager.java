package texttime.android.app.texttime.CommonClasses;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by Dinesh on 7/30/2016.
 */
public class PermissionManager {

    Activity activity;
    public String[] FIRSTPAGEPERMISSIONS={Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
    public String[] SECONDPAGEPERMISSIONS={Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public PermissionManager(Activity activity) {
        this.activity = activity;
    }


    public boolean checkPermission(String permision){
        int res = ContextCompat.checkSelfPermission(activity,permision);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    public boolean checkLocationPermission(){
        int res = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    @SuppressLint("NewApi")
    public void getLocationPermission(){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED){
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionCode.PERMISSIONACCESSLOCATION);
        }
    }

    @SuppressLint("NewApi")
    public void getPermission(String permision, int code){
        if(ContextCompat.checkSelfPermission(activity, permision)!= PackageManager.PERMISSION_GRANTED){
            activity.requestPermissions(new String[]{permision},code);
        }
    }


    @SuppressLint("NewApi")
    public void getPermissionCollection(String permision[], int code){
        ArrayList<String> permissionRequest=new ArrayList<>();
        if(permision.length>0){
            for(int i=0;i<permision.length;i++){
                if(ContextCompat.checkSelfPermission(activity, permision[i])!= PackageManager.PERMISSION_GRANTED){
                    permissionRequest.add(permision[i]);
                }
            }
        }

        if(permissionRequest.size()>0){
            String[] mStringArray = new String[permissionRequest.size()];
            mStringArray = permissionRequest.toArray(mStringArray);
            activity.requestPermissions(mStringArray,code);
        }

    }


    public boolean checkPermission(String permision[]){
        ArrayList<String> permissionRequest=new ArrayList<>();
        if(permision.length>0){
            for(int i=0;i<permision.length;i++){
                if(ContextCompat.checkSelfPermission(activity, permision[i])!= PackageManager.PERMISSION_GRANTED){
                    permissionRequest.add(permision[i]);
                }
            }
        }

        if(permissionRequest.size()>0){
           return false;
        }

        return true;
    }

}
