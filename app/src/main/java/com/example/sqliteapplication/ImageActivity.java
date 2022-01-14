package com.example.sqliteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

// https://www.youtube.com/watch?v=v54dyccVZn0&list=PL4_qJDCYtHa9JgZRk5i71jsVaFjrUwf6u&index=6
public class ImageActivity extends AppCompatActivity {
    TextView textView, nameTxt;
    EditText editText;
    ImageView imageView;
    Button button;
    DBImage DB;
    String nameDB;
    Bitmap imageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        editText = (EditText) findViewById(R.id.edittext);
        imageView = (ImageView) findViewById(R.id.image);
        button = (Button) findViewById(R.id.button);
        DB = new DBImage(ImageActivity.this);
        nameTxt = (TextView) findViewById(R.id.textview1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();

                // create bitmap for the image
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.draw);

                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                // Write a compressed version of the bitmap to the specified outputStream ( byteArray ).

                byte[] img = byteArray.toByteArray();

                boolean insert = DB.insertData(name, img); // we have passed same image for all records
                if (insert == true) {
                    Toast.makeText(ImageActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ImageActivity.this, "Data Not Saved",
                            Toast.LENGTH_SHORT).show();
                }

                imageDB = DB.getImage(name);
                nameDB = DB.getName(name);
                imageView.setImageBitmap(imageDB);
                nameTxt.setText("The name entered by you is \n\n" + nameDB);
            }
        });
    }
}
