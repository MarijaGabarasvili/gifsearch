package gifsearch2.com

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class GifAdapter(val context: Context, val gif: List<DataObject>) : RecyclerView.Adapter<GifAdapter.ViewHolder>() {

     lateinit var mListener: OnItemClickListener
      lateinit var  listener: OnItemClickListener


    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false), mListener)
    }

    override fun getItemCount(): Int {
        return gif.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gif[position]

        Glide.with(context).load(data.images.ogImage.url).into(holder.imageView)
    }



    class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.ivGif)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

}
