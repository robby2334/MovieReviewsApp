package com.dev.divig.moviereviewsapp.ui.main.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.ItemImageSliderBinding
import com.dev.divig.moviereviewsapp.databinding.ItemParentBinding
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.ui.main.movie.model.ParentEntity
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.SpacesItemDecoration
import com.dev.divig.moviereviewsapp.utils.Utils

class ParentItemAdapter(
    private val parentItemList: List<ParentEntity>,
    private val onClickShowAll: (ParentEntity) -> Unit,
    private val onClickItemChild: (MovieEntity) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) Constant.ITEM_TYPE_IMAGE_SLIDER else Constant.ITEM_TYPE_CHILD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.ITEM_TYPE_IMAGE_SLIDER) {
            val binding =
                ItemImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ImageSliderViewHolder(binding)
        } else {
            val binding =
                ItemParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChildViewHolder(binding, onClickShowAll, onClickItemChild)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is ImageSliderViewHolder) {
            holder.bindView(parentItemList[position])
        } else if (holder is ChildViewHolder) {
            holder.bindView(parentItemList[position])
        }
    }

    override fun getItemCount(): Int = parentItemList.size

    class ImageSliderViewHolder(
        private val binding: ItemImageSliderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: ParentEntity) {
            with(item) {
                val listNumber = Utils.randomNumber(Constant.FIVE)

                val imageList = ArrayList<SlideModel>().apply {
                    listNumber.forEach {
                        add(
                            SlideModel(
                                Constant.BASE_URL_IMAGE + childItemList[it].backdropPath,
                                ""
                            )
                        )
                    }
                }
                binding.imgSlider.setImageList(imageList, ScaleTypes.FIT)
                setTitleImgSlider(childItemList, listNumber, 0)

                binding.imgSlider.setItemChangeListener(object : ItemChangeListener {
                    override fun onItemChanged(position: Int) {
                        setTitleImgSlider(childItemList, listNumber, position)
                    }
                })

                binding.imgSlider.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                        DetailActivity.startActivity(
                            binding.root.context,
                            childItemList[listNumber[position]].id,
                            false
                        )
                    }
                })
            }
        }

        private fun setTitleImgSlider(
            listMovie: List<MovieEntity>,
            listNumber: List<Int>,
            position: Int
        ) {
            binding.tvImgTitle.text = listMovie[listNumber[position]].title
            binding.tvImgGenre.text = listMovie[listNumber[position]].genres
        }
    }

    class ChildViewHolder(
        private val binding: ItemParentBinding,
        private val onClickShowAll: (ParentEntity) -> Unit,
        private val onClickItemChild: (MovieEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: ParentEntity) {
            with(item) {
                binding.parentItemTitle.text = parentItemTitle
                val layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                val childItemAdapter = ChildItemAdapter {
                    onClickItemChild(it)
                }
                childItemAdapter.submitList(childItemList)
                binding.childRecyclerView.apply {
                    this.layoutManager = layoutManager
                    addItemDecoration(
                        SpacesItemDecoration(
                            Utils.dpToPixels(binding.root.context, 10)
                        )
                    )
                    adapter = childItemAdapter
                    setRecycledViewPool(RecycledViewPool())
                }

                binding.btnShowAll.setOnClickListener {
                    onClickShowAll(this)
                }
            }
        }
    }
}