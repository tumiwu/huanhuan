package com.example.a50067.huanhuan.View;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.Presenter.SettingACPresenter;
import com.example.a50067.huanhuan.R;
import com.example.a50067.huanhuan.Utility.CircleImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingActivity extends BaseActivity implements ISettingView{
    private String TAG="setting AC";
    private CircleImageView userIcon;    //pubComImage 对应
    private Button takePhotoBtn;
    private Button pickPhotoBtn;
    private Button saveBtn;
    private Toolbar toolbar;
    private EditText userTel;
    private EditText userAddress;
    private Uri imageUri;
    private Bitmap imageBitmap;

    private SettingACPresenter settingACPresenter;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        settingACPresenter=new SettingACPresenter(this);
    }


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setContentView(getRootLayoutId());
        userIcon=(CircleImageView)findViewById(R.id.settingAC_userIcon);
        takePhotoBtn=(Button)findViewById(R.id.settingAC_cameraAddImageBtn);
        pickPhotoBtn=(Button)findViewById(R.id.settingAC_photoAddImageBtn);
        saveBtn=(Button)findViewById(R.id.settingAC_saveBtn);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        userTel=(EditText)findViewById(R.id.settingAC_userTel);
        userAddress=(EditText)findViewById(R.id.settingAC_userAddress);
        toolbar.setTitle(getString(R.string.settingAC_toolbar));
        setEditTextInhibitInputSpeChat(userAddress);



    }

    @Override
    protected void initListener() {
        takePhotoBtn.setOnClickListener(this);
        pickPhotoBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settingAC_cameraAddImageBtn:
                if(ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SettingActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }
                else {
                    getImageUri();
                }
                break;
            case R.id.settingAC_photoAddImageBtn:
                if(ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SettingActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }
                else {
                    openAlbum();
                }
                break;
            case R.id.settingAC_saveBtn:
                if(ETisEmpty(userTel)||ETisEmpty(userAddress)){
                    toastShort(getString(R.string.settingAC_input_failed));
                }else {
                    settingACPresenter.saveUserMsg();
                }
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getImageUri();
                }else {
                    toastShort("你拒绝了该权限");
                }
                break;
            case 2:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    toastShort("你拒绝了该权限");
                }
            default:
                break;
        }
    }
    @Override
    public void setImage(Uri uri) {
        try{
            Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            userIcon.setImageBitmap(bitmap);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public void getImageUri(){
        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(SettingActivity.this,"com.example.a50067.huanhuan.fileprovider",outputImage);
        }else {
            imageUri=Uri.fromFile(outputImage);
        }
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try{
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        Bitmap bitmap1=compressBitmapUri(bitmap);
                        imageBitmap=bitmap1;
                        userIcon.setImageBitmap(bitmap1);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    handleImageOnKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri1=data.getData();
        Uri uri=Uri.parse(uri1.toString());
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap=compressBitmap(imagePath);
            imageBitmap=bitmap;
            userIcon.setImageBitmap(bitmap);
        }else {
            toastShort("从相册加载图片失败");
        }
    }
    @Override
    public Bitmap getBitmap() {
        return imageBitmap;
    }
    public Boolean ETisEmpty(EditText editText){
        if(TextUtils.isEmpty(editText.getText().toString())){
            return true;
        }else return false;

    }

    @Override
    public String getUserTel() {
        return userTel.getText().toString();
    }

    @Override
    public String getUserAddress() {
        return userAddress.getText().toString();
    }

    @Override
    public void intentToMainAC() {
        openActivity(MainActivity.class);
        toastShort(getString(R.string.settingAC_input_success));
        this.finish();
    }

    @Override
    public void insertFaild() {
        toastShort(getString(R.string.settingAC_input_failed));
    }
    /**
     * 禁止EditText输入特殊字符
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText){

        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    private Bitmap compressBitmap(String path){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);
        int height=options.outHeight;
        int width=options.outWidth;
        int inSampleSize=2;
        int minLen=Math.min(height,width);
        if(minLen>100){
            float ratio=(float)minLen/100.0f;
            inSampleSize=(int)ratio;
        }
        options.inJustDecodeBounds=false;
        options.inSampleSize=inSampleSize;
        Bitmap bm=BitmapFactory.decodeFile(path,options);
        return bm;

    }

    private Bitmap compressBitmapUri(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap b = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return b;
    }
}
