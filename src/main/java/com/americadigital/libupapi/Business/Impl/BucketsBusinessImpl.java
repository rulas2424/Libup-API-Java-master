package com.americadigital.libupapi.Business.Impl;


import com.americadigital.libupapi.Business.Interfaces.BucketsBusiness;
import com.americadigital.libupapi.Pojos.AcrCloud.AddBucket;
import com.americadigital.libupapi.Pojos.AcrCloud.ItemsBucketsAll;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.AcrCloud.Buckets.AddBucketRequest;
import com.americadigital.libupapi.WsPojos.Response.AcrCloud.AcrResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service("acrCloudBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class BucketsBusinessImpl implements BucketsBusiness {
    private static final String GET_URL = "https://api.acrcloud.com/v1/buckets";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final Logger LOG = LoggerFactory.getLogger(BucketsBusinessImpl.class);

    public static String signatureVersion = "1";
    public static String httpAction = "/v1/buckets";
    public static String accessSecret = "cde76a9eed329b3e";
    public static String accessKey = "3c6269306d52ce61e449e7e41eb39ac7";

    @Override
    public ResponseEntity<AcrResponse> getAllBuckets() throws IOException {
        String msg;
        AcrResponse response;
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        String htttMethod = "GET";
        String timestamp = getUTCTimeSeconds();
        String sigStr = htttMethod + "\n" + httpAction + "\n" + accessKey
                + "\n" + signatureVersion + "\n" + timestamp;
        String signature = encryptByHMACSHA1(sigStr.getBytes(),
                accessSecret.getBytes());
        con.setRequestMethod("GET");
        con.setRequestProperty("access-key", accessKey);
        con.setRequestProperty("signature-version", signatureVersion);
        con.setRequestProperty("signature", signature);
        con.setRequestProperty("timestamp", timestamp);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine).append('\n');
        }
        in.close();
        Object fromJsonGet = fromJsonGetBuccket(responseBuffer.toString());
        msg = ConstantsTexts.BUCKETS_GET;
        response = new AcrResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), fromJsonGet);
        con.disconnect();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AcrResponse> addBucket(AddBucketRequest bucketRequest) throws IOException {
        String msg;
        AcrResponse response;
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        String htttMethod = "POST";
        String timestamp = getUTCTimeSeconds();
        String sigStr = htttMethod + "\n" + httpAction + "\n" + accessKey
                + "\n" + signatureVersion + "\n" + timestamp;
        String signature = encryptByHMACSHA1(sigStr.getBytes(),
                accessSecret.getBytes());
        con.setRequestMethod("POST");
        //SET HEADERS PARAMS
        con.setRequestProperty("access-key", accessKey);
        con.setRequestProperty("signature-version", signatureVersion);
        con.setRequestProperty("signature", signature);
        con.setRequestProperty("timestamp", timestamp);

        //SET HEADERS PARAMS
        String scale = bucketRequest.getScale().toString();
        HashMap<String, String> postParams = new HashMap<>();
        postParams.put("name", bucketRequest.getName());
        postParams.put("type", bucketRequest.getType());
        postParams.put("scale", scale);
        postParams.put("content_type", bucketRequest.getContent_type());
        postParams.put("region", bucketRequest.getRegion());

        OutputStream outputStream = con.getOutputStream();
        outputStream.write(getPostDataString(postParams).getBytes());
        outputStream.flush();

        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                (con.getInputStream())));
        StringBuffer stringBuffer = new StringBuffer();
        String output;
        while ((output = responseBuffer.readLine()) != null) {
            stringBuffer.append(output);
        }
        ObjectMapper mapper = new ObjectMapper();
        AddBucket bucketResponse = mapper.readValue(stringBuffer.toString(), AddBucket.class);

        responseBuffer.close();
        msg = ConstantsTexts.BUCKETS_ADD;
        response = new AcrResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), bucketResponse);
        con.disconnect();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderGeneric> deleteBucket(String name_bucket) throws IOException {
        String msg;
        HeaderGeneric response;
        URL obj = new URL(GET_URL + "/" + name_bucket);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        String htttMethod = "DELETE";
        String timestamp = getUTCTimeSeconds();
        String sigStr = htttMethod + "\n" + httpAction + "/" + name_bucket + "\n" + accessKey
                + "\n" + signatureVersion + "\n" + timestamp;
        String signature = encryptByHMACSHA1(sigStr.getBytes(),
                accessSecret.getBytes());
        con.setRequestMethod("DELETE");
        con.setRequestProperty("access-key", accessKey);
        con.setRequestProperty("signature-version", signatureVersion);
        con.setRequestProperty("signature", signature);
        con.setRequestProperty("timestamp", timestamp);
        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        LOG.info("HttpStatus CODE>>" + con.getResponseCode());
        responseBuffer.close();
        msg = ConstantsTexts.BUCKETS_DELETE + name_bucket;
        response = new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg);
        con.disconnect();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public Object fromJsonGetBuccket(String json) throws JsonParseException
            , JsonMappingException, IOException {
        ItemsBucketsAll items = new ObjectMapper().readValue(json, ItemsBucketsAll.class);
        return items;
    }

    private static String getUTCTimeSeconds() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTimeInMillis() / 1000 + "";
    }

    private static String encodeBase64(byte[] bstr) {
        Base64 base64 = new Base64();
        return new String(base64.encode(bstr));
    }

    private static String encryptByHMACSHA1(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data);
            return encodeBase64(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
