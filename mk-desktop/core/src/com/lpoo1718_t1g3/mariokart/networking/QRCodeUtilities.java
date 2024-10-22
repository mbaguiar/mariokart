package com.lpoo1718_t1g3.mariokart.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Utility functions to handle qr codes
 */
public class QRCodeUtilities {

    private static final int size = 200;

    /**
     * Generates qr code
     * @param ip ip to create qr code from
     * @param port port to create qr code from
     * @return Returns true on success and false otherwise
     */
    static boolean generateQRCode(String ip, int port){
        String address = ip + ":" + port;

        QRCodeWriter w = new QRCodeWriter();

        try {
            BitMatrix b = w.encode(address, BarcodeFormat.QR_CODE, size, size);

            MatrixToImageConfig config = new MatrixToImageConfig(0xFFDC3918, MatrixToImageConfig.WHITE);

            BufferedImage img = MatrixToImageWriter.toBufferedImage(b, config);

            ImageIO.write(img, "PNG", new File(Gdx.files.getLocalStoragePath() + "qrcode.png"));


            return true;

        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

}
