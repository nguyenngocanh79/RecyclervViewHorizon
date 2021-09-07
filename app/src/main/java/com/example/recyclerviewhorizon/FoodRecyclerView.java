package com.example.recyclerviewhorizon;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodRecyclerView {
    private RecyclerView mRcvFood;
    private List<Food> mListFood;
    private FoodAdapter mFoodAdapter;
    private int mCurrentPage = 1;
    private int mTotalPage;
    private boolean mLoading = false;
    private boolean mLastPage = false;

    public FoodRecyclerView(RecyclerView mRcvFood, List<Food> mListFood,
                            int mCurrentPage, int mTotalPage, boolean mLoading, boolean mLastPage) {
        this.mRcvFood = mRcvFood;
        this.mListFood = mListFood;
        this.mCurrentPage = mCurrentPage;
        this.mTotalPage = mTotalPage;
        this.mLoading = mLoading;
        this.mLastPage = mLastPage;
    }

    public RecyclerView getmRcvFood() {
        return mRcvFood;
    }

    public void setmRcvFood(RecyclerView mRcvFood) {
        this.mRcvFood = mRcvFood;
    }

    public List<Food> getmListFood() {
        return mListFood;
    }

    public void setmListFood(List<Food> mListFood) {
        this.mListFood = mListFood;
    }

    public FoodAdapter getmFoodAdapter() {
        return mFoodAdapter;
    }

    public void setmFoodAdapter(FoodAdapter mFoodAdapter) {
        this.mFoodAdapter = mFoodAdapter;
    }

    public int getmCurrentPage() {
        return mCurrentPage;
    }

    public void setmCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    public int getmTotalPage() {
        return mTotalPage;
    }

    public void setmTotalPage(int mTotalPage) {
        this.mTotalPage = mTotalPage;
    }

    public boolean ismLoading() {
        return mLoading;
    }

    public void setmLoading(boolean mLoading) {
        this.mLoading = mLoading;
    }

    public boolean ismLastPage() {
        return mLastPage;
    }

    public void setmLastPage(boolean mLastPage) {
        this.mLastPage = mLastPage;
    }

    void recyclerViewShow(Context context){
        //Add Loading item footer ở trang đầu
        mFoodAdapter = new FoodAdapter(mListFood);
        if (mCurrentPage < mTotalPage){
            mFoodAdapter.addFooterLoading();
        }else{
            mLastPage = true;
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRcvFood.setLayoutManager(layoutManager);
        mRcvFood.setHasFixedSize(true);
        mRcvFood.setAdapter(mFoodAdapter);
    }

    //Delete Item
    void deleteItemListener(Context context){
        mFoodAdapter.setOnItemListener(new OnItemListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                //Giao diện
                builder.setTitle("DELETE ITEM");
                builder.setMessage("Are you sure to delete this item?");
                builder.setIcon(R.mipmap.ic_launcher);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListFood.remove(mListFood.get(position));
                        mFoodAdapter.notifyItemRemoved(position);
                        Toast.makeText(context, "One item deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                //Negative button
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


//                mFoodAdapter.notifyItemRangeRemoved(position, mListFood.size());

            }
        });
    }

    //Scroll Loadmore
    void recyclerViewScrollListener(){
        mRcvFood.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager) mRcvFood.getLayoutManager()) {
            @Override
            public void loadMoreItem() {
                mLoading = true;
                mCurrentPage +=1;

                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return mLoading;
            }

            @Override
            public boolean isLastPage() {
                return mLastPage;
            }
        });
    }
    private void loadNextPage() {
        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Log.d("BBB", "Trang: " + mCurrentPage + "");
                if (mCurrentPage > 1) {
                    mFoodAdapter.removeLoading();
                }
                mListFood.addAll(Food.getMock());
                mFoodAdapter.notifyDataSetChanged();
                mLoading = false;

                if (mCurrentPage < mTotalPage) {
                    mFoodAdapter.addFooterLoading();
                } else {
                    mLastPage = true;
                }

            }
        }.start();
    }

}
