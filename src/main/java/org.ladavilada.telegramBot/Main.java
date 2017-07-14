package org.vvpro.pbot.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by vbobina on 12.07.2017.
 */
public class Main {

    public static final String BOT_BASEURL = "https://api.telegram.org/bot";
    public static final String BOT_TOKEN = "420132414:AAFDyDtqIA5kEU77Pe0LDDnI2ZbDYLUz6Zw";
    private volatile RequestConfig requestConfig;
    private volatile CloseableHttpClient httpclient;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ExecutorService exe;


    public static void main(String[] args) {
        Main myClass = new Main();
        try {
            myClass.initData();
            myClass.sendApiMethod(new LabeledPrice("$", 123));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    protected final <T extends Serializable, Method extends BotApiMethod<T>> String sendApiMethod(LabeledPrice method) throws TelegramApiException {
        method.validate();
        String responseContent;
        try {
//            String url = getBaseUrl() + method.getMethod();
//            https://api.telegram.org/bot420132414:AAFDyDtqIA5kEU77Pe0LDDnI2ZbDYLUz6Zw/getUpdates
            String url = getBaseUrl() + "sendMessage"+ "?chat_id=-240537847&text=uuuh!";
            HttpPost httppost = new HttpPost(url);
            httppost.setConfig(requestConfig);
            httppost.addHeader("content-type", "application/x-www-form-urlencoded");
//            httppost.setEntity(new StringEntity("details={\"name\":\"myname\",\"age\":\"20\"} "));
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                HttpEntity ht = response.getEntity();
                BufferedHttpEntity buf = new BufferedHttpEntity(ht);
                responseContent = EntityUtils.toString(buf, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new TelegramApiException("Unable to execute method", e);
        }

        return method.getLabel();
    }

    protected String getBaseUrl() {
        return getApiBaseUrl() + getBotToken() + "/";
    }

    private String getApiBaseUrl() {
        return BOT_BASEURL;
    }

    public Object getBotToken() {
        return BOT_TOKEN;
    }

    protected void initData() {
        this.exe = Executors.newFixedThreadPool(3);
//        this.options = options;
        httpclient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setConnectionTimeToLive(70, TimeUnit.SECONDS)
                .setMaxConnTotal(100)
                .setProxy(new HttpHost("127.0.0.1",1234))
                .build();

//        requestConfig = new Re;
//
//        if (requestConfig == null) {
//            requestConfig = RequestConfig.copy(RequestConfig.custom().build())
//                    .setSocketTimeout(SOCKET_TIMEOUT)
//                    .setConnectTimeout(SOCKET_TIMEOUT)
//                    .setConnectionRequestTimeout(SOCKET_TIMEOUT).build();
//        }
    }
}
