package com.lpb.lifecardsdk.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;



public class LocationUtil {
    public interface LocationCallback {
        void onLocationChanged(GPSCoordinates location);
    }

    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager
                    .requestSingleUpdate(criteria, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location == null) {
                        Log.e( "requestLocation: ", "onFail");
                        return;
                    }
                    callback.onLocationChanged(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.e( "requestLocation: ", "onStatusChanged");
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.e( "requestLocation: ", "onProviderEnabled");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.e( "requestLocation: ", "onProviderDisabled");
                }
            }, null);
        } else if (isNetworkEnabled) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestSingleUpdate(criteria, new LocationListener() {
                public void onLocationChanged(Location location) {
                    if (location == null) {
                        Log.e( "requestLocation: ", "onFail");
                        return;
                    }
                    callback.onLocationChanged(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.e( "requestLocation: ", "onStatusChanged");
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.e( "requestLocation: ", "onProviderEnabled");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.e( "requestLocation: ", "onProviderDisabled");
                }
            }, null);
        }
    }


    // consider returning Location instead of this dummy wrapper class
    public static class GPSCoordinates {
        public double longitude;
        public double latitude;

        public GPSCoordinates(double theLatitude, double theLongitude) {
            longitude = theLongitude;
            latitude = theLatitude;
        }
    }

}
