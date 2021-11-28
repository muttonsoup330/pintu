package com.example.pintu;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ImageSplitter {

    public static List<ImagePiece> split(Bitmap bitmap, int piece){

        List<ImagePiece> pieces = new ArrayList<ImagePiece>(piece * piece);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.e("TAG", "bitmap Width = " + width + " , height = " + height);
        int pieceWidth = Math.min(width, height) / piece;
        for (int i = 0; i < piece; i++){
            for (int j = 0; j < piece; j++){
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.index = j + i * piece;
                int xValue = j * pieceWidth;
                int yValue = i * pieceWidth;

                imagePiece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceWidth);
                pieces.add(imagePiece);
            }
        }
        return pieces;
    }
}

