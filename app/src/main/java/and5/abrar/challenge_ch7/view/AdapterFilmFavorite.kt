package and5.abrar.challenge_ch7.view


import and5.abrar.challenge_ch7.room.Favorite
import and5.abrar.challenge_ch7.room.FavoriteDatabase
import and5.abrar.challenge_ch7.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_film_fav.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Suppress("DeferredResultUnused")
class AdapterFilmFavourite(private val listFilmFavourite: List<Favorite>,
                           private var onClik : (Favorite)->Unit
) : RecyclerView.Adapter<AdapterFilmFavourite.ViewHolder>() {
    private var filmDb: FavoriteDatabase? = null
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_film_fav, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cardfav.setOnClickListener {
            onClik(listFilmFavourite[position])
        }
        this.let {
            Glide.with(holder.itemView.context).load(listFilmFavourite[position].image).into(holder.itemView.iv_filmimage)
        }
        holder.itemView.tv_filmdirector.text = listFilmFavourite[position].director
        holder.itemView.tv_filmtitle.text = listFilmFavourite[position].title
        holder.itemView.del_fav.setOnClickListener {
            filmDb = FavoriteDatabase.getinstance(it.context)
            AlertDialog.Builder(it.context)
                .setTitle("Hapus data")
                .setMessage("yakin hapus data")
                .setPositiveButton("ya"){
                        _: DialogInterface, _: Int ->
                    GlobalScope.async {
                        val result = filmDb?.favoriteduo()?.deletefavorite(listFilmFavourite[position])
                        (holder.itemView.context as FavoriteActivity).runOnUiThread{
                            if (result != 0){
                                Toast.makeText(it.context, "data ${listFilmFavourite[position].title} dihapus",
                                    Toast.LENGTH_LONG).show()
                                (holder.itemView.context as FavoriteActivity).getFilmFav()
                            }else{
                                Toast.makeText(it.context, "gagal di hapus", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Tidak"){
                        dialogInterface : DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
        return listFilmFavourite.size
    }
}