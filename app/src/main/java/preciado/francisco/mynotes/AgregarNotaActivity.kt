package preciado.francisco.mynotes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_agregar_nota.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class AgregarNotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        btnGuardar.setOnClickListener {
            guardarNota();
        }
    }

    private fun guardarNota() {
        if(ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                235
            )
        }else{
            guardar()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            235->{
                if((grantResults.isNotEmpty()&&
                            grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    guardar()
                }else{
                    Toast.makeText(this,"Error: permisos denegados", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun guardar() {
        var titulo=etTitulo.text.toString()
        var contenido=etContenido.text.toString()

        if(titulo.isEmpty()&&contenido.isEmpty()){
            Toast.makeText(this, "Error: campos vacíos", Toast.LENGTH_SHORT).show()
        }else{
            try{
                val archivo = File(ubicacion(), titulo + ".txt")
                val fos=FileOutputStream(archivo)
                fos.write(contenido.toByteArray())
                fos.close()
                Toast.makeText(this,
                    "La nota se ha guardado",
                    Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(this, "Error: no se guardó la nota", Toast.LENGTH_SHORT).show()
            }
        }
        onBackPressed()
    }

    private fun ubicacion(): File? {
        val carpeta = File(getExternalFilesDir(null), "notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta
    }

    override fun onBackPressed() {
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}