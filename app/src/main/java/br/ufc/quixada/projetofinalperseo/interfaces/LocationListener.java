package br.ufc.quixada.projetofinalperseo.interfaces;

import android.location.Location;
import android.os.Bundle;

public interface LocationListener extends android.location.LocationListener {
    void onLocationChanged(Location var1);

    void onStatusChanged(String var1, int var2, Bundle var3);

    void onProviderEnabled(String var1);

    void onProviderDisabled(String var1);
}
