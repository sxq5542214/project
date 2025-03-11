package com.yd.business.client;

import com.yd.basic.framework.client.BaseCMDClient;
import com.yd.util.JsonUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
public class RuiLaiShuiWuInterfaceClient extends BaseCMDClient {

    private final String baseUrl = "";
    private final String appId = "";
    private final String appSecret = "";

//    public RuiLaiShuiWuInterfaceClient(String baseUrl, String appId, String appSecret) {
//        this.baseUrl = baseUrl;
//        this.appId = appId;
//        this.appSecret = appSecret;
//    }

    public String encrypt(String data) throws Exception {
        // 加密密钥为appId后16字节
        byte[] keyBytes = appId.getBytes(StandardCharsets.UTF_8);
        byte[] key = new byte[16];
        System.arraycopy(keyBytes, keyBytes.length - 16, key, 0, 16);
        
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encrypted);
    }

    public String sign(String encryptedBody, String sequenceId) throws Exception {
        String signStr = String.format("%s$%s$%s$%s", appId, appSecret, sequenceId, encryptedBody);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(signStr.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String request(String endpoint, String jsonBody) throws Exception {
        String sequenceId = String.valueOf(System.currentTimeMillis() / 1000);
        String encryptedBody = encrypt(jsonBody);
        String signature = sign(encryptedBody, sequenceId);

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl + endpoint);
        
        httpPost.setHeader("SequenceId", sequenceId);
        httpPost.setHeader("Authorization", appId);
        httpPost.setHeader("Signature", signature);
        httpPost.setHeader("Content-Type", "application/json");
        
        if (!jsonBody.isEmpty()) {
            httpPost.setEntity(new StringEntity(jsonBody, StandardCharsets.UTF_8));
        }

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    // 表具列表查询示例
    public String getMeterList() throws Exception {
        return request("/exts/flowmeter/list.json", "{}");
    }

    // 实时数据查询示例
    public String getRealTimeData(String meterCode, String[] units) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if (meterCode != null) params.put("flowmeter", meterCode);
        if (units != null) params.put("units", units);
        return request("/exts/flowmeter/realtimes.json", JsonUtil.convertObjectToJsonString(params));
    }

    // 历史数据查询示例
    public String getHistoryData(String meterCode, long startTime, long endTime, String[] units) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("flowmeter", meterCode);
        params.put("stime", startTime);
        params.put("etime", endTime);
        if (units != null) params.put("units", units);
        return request("/exts/flowmeter/history.json", JsonUtil.convertObjectToJsonString(params));
    }
}

