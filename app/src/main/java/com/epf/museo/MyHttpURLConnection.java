package com.epf.museo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyHttpURLConnection {

    public static String startHttpRequest(String urlString){

        StringBuilder stringBuilder = new StringBuilder();

        try{
            //1 - Declaration de l'objet URL à partir d'une adresse HTTP contenue dans une variable String
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //2 - Ouverture d'un canal vers l'url et récupération du flix de donnée via InputStream
            conn.connect();
            InputStream in = conn.getInputStream();
            //3 - Lecture du flux par le BufferedReader et conversion ligne par ligne par le StringBuilder puis on retourne le résultat dans une variable de type String
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }

        } catch (MalformedURLException exception){

        } catch (IOException exception){

        } catch (Exception e){

        }
        return stringBuilder.toString();
    }
}
