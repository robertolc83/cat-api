package org.catApi.service;

import org.catApi.api.CatApi;
import org.catApi.model.CatModel;
import org.catApi.model.SeeCatModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CatService {

    /**
     * Descripción: Método que devuelve una imagen de un gato random
     *
     * private String id : id de la imagen
     * private ImageIcon image : imagen del gato
     *
     * @return seeCat;
     */
    public static SeeCatModel seeCatService(){

        //Hacemos el llamado a la API
        CatModel catModel = CatApi.getApiCat();
        //Método para convertir la url en imagen y redimenciona su tamaño
        ImageIcon image = resizeImageService(catModel.getUrl());

        //Creamos un objeto seeCat y seteamos el id y la imagen a desplegar
        SeeCatModel seeCatModel = new SeeCatModel();
        seeCatModel.setId(catModel.getId());
        seeCatModel.setImage(image);

        return seeCatModel;
    }

    /**
     * Descripción: Método que recibe una url de una imagen y regresa una imagen de 800 x 800
     *
     * @param
     * @return imageIcon
     */
    public static ImageIcon resizeImageService(String urlCat){
        try{
            //Convierte la url en imagen
            URL url = new URL(urlCat);
            Image image = ImageIO.read(url);
            ImageIcon imageIcon = new ImageIcon(image);

            //Validamos el ancho de la imagen
            if(imageIcon.getIconWidth() > 800 || imageIcon.getIconHeight() > 800){
                //Redimencionamos la imagen
                Image imageOld = imageIcon.getImage();
                Image imageNew = imageOld.getScaledInstance(600,600, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(imageNew);
            }
            return imageIcon;
        }catch (IOException ioException){
            System.out.println("IN resizeImageService");
            System.out.println(ioException);
        }
        return null;
    }
}
