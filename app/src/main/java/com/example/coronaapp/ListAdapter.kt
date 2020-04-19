import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coronaapp.R
import com.example.coronaapp.databinding.LayoutListHeaderSampleBinding
import com.example.coronaapp.databinding.LayoutListItemBinding
import com.example.coronaapp.network.Property

enum class RowType {
    HEADER,
    ROW
}


class ListAdapter(private val list: ArrayList<Property>) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//
//        val layoutInflater = LayoutInflater.from(parent.context)
//
//        val inflatedView: View = when (viewType) {
//            RowType.ROW.ordinal -> layoutInflater.inflate(R.layout.layout_list_item, parent, false)
//            else -> layoutInflater.inflate(R.layout.layout_list_header_sample, parent, false)
//        }
//        return MyViewHolder(inflatedView)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == RowType.ROW.ordinal) {
            val binding = DataBindingUtil.inflate<LayoutListItemBinding>(
                inflater,
                R.layout.layout_list_item,
                parent,
                false
            )
            return MyViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<LayoutListHeaderSampleBinding>(
                inflater,
                R.layout.layout_list_header_sample,
                parent,
                false
            )
            return MyViewHolder(binding)
        }
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//    val inflater = LayoutInflater.from(parent.context)
//        val binding = LayoutListItemBinding.inflate(inflater)
//        return MyViewHolder(inflater)
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//                val inflater = LayoutInflater.from(parent.context)
//        var binding?= = null
//        if(viewType == RowType.ROW.ordinal) {
//            var binding = LayoutListItemBinding.inflate(inflater)
//        } else {
//            var binding = LayoutListHeaderSampleBinding.inflate(inflater)
//        }
//        return MyViewHolder(binding)
//
//    }


    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val property = list[position]

        if (holder.itemViewType == RowType.HEADER.ordinal) {
            holder.bindHeader(property)
        } else {
            holder.bindItem(property)
        }

//        holder.itemView.setOnClickListener {
//            println("Pressed at row $position")
//        }
    }


    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return RowType.HEADER.ordinal
        } else {
            return RowType.ROW.ordinal
        }
    }


//    inner class MyViewHolder4(private val binding: LayoutListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        private var headerBinding: LayoutListHeaderSampleBinding? = null
//        private var regularItemBinding: LayoutListItemBinding? = null
//
//        constructor(binding: LayoutListHeaderSampleBinding) : super(binding.root) {
//            headerBinding = binding
//        }
//
//        fun bindHeader(item: RecyclerViewContainer) {
//            binding.props = item.property
//            binding.executePendingBindings()
//        }
//
//        fun bindItem(item: RecyclerViewContainer) {
//            binding.props = item.property
//            binding.executePendingBindings()
//        }
//    }


    class MyViewHolder : RecyclerView.ViewHolder {
        //        private var headerBinding: LayoutListHeaderSampleBinding? = null
//        private var regularItemBinding: LayoutListItemBinding? = null
        private lateinit var regularItemBinding: LayoutListItemBinding
        private lateinit var headerBinding: LayoutListHeaderSampleBinding


        constructor(binding: LayoutListHeaderSampleBinding) : super(binding.root) {
            headerBinding = binding
        }

        constructor(binding: LayoutListItemBinding) : super(binding.root) {
            regularItemBinding = binding
        }

        fun bindHeader(item: Property) {
            headerBinding.props = item
            headerBinding.executePendingBindings()
        }

        fun bindItem(item: Property) {
            regularItemBinding.props = item
            regularItemBinding.executePendingBindings()
        }
    }


}
