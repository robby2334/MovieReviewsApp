package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import com.dev.divig.moviereviewsapp.databinding.ActivityBottomSheetReviewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetReviewActivity :
    BaseActivity<ActivityBottomSheetReviewBinding, BottomSheetActivityContract.Presenter>(
        ActivityBottomSheetReviewBinding::inflate),BottomSheetActivityContract.View {
    private lateinit var binding: ActivityBottomSheetReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomSheetReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BottomSheetBehavior.from(binding.btsReview).apply {
            peekHeight = 400
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.tilReview.setEndIconOnClickListener {
            Toast.makeText(this, "Send Review", Toast.LENGTH_SHORT).show()
        }
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initPresenter() {
        TODO("Not yet implemented")
    }

    override fun getData() {
        TODO("Not yet implemented")
    }

    override fun onDataCallback(response: Resource<List<ReviewEntity>>) {
        TODO("Not yet implemented")
    }

    override fun initList() {
        TODO("Not yet implemented")
    }

    override fun setListData(data: List<ReviewEntity>) {
        TODO("Not yet implemented")
    }
}