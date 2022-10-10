package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;


public class Poisk extends AppCompatActivity {
    Connection connection;
    String ConnectionResult = "";
    ImageView ImgP;
    String Poisk;
    String ImgPoisk="";
    public final int[] i = {0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisk);
        configureBackButton();
    }

    private void configureBackButton() {
        Button Back = (Button) findViewById(R.id.Backk);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

        public void GetTextFromSql(View a) {
            TextView IDPoisk = findViewById(R.id.IDPoisk);
            TextView NamePoisk = findViewById(R.id.NamePoisk);
            TextView SurPoisk = findViewById(R.id.SurPoisk);
            ImageView ImgP = findViewById(R.id.ImgP);
            TextView poisk=findViewById(R.id.Poiskov);
            Poisk=poisk.getText().toString();
            try {
                ConectionHellper conectionHellper = new ConectionHellper();
                connection = conectionHellper.connectionClass();

                if (connection != null) {

                    String query3 = "select count(Name_id) from Student where Name LIKE '"+Poisk+"%'";
                    Statement statement3 = connection.createStatement();
                    ResultSet resultSet3= statement3.executeQuery(query3);
                    int c = 0;
                    while (resultSet3.next()) {
                        c = resultSet3.getInt(1);
                    }
                    if (i[0] != 1) {
                        i[0] = i[0] - 1;
                    }
                    String query100 = "select Name_id from Student where Name LIKE '"+Poisk+"%'";
                    Statement statement100 = connection.createStatement();
                    ResultSet resultSet100 = statement100.executeQuery(query100);
                    int[] index= new int[c];
                    int b=0;
                    while (resultSet100.next()) {
                        index[b] = resultSet100.getInt(1);
                        b++;
                    }
                    b=0;
                    String query4 = "Select * From Student where Name_id=" + index[b] + "";
                    Statement statement4 = connection.createStatement();
                    ResultSet resultSet4 = statement4.executeQuery(query4);
                    while (resultSet4.next()) {
                        IDPoisk.setText(resultSet4.getString(1));
                        NamePoisk.setText(resultSet4.getString(2));
                        SurPoisk.setText(resultSet4.getString(3));
                        ImgPoisk = (resultSet4.getString(4));
                        ImgP.setImageBitmap(getImgBitmap(ImgPoisk));
                    }
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {

            }

        }

        private Bitmap getImgBitmap(String encodedImg) {
            if (encodedImg != null && !encodedImg.equals("null")) {
                byte[] bytes = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    bytes = Base64.getDecoder().decode(encodedImg);
                }
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
            return BitmapFactory.decodeResource(com.example.myapplication.Poisk.this.getResources(),
                    R.drawable.no_photo);
        }
        public void onClickChooseImage(View view)
        {
            getImage();

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==1 && data!= null && data.getData()!= null)
            {
                if(resultCode==RESULT_OK)
                {
                    Log.d("MyLog","Image URI : "+data.getData());
                    ImgP.setImageURI(data.getData());
                    Bitmap bitmap = ((BitmapDrawable)ImgP.getDrawable()).getBitmap();
                    encodeImage(bitmap);

                }
            }
        }

        private void getImage()
        {
            Intent intentChooser= new Intent();
            intentChooser.setType("image/*");
            intentChooser.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentChooser,1);
        }

        private String encodeImage(Bitmap bitmap) {
            int prevW = 150;
            int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
            Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ImgPoisk=Base64.getEncoder().encodeToString(bytes);
                return ImgPoisk;
            }
            return "";
        }
}