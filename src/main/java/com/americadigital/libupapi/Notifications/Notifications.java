package com.americadigital.libupapi.Notifications;

import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class Notifications {
    private static final String FIREBASE_API_URL = ConstantsTexts.FIREBASE_URL;
    private static final String FIREBASE_SERVER_KEY = ConstantsTexts.FIREBASE_KEY;

    public static String sendPushNotification(List<String> deviceTokens, String idShop, String idPromo, String namePromo, String pathImage, String msg, String typeNotification, String commerceName, String urlCommerce) throws IOException {

        String result = "";
        URL url = new URL(FIREBASE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + FIREBASE_SERVER_KEY);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        System.out.println(deviceTokens);
        try {
            json.put("registration_ids", deviceTokens);
            JSONObject data = new JSONObject();
            data.put("message", msg);
            data.put("idShop", idShop);
            data.put("idPromo", idPromo);
            data.put("namePromo", namePromo);
            data.put("imagePath", pathImage);
            data.put("type", typeNotification);
            data.put("commerceName", commerceName);
            data.put("urlCommerce", urlCommerce);
            json.put("data", data);

        } catch (JSONException e1) {
            throw new ConflictException(e1 + ": " + e1);
        }

        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "succcess";
        } catch (Exception e) {
            e.printStackTrace();
            result = "failure";
            throw new ConflictException(result + ": " + StringFormatUtilities.exceptionMsgBuilder(e));
        }
        System.out.println("GCM Notification is sent successfully");

        return result;

    }
}
