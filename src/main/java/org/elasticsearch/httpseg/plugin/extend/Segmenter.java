package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author BD-PC27
 */

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.elasticsearch.env.Environment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Segmenter {

    static String req_url = "http://192.168.4.250:58086/z";
    static String debug = "false";

    public static String getPath(String core_config, String key) {
        String filePath = null;
        InputStream in = null;
        try {
            Properties properties = new Properties();
            in = Segmenter.class.getClassLoader().getResourceAsStream(core_config);
            properties.load(in);
            filePath = properties.getProperty(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    public Segmenter() {
        debug = "false";
    }

    public Segmenter(Environment environment) {
        String core_config = environment.pluginsFile().resolve("httpseg-plugin/config/core.config").toString();
        req_url = getPath(core_config, "modelserver");
        debug = getPath(core_config, "debug");
    }

    public List<Token> tokenize(String sentence) throws JSONException {
        List<Token> tokens = new ArrayList<Token>();
        int start = 0;

        JSONObject jsobj = new JSONObject();
        jsobj.put("text", sentence);
        if (debug == "true") {
            System.out.println(jsobj.toString());
        }
        String result = post(jsobj, req_url);
        JSONObject outjson = new JSONObject(result);
        if (debug == "true") {
            System.out.println(outjson.toString());
        }
        JSONArray outarray = outjson.getJSONArray("data");
        for (int i = 0; i < outarray.length(); i++) {

            JSONObject wordobj = outarray.getJSONObject(i);
            String word = wordobj.getString("word");
            String pos;
            boolean haspos = wordobj.has("pos");
            if (haspos) {
                pos = wordobj.getString("pos");
            } else {
                pos = "w";
            }

            tokens.add(new Token(word, pos, start, start + word.length()));
            start += word.length();
        }

        return tokens;

    }

    public static String post(JSONObject jsonobj, String URL) {

        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        String result = "";

        CloseableHttpClient client = HttpClients.createDefault();

        try {
            StringEntity s = new StringEntity(jsonobj.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            //发送请求
            CloseableHttpResponse httpResponse = client.execute(post);

            //获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            //System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求成功，作相应处理");

            } else {
                System.out.println("请求失败");
            }
        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }
        return result;
    }
}
