package com.znys.model.service;

import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.znys.model.Constant;

/**
 * Created by ms on 2015/5/17.
 */
public class WebHelper {
    private final static int TIMEOUT = 10000;
    private final static String CHARSET = "utf-8";
    private final static String BOUNDARY = UUID.randomUUID().toString();
    private final static String PREFIX = "--";
    private final static String LINEEND = "\r\n";

    /**
     * use httpClient to send get request
     *
     * @param url the expected url to request
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws SocketException
     */
    public static String httpClientSendGet(String url) throws IOException {
        String httpUrl = url;
        HttpGet httpGet = new HttpGet(httpUrl);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpGet);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        return "";
    }

    /**
     * use httpClient to send get request with a cookies header
     *
     * @param url
     * @param cookies
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws SocketException
     */
    public static String httpClientSendGet(String url, String cookies) throws IOException {
        String httpUrl = url;
        HttpGet httpGet = new HttpGet(httpUrl);
        HttpClient httpClient = new DefaultHttpClient();
        httpGet.setHeader("cookie", cookies);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        return "";
    }

    /**
     * @param url
     * @param jsonString
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws SocketException
     * @throws SocketTimeoutException
     */
    public static String httpClientSendPost(String url, String jsonString) throws IOException {
        StringBuilder httpUrl = new StringBuilder(url);

        //httpUrl.append("yh/");
        //HttpPost httpPost = new HttpPost(url);
        HttpPost httpPost = new HttpPost(httpUrl.toString());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair(Constant.JSON_KEY, jsonString));
        HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
        httpPost.setEntity(httpEntity);

        HttpClient httpClient = new DefaultHttpClient();
        Log.i("weiwei-debug", "before execute the post");
        HttpResponse httpResponse = httpClient.execute(httpPost);
        Log.i("weiwei-debug", "Execute the post!");

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println("Success to connect to the cloud!");
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }

        return null;
    }

    /**
     * @param url
     * @param paramName
     * @param paramValue
     * @throws ClientProtocolException
     * @throws IOException
     * @throws SocketException
     */
    public static void httpClientSendPostWithoutResult(String url, List<String> paramName, List<String> paramValue) throws IOException {
        StringBuilder httpUrl = new StringBuilder(url);

        HttpPost httpPost = new HttpPost(httpUrl.toString());

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (int i = 0; i < paramName.size(); i++) {
            params.add(new BasicNameValuePair(paramName.get(i), paramValue.get(i)));
        }

        HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
        httpPost.setEntity(httpEntity);

        HttpClient httpClient = new DefaultHttpClient();
        httpClient.execute(httpPost);
    }

    public static String uploadFile(File[] files, String requestURL, String userId) throws IOException {
        String result = null;

        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(TIMEOUT);
        conn.setConnectTimeout(TIMEOUT);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Charset", CHARSET);
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        DataOutputStream dataOutputStream = new DataOutputStream(conn.getOutputStream());

        StringBuffer requestHeader = new StringBuffer();
        requestHeader.append(PREFIX);
        requestHeader.append(BOUNDARY);
        requestHeader.append(LINEEND);
        requestHeader.append("Content-Disposition: form-data; name=\"userId\"" + LINEEND);
        dataOutputStream.write(requestHeader.toString().getBytes());
        dataOutputStream.write(userId.getBytes());
        dataOutputStream.write(LINEEND.getBytes());
        dataOutputStream.flush();

        for (File file : files) {
            if (file.getName().contains(".mp3")) {
                httpContentWrapper(dataOutputStream, file, "music");
            } else if (file.getName().contains(".lrc")) {
                httpContentWrapper(dataOutputStream, file, "lrc");
            } else {
                httpContentWrapper(dataOutputStream, file, "image");
            }
        }

        int resultCode = conn.getResponseCode();
        Log.e("uploadstate", "response code:" + resultCode);
        if (resultCode == 200) {
            InputStream responseInputStream = conn.getInputStream();
            StringBuffer resultBuffer = new StringBuffer();
            int intFlag;
            while ((intFlag = responseInputStream.read()) != -1) {
                resultBuffer.append((char) intFlag);
            }
            result = resultBuffer.toString();
            Log.e("Uploadresult", "result : " + result);
        } else {
            Log.e("error", "request error");
        }

        return result;
    }

    private static void httpContentWrapper(DataOutputStream dataOutputStream, File file, String nameField) throws IOException {
        StringBuffer requestHeader = new StringBuffer();
        requestHeader.append(PREFIX);
        requestHeader.append(BOUNDARY);
        requestHeader.append(LINEEND);
        requestHeader.append("Content-Disposition: form-data; name=\"" + nameField + "\";" + "filename=\"" + file.getName() + "\"" + LINEEND);
        dataOutputStream.write(requestHeader.toString().getBytes());
        InputStream inputStream = new FileInputStream(file);
        byte[] byteData = new byte[(int) file.length()];
        int length = 0;
        while ((length = inputStream.read(byteData)) != -1) {
            dataOutputStream.write(byteData, 0, length);
        }
        inputStream.close();
        dataOutputStream.write(LINEEND.getBytes());
        dataOutputStream.flush();
    }

    /**
     * @param url
     * @param path
     * @param fileName
     * @return
     * @throws Exception
     */
    public static int downloadFile(String url, String path, String fileName) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = getInputStreamFromUrl(url);
            File resultFile = writeToSDFromInput(path, fileName, inputStream);
            if (resultFile == null) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                throw e;
            }
        }
        return 0;
    }

    /**
     * @param urlString
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static InputStream getInputStreamFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return inputStream;
    }

    /**
     * @param directory
     * @param fileName
     * @param input
     * @return
     */
    private static File writeToSDFromInput(String directory, String fileName, InputStream input) {
        File file = null;
        String SDPATH = Environment.getExternalStorageDirectory().toString();
        FileOutputStream output = null;
        File dir = new File(SDPATH + directory);
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            file = new File(dir + File.separator + fileName);
            file.createNewFile();
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            while ((input.read(buffer)) != -1) {
                output.write(buffer);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}

