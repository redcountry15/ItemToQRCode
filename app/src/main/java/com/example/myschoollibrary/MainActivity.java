package com.example.myschoollibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    EditText editText;
    String EditTextValue ;
    Thread thread ;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.img_view);
        editText = findViewById(R.id.edt_text);
        button = findViewById(R.id.btn_generate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextValue = editText.getText().toString();

                try {
                    bitmap = TextToImageEncode(EditTextValue);
                    imageView.setImageBitmap(bitmap);
                }catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });


    }
    Bitmap TextToImageEncode(String value) throws WriterException{
        BitMatrix bitMatrix;
        try {
            bitMatrix  = new MultiFormatWriter().encode(
                    value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth,QRcodeWidth,null
            );
        }catch (IllegalArgumentException illegal){
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrikdHeigh = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth*bitMatrikdHeigh];
        for (int y  =0; y<bitMatrikdHeigh;y++){

            int offset = y*bitMatrixWidth;

            for (int x=0;x<bitMatrixWidth;x++){

                pixels[offset + x] = bitMatrix.get(x,y) ?
                        getResources().getColor(R.color.CodeBlackColor):getResources().getColor(R.color.CodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth,bitMatrikdHeigh,Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels,0,500,0,0,bitMatrixWidth,bitMatrikdHeigh);
        return bitmap;
    }
}
