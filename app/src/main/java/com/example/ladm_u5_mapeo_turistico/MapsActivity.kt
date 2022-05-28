package com.example.ladm_u5_mapeo_turistico

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ladm_u5_mapeo_turistico.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        puntos(googleMap)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
    }

    fun onMyLocationClick(location: MapsActivity) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }



    private fun puntos(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        val entrada = LatLng(21.508466445471086, -104.90163568089697)
        val marcadorEntrada = mMap.addMarker(
            MarkerOptions()
                .position(entrada)
                .title("ENTRADA ALAMEDA")
                .snippet(
                    "La entrada del parque se encuentra sobre la av. insurgentes " +
                            "y se conforma por una estructura algo similar a un acueducto pero no igual," +
                            " cruzando podemos encontrarnos con un monumento del símbolo de la bandera de México," +
                            " un águila devorando una serpiente."
                )
        )
        val aviario = LatLng(21.510869522670717, -104.90124724686727)
        val marcadorAviario = mMap.addMarker(
            MarkerOptions()
                .position(aviario)
                .title("AVIARIO")
                .snippet("Inaugurado en 1987, en el aviario de la alameda se preservan aves exóticas, el aviario se conforma de una serie de jaulas en las que podemos encontrar diversas aves y está abierto al público.")
        )

        val deportiva = LatLng(21.509262485167135, -104.9005520684651)
        val marcadorDeportiva = mMap.addMarker(
            MarkerOptions()
                .position(deportiva)
                .title("ZONA DEPORTIVA")
                .snippet("El parque Alameda cuenta con una zona dedicada a los deportes, para el público en general, podemos encontrar cancha de frontón, cancha de fútbol rápido, cancha de basquetball y gimnasio.")
        )

        val fuente = LatLng(21.510507938247414, -104.90051436508571)
        val marcadorFuente = mMap.addMarker(
            MarkerOptions()
                .position(fuente)
                .title("FUENTE CENTRAL")
                .snippet("La fuente del cisne se encuentra en el centro del parque y es donde se interseccionan todos los caminos del parque.")
        )

        val biblioteca = LatLng(21.51076471634501, -104.89958964175523)
        val marcadorBiblio = mMap.addMarker(
            MarkerOptions()
                .position(biblioteca)
                .title("BIBLIOTECA")
                .snippet("Con su renovación el 23 de junio del año 2021 la biblioteca pública ha vuelto a la vida siendo tomada en cuenta como primera zona que será mapeada en el proyecto.")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(entrada))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(entrada))
        mMap.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(entrada, 18f), 2000, null
        )

        this.mMap?.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val infoWindow = layoutInflater.inflate(R.layout.custom_info_contents,
                    findViewById<FrameLayout>(R.id.map), false)
                if(marker.title == "BIBLIOTECA") {
                    val imagen = infoWindow.findViewById<ImageView>(R.id.imagen)
                    imagen.setImageDrawable(getResources().getDrawable(R.drawable.biblio))
                }
                if(marker.title == "ENTRADA ALAMEDA") {
                    val imagen = infoWindow.findViewById<ImageView>(R.id.imagen)
                    imagen.setImageDrawable(getResources().getDrawable(R.drawable.entrada))
                }
                if(marker.title == "FUENTE CENTRAL") {
                    val imagen = infoWindow.findViewById<ImageView>(R.id.imagen)
                    imagen.setImageDrawable(getResources().getDrawable(R.drawable.fuente))
                }
                if(marker.title == "ZONA DEPORTIVA") {
                    val imagen = infoWindow.findViewById<ImageView>(R.id.imagen)
                    imagen.setImageDrawable(getResources().getDrawable(R.drawable.deportiva))
                }
                if(marker.title == "AVIARIO") {
                    val imagen = infoWindow.findViewById<ImageView>(R.id.imagen)
                    imagen.setImageDrawable(getResources().getDrawable(R.drawable.aviario))
                }
                val title = infoWindow.findViewById<TextView>(R.id.title)
                title.text = marker.title
                val snippet = infoWindow.findViewById<TextView>(R.id.snippet)
                snippet.text = marker.snippet
                return infoWindow
            }
        })
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Posición Actual:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Moviendose a tu posición actual", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }
}