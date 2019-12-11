package com.njxm.smart.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.njxm.smart.activities.adapter.SimpleImageAdapter;
import com.ns.demo.R;

import java.util.ArrayList;
import java.util.List;


public class MedicalReportActivity extends BaseActivity {


    private SimpleImageAdapter adapter;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_medical_report;
    }

    private RecyclerView mRecyclerView;
    public static final int default_icon = R.mipmap.edt_icon_pwd_verify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("体检报告");
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mRecyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(layoutManager);

        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getDrawable(default_icon));

        adapter = new SimpleImageAdapter(drawables);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == adapter.getData().size() - 1) {
                    Intent starter = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(starter, 100);
                }
            }
        });


        mRecyclerView.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            adapter.getData().add(0, new BitmapDrawable(getResources(), bitmap));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onClickLeftBtn();
        finish();
    }
}
