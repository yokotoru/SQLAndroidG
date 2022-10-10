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

public class MainActivity extends AppCompatActivity {
    Connection connection;
    String ConnectionResult = "";
    ImageView image;
    String Img="";
    public final int[] i = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configurationNextButton();
        configurationNextButton1();
        Del();
    }

    private void configurationNextButton() {
        try{
            Button addData = (Button) findViewById(R.id.addData);
            addData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, AddData.class));
                }
            });
        }
        catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void configurationNextButton1() {
        Button Poisk = (Button) findViewById(R.id.Poisk);
        Poisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Poisk.class));
            }
        });
    }

    public void GetTextFromSql1(View a) {
        TextView Name_id = findViewById(R.id.Name_id);
        TextView IName = findViewById(R.id.IName);
        TextView ISurname = findViewById(R.id.ISurname);
        ImageView imageView = findViewById(R.id.image);
        try {
            ConectionHellper conectionHellper = new ConectionHellper();
            connection = conectionHellper.connectionClass();
            if (connection != null) {

                String query0 = "select count(Name_id) from Student ";
                Statement statement0 = connection.createStatement();
                ResultSet resultSet0 = statement0.executeQuery(query0);
                int c = 0;
                while (resultSet0.next()) {
                    c = resultSet0.getInt(1);
                }

                int finalC = c;

                        if (i[0] != finalC) {
                            i[0] = i[0] + 1;
                        }
                Button IzmData = (Button) findViewById(R.id.IzmData);
                IzmData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, IzmData.class);
                        intent.putExtra("Gposition",IName.getText().toString());
                        intent.putExtra("Number",ISurname.getText().toString());
                        intent.putExtra("Image",Img);
                        startActivity(intent);
                    }
                });

                String query = "Select * From Student where Name_id=" + i[0] + "";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Name_id.setText(resultSet.getString(1));
                    IName.setText(resultSet.getString(2));
                    ISurname.setText(resultSet.getString(3));
                    Img = (resultSet.getString(4));
                        imageView.setImageBitmap(getImgBitmap(Img));
                }
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {

        }

    }
    public void GetTextFromSql(View a) {
        TextView Name_id = findViewById(R.id.Name_id);
        TextView IName = findViewById(R.id.IName);
        TextView ISurname = findViewById(R.id.ISurname);
        ImageView imageView = findViewById(R.id.image);
        try {
            ConectionHellper conectionHellper = new ConectionHellper();
            connection = conectionHellper.connectionClass();

            if (connection != null) {

                String query3 = "select count(Name_id) from Student ";
                Statement statement3 = connection.createStatement();
                ResultSet resultSet3= statement3.executeQuery(query3);
                int c = 0;
                while (resultSet3.next()) {
                    c = resultSet3.getInt(1);
                }
                if (i[0] != 1) {
                    i[0] = i[0] - 1;
                }

                Button IzmData = (Button) findViewById(R.id.IzmData);
                IzmData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, IzmData.class);
                        intent.putExtra("Name",IName.getText().toString());
                        intent.putExtra("Surname",ISurname.getText().toString());
                        startActivity(intent);
                    }
                });

                String query4 = "Select * From Student where Name_id=" + i[0] + "";
                Statement statement4 = connection.createStatement();
                ResultSet resultSet4 = statement4.executeQuery(query4);
                while (resultSet4.next()) {
                    Name_id.setText(resultSet4.getString(1));
                    IName.setText(resultSet4.getString(2));
                    ISurname.setText(resultSet4.getString(3));
                    Img = (resultSet4.getString(4));
                    imageView.setImageBitmap(getImgBitmap(Img));
                }
            } else {
                ConnectionResult = "Check Connection";
            }
        } catch (Exception ex) {

        }

    }
    public void GetTextFromSql2(View a) {
        TextView Name_id = findViewById(R.id.Name_id);
        TextView IName = findViewById(R.id.IName);
        TextView ISurname = findViewById(R.id.ISurname);
        ImageView imageView = findViewById(R.id.image);
        try {
            ConectionHellper conectionHellper = new ConectionHellper();
            connection = conectionHellper.connectionClass();

            if (connection != null) {


                Button IzmData = (Button) findViewById(R.id.IzmData);
                IzmData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, IzmData.class);
                        intent.putExtra("Id",Name_id.getText().toString());
                        intent.putExtra("Name",IName.getText().toString());
                        intent.putExtra("Surname",ISurname.getText().toString());
                        startActivity(intent);
                    }
                });

                String query7 = "Select * From Student where Name_id=" + i[0] + "";
                Statement statement7 = connection.createStatement();
                ResultSet resultSet7 = statement7.executeQuery(query7);
                while (resultSet7.next()) {
                    Name_id.setText(resultSet7.getString(1));
                    IName.setText(resultSet7.getString(2));
                    ISurname.setText(resultSet7.getString(3));
                    Img = (resultSet7.getString(4));
                    imageView.setImageBitmap(getImgBitmap(Img));
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
        return BitmapFactory.decodeResource(MainActivity.this.getResources(),
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
                image.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
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
            Img=Base64.getEncoder().encodeToString(bytes);
            return Img;
        }
        return "";
    }
    private  void Del()
    {
        Button DelData = findViewById(R.id.delData);
        DelData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ConectionHellper conectionHellper = new ConectionHellper();
                    connection = conectionHellper.connectionClass();

                    if (connection != null) {

                        String query2 = "DELETE FROM Student WHERE Name_id = " + i[0] + "";
                        Statement statement2 = connection.createStatement();
                        statement2.execute(query2);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Удаление прошло успешно", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        ConnectionResult = "Check Connection";
                    }
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}