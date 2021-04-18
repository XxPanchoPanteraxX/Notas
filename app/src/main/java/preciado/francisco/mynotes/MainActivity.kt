package preciado.francisco.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    var lstNotas=ArrayList<Nota>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leerNotas()

        var adapter = AdaptadorNotas(lstNotas, this)

        lvNotas.adapter=adapter


        btnAgregarNota.setOnClickListener{
            var intent= Intent(this,AgregarNotaActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun leerNotas(){
        lstNotas.clear()

        var carpeta = File(ubicacion().absolutePath)

        if(carpeta.exists()){
            var archivos=carpeta.listFiles()
            if(archivos!=null){
                archivos.forEach {
                    leerArchivo(it)
                }
            }
        }
    }

    private fun leerArchivo(it: File?) {
        val fis=FileInputStream(it)
        val di=DataInputStream(fis)
        val br=BufferedReader(InputStreamReader(di))

        var strLine: String? = br.readLine()
        var contenido=""

        while(strLine!=null){
            contenido+=strLine
            strLine=br.readLine()
        }

        br.close()
        di.close()
        fis.close()

        var titulo=it!!.name.substring(0, it!!.name.length-4)

        var nota=Nota(titulo, contenido)
        lstNotas.add(nota)
    }

    private fun ubicacion(): File {
        val carpeta = File(getExternalFilesDir(null), "notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta
    }

    private fun notasDePrueba() {
        lstNotas.add(Nota("Tarea","Investigación sobre las bases de datos en kotlin"))
        lstNotas.add(Nota("Pruebas","Ejecutar pruebas de software a la aplicación"))
        lstNotas.add(Nota("Ensayo","Elaborar un ensayo sobre la colaboración entre diseñadores y programadores"))
    }
}