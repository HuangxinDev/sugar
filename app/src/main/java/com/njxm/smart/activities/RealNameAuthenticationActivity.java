package com.njxm.smart.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.ns.demo.R;

public class RealNameAuthenticationActivity extends BaseActivity {

    private AppCompatTextView mCardId;

    public static final int ACTION_CAPTURE = 100;

    @Override
    protected int setContentLayoutId() {
        return R.layout.my_realname_authentication;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarTitle(getIntent().getStringExtra("title"));
        showLeftBtn(true, R.mipmap.arrow_back_blue);

        mCardId = findViewById(R.id.card_id_face);

        mCardId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == mCardId) {
            start(this);
        }
    }

    public void start(Context context) {
        Intent starter = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String f = System.currentTimeMillis() + ".jpg";

//        File dir =
//                new File(Environment.getExternalStoragePublicDirectory("").getPath() + "/Android" +
//                        "/data/" + getPackageName());
//
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        Uri fUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider",
//                new File(dir, f));

//        Uri fileUri =
//                Uri.fromFile(new File(dir, f));
//        starter.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
        startActivityForResult(Intent.createChooser(starter, "拍照"), ACTION_CAPTURE);
    }

    public void pickImage(Context context) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_CAPTURE) {
            showToast("bitmap return");
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                mCardId.setCompoundDrawablesWithIntrinsicBounds(null,
                        new BitmapDrawable(getResources(), bitmap), null, null);
            }
        }
    }

    @Override
    public void onClickLeftBtn() {
        super.onBackPressed();
    }
}
