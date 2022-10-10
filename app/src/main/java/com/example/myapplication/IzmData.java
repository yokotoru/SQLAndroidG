package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class IzmData extends AppCompatActivity {
    Connection connection;
    String ConnectionResult = "";
    private ImageView imageButton;
    TextView NameIzm;
    TextView SurnameIzm;
    ImageView imageView;
    String Img="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izm_data);
        imageButton=findViewById(R.id.imageIzm);
        configureBackButton();
        NameIzm = findViewById(R.id.SurCh);
        SurnameIzm = findViewById(R.id.IzmName);
        imageView=findViewById(R.id.imageIzm);
        Intent intent = getIntent();
        String Base = intent.getStringExtra("Number");
        String Gposition=intent.getStringExtra("Gposition");
        String Image=intent.getStringExtra("Image");
        NameIzm.setText(Base);
        SurnameIzm.setText(Gposition);
        imageView.setImageBitmap(getImgBitmap(Image));
    }
    public void onClickChooseImage(View view)
    {
        getImage();

    }
    private Bitmap getImgBitmap(String encodedImg) {
        if (encodedImg != null && !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(IzmData.this.getResources(),
                R.drawable.no_photo);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageButton.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageButton.getDrawable()).getBitmap();
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
            Img= Base64.getEncoder().encodeToString(bytes);
            return Img;
        }
        return "";
    }
    private void configureBackButton() {
        Button back = (Button) findViewById(R.id.cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void IzmTextFromSql(View v) {

        String Bases = NameIzm.getText().toString();
        String Positions = SurnameIzm.getText().toString();
        try {
            ConectionHellper conectionHellper = new ConectionHellper();
            connection = conectionHellper.connectionClass();
            Intent intent = getIntent();
            String Base = intent.getStringExtra("base");
            if (connection != null) {
                String query11 = "select Name_id from Student where Name = '" + Base + "'";
                Statement statement11 = connection.createStatement();
                ResultSet resultSet11 = statement11.executeQuery(query11);
                int i = 0;
                while (resultSet11.next())
                {
                    i=resultSet11.getInt(1);
                }
                String query12 = "update Student set Name = '" + Bases + "', Surname ='" + Positions + ", Image = '"+ Img +"' where Name_id = "+i+"";
                Statement statement12 = connection.createStatement();
                statement12.execute(query12);
                finish();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Данные успешно изменены", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}