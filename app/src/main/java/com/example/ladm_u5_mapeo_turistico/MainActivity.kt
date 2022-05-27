package com.example.ladm_u5_mapeo_turistico

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ladm_u5_mapeo_turistico.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var baseRemota = FirebaseFirestore.getInstance()
    var posicion =ArrayList<Data>()
    lateinit var locacion : LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
        }


        baseRemota.collection("Alameda")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(firebaseFirestoreException != null){
                    binding.textView.setText("ERROR:"+firebaseFirestoreException.message)
                    return@addSnapshotListener
                }

                var resultado = ""
                posicion.clear()
                for(document in querySnapshot!!){
                    var data = Data()
                    data.nombre = document.getString("nombre").toString()
                    data.posicion1 = document.getGeoPoint("posicion1")!!
                    data.posicion2 = document.getGeoPoint("posicion2")!!

                    resultado += data.toString()+"\n\n"
                    posicion.add(data)
                }

                binding.textView.setText(resultado)
            }

        binding.button.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        locacion = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var oyente = Oyente(this)
        locacion.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            0,01f, oyente)
    }
}

class Oyente(puntero:MainActivity) : LocationListener {
    var p = puntero

    override fun onLocationChanged(location: Location) {
        p.binding.textView2.setText("${location.latitude}, ${location.longitude}")
        p.binding.textview3.setText("")
        var geoPosicionGPS = GeoPoint(location.latitude,location.longitude)

        for (item in p.posicion){
            if(item.estoyEn(geoPosicionGPS)){
                p.binding.textview3.setText("Estas en ${item.nombre}")
            }
        }
    }
    override fun onStatusChanged(provider: String?, status: Int,extras: Bundle?) {

    }
    override fun onProviderEnabled(provider: String) {

    }
    override fun onProviderDisabled(provider: String) {

    }

}