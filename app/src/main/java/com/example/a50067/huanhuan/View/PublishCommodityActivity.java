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
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a50067.huanhuan.BaseActivity;
import com.example.a50067.huanhuan.Presenter.MainFragPresenter;
import com.example.a50067.huanhuan.Presenter.PublishComPresenter;
import com.example.a50067.huanhuan.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;

public class PublishCommodityActivity extends BaseActivity implements IPublishcommodityView{

    private String TAG="PubComAC";
    private Toolbar toolbar;
    private EditText pubComTitle;
    private EditText pubComDetails;
    private EditText pubComPrice;
    private Button takePhotoBtn;
    private Button pickPhotoBtn;
    private Button publishBtn;
    private ImageView pubComImage;
    private Spinner exchangeableSpinner;
    private ArrayAdapter<String> exchange_adapter;
    private PublishComPresenter comPresenter;
    private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private MainFragPresenter mainFragPresenter;

    /** 定义相片的最大尺寸 **/
    private final int IMAGE_MAX_WIDTH = 50;
    private final int IMAGE_MAX_HEIGHT = 50;
    int imageScale=0;

    private String exchange;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_commodity);

        initView();
        initListener();


        comPresenter=new PublishComPresenter(this);


    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_publish_commodity;
    }

    @Override
    protected void initView() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.pubComAC));
        pubComTitle=(EditText)findViewById(R.id.pubCom_edit_Title);
        pubComDetails=(EditText)findViewById(R.id.pubCom_edit_details);
        pubComPrice=(EditText)findViewById(R.id.pubCom_price);
        takePhotoBtn=(Button)findViewById(R.id.pubCom_cameraAddImageBtn);
        pickPhotoBtn=(Button)findViewById(R.id.pubCom_photoAddImageBtn);
        publishBtn=(Button)findViewById(R.id.publish_button);
        pubComImage=(ImageView)findViewById(R.id.pubCom_image);
        exchangeableSpinner=(Spinner)findViewById(R.id.pubCom_exchangeable_spinner);
        exchange_adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.exchangeable));
        exchange_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exchangeableSpinner.setAdapter(exchange_adapter);
    }

    @Override
    protected void initListener() {
        takePhotoBtn.setOnClickListener(this);
        pickPhotoBtn.setOnClickListener(this);
        publishBtn.setOnClickListener(this);
        exchangeableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exchange=exchangeableSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public String getComTitle() {
          return pubComTitle.getText().toString();
    }

    @Override
    public String getComDetails() {
        return pubComDetails.getText().toString();
    }

    @Override
    public String getComPrice() {
        return pubComPrice.getText().toString();
    }

    @Override
    public void intentToMainAC() {
        startActivity(new Intent(PublishCommodityActivity.this,MainActivity.class));
        this.finish();
        Intent intent=new Intent("refresh_recyclerview_data");
        sendBroadcast(intent);
        toastShort(getResources().getString(R.string.publish_com_success));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pubCom_cameraAddImageBtn:
                if(ContextCompat.checkSelfPermission(PublishCommodityActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PublishCommodityActivity.this,new String[]{Manifest.permission.CAMERA},1);
                }
                else {
                    getImageUri();
                }
                break;
            case R.id.pubCom_photoAddImageBtn:
                if(ContextCompat.checkSelfPermission(PublishCommodityActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PublishCommodityActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }
                else {
                    openAlbum();
                }
                break;
            case R.id.publish_button:
                if(ETisEmpty(pubComDetails)||ETisEmpty(pubComPrice)||ETisEmpty(pubComTitle)||imageBitmap==null){
                    toastShort(getString(R.string.pubComIsEmpty));
                }else {
                    comPresenter.saveComMsg();
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
            pubComImage.setImageBitmap(bitmap);
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
            imageUri= FileProvider.getUriForFile(PublishCommodityActivity.this,"com.example.a50067.huanhuan.fileprovider",outputImage);
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
                        imageBitmap=bitmap;
                        pubComImage.setImageBitmap(bitmap);
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
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            imageBitmap=bitmap;
            pubComImage.setImageBitmap(bitmap);
        }else {
            toastShort("从相册加载图片失败");
        }
    }
    private int getZoomScale(File imageFile){
        int scale=1;
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(imageUri.getPath());
        while (options.outWidth/scale>=IMAGE_MAX_WIDTH||options.outHeight/scale>=IMAGE_MAX_HEIGHT){
            scale*=2;
        }
        return scale;
    }

    @Override
    public String getExchangeable() {
        return exchange;
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


}
