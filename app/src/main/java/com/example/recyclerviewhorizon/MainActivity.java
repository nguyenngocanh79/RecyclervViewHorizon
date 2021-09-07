package com.example.recyclerviewhorizon;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRcvFood1, mRcvFood2;
    List<Food> mListFood1, mListFood2;
    FoodAdapter mFoodAdapter1, mFoodAdapter2;
    int mCurrentPage1 = 1;
    int mTotalPage1 = 3;
    int mCurrentPage2 = 1;
    int mTotalPage2 = 4;
    boolean mLoading1 = false;
    boolean mLastPage1 = false;
    boolean mLoading2 = false;
    boolean mLastPage2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRcvFood1 = findViewById(R.id.recyclerView1);
        mRcvFood2 = findViewById(R.id.recyclerView2);
        mListFood1 = Food.getMock();
        mListFood2 = Food.getMock();
        //Add Loading item footer ở trang đầu
        mFoodAdapter1 = new FoodAdapter(mListFood1);
        mFoodAdapter2 = new FoodAdapter(mListFood2);
        if (mCurrentPage1 < mTotalPage1){
            mFoodAdapter1.addFooterLoading();
        }else{
            mLastPage1 = true;
        }
        if (mCurrentPage2 < mTotalPage2){
            mFoodAdapter2.addFooterLoading();
        }else{
            mLastPage2 = true;
        }
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRcvFood1.setLayoutManager(layoutManager1);
        mRcvFood1.setHasFixedSize(true);
        mRcvFood1.setAdapter(mFoodAdapter1);

        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRcvFood2.setLayoutManager(layoutManager2);
        mRcvFood2.setHasFixedSize(true);
        mRcvFood2.setAdapter(mFoodAdapter2);

        FoodRecyclerView foodRecyclerView = new FoodRecyclerView(findViewById(R.id.recyclerView3),
                Food.getMock(), 1, 3, false, false);
        foodRecyclerView.recyclerViewShow(this);
        foodRecyclerView.deleteItemListener(this);
        foodRecyclerView.recyclerViewScrollListener();

        //Delete item
        mFoodAdapter1.setOnItemListener(new OnItemListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //Giao diện
                builder.setTitle("DELETE ITEM");
                builder.setMessage("Are you sure to delete this item?");
                builder.setIcon(R.mipmap.ic_launcher);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListFood1.remove(mListFood1.get(position));
                        mFoodAdapter1.notifyItemRemoved(position);
                        Toast.makeText(MainActivity.this, "One item deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                //Negative button
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


//                mFoodAdapter.notifyItemRangeRemoved(position, mListFood.size());

            }
        });


        mRcvFood1.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager) mRcvFood1.getLayoutManager()) {
            @Override
            public void loadMoreItem() {
                mLoading1 = true;
                mCurrentPage1 +=1;

                loadNextPage1();
            }

            @Override
            public boolean isLoading() {
                return mLoading1;
            }

            @Override
            public boolean isLastPage() {
                return mLastPage1;
            }
        });

        mRcvFood2.addOnScrollListener(new PaginationScrollListener((LinearLayoutManager) mRcvFood2.getLayoutManager()) {
            @Override
            public void loadMoreItem() {
                mLoading2 = true;
                mCurrentPage2 +=1;

                loadNextPage2();
            }

            @Override
            public boolean isLoading() {
                return mLoading2;
            }

            @Override
            public boolean isLastPage() {
                return mLastPage2;
            }
        });
    }


    private void loadNextPage1(){
        new CountDownTimer(2000 , 2000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Log.d("BBB","Trang: " + mCurrentPage1 + "");
                if (mCurrentPage1 > 1){
                    mFoodAdapter1.removeLoading();
                }
                mListFood1.addAll(Food.getMock());
                mFoodAdapter1.notifyDataSetChanged();
                mLoading1 = false;

                if (mCurrentPage1 < mTotalPage1){
                    mFoodAdapter1.addFooterLoading();
                }else{
                    mLastPage1 = true;
                }

            }
        }.start();

    }
    private void loadNextPage2() {
        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Log.d("BBB", "Trang: " + mCurrentPage2 + "");
                if (mCurrentPage2 > 1) {
                    mFoodAdapter2.removeLoading();
                }
                mListFood2.addAll(Food.getMock());
                mFoodAdapter2.notifyDataSetChanged();
                mLoading2 = false;

                if (mCurrentPage2 < mTotalPage2) {
                    mFoodAdapter2.addFooterLoading();
                } else {
                    mLastPage2 = true;
                }

            }
        }.start();
    }
}