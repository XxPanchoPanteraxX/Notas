package preciado.francisco.mynotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.nota_layout.view.*
import java.io.File
import java.lang.Exception

class AdaptadorNotas : BaseAdapter {

    var lstNotas = ArrayList<Nota>()
    var context : Context? = null

    constructor(lstNotas: ArrayList<Nota>, context: Context?) : super() {
        this.lstNotas = lstNotas
        this.context = context
    }

    override fun getCount(): Int {
        return lstNotas.size
    }

    override fun getItem(p0: Int): Any {
        return lstNotas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var nota=lstNotas[p0]

        var inflater = LayoutInflater.from(context)
        var vista = inflater.inflate(R.layout.nota_layout, null)

        vista.tvTitulo.setText(nota.titulo)
        vista.tvContenido.setText(nota.contenido)
        vista.btnEliminar.setOnClickListener{
            eliminar(nota.titulo)
            lstNotas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    private fun eliminar(titulo: String) {
        if(titulo.isEmpty()){
            Toast.makeText(context, "Error: título vacío", Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archivo= File(ubicacion(), titulo+".txt")
                archivo.delete()
                Toast.makeText(context, "Se eliminó la nota", Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(context,  "Error: no se pudo eliminar la nota", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ubicacion(): File? {
        val carpeta = File(context?.getExternalFilesDir(null), "notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }
        return carpeta
    }
}