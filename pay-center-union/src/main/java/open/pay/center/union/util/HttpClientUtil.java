package open.pay.center.union.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YeTing on 2016/8/9.
 */
public class HttpClientUtil {
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
//        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpPost = new HttpGet(apiUrl);
            HttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println("执行状态码 : " + statusCode);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("http.protocol.content-charset", "UTF-8");
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     * @param apiUrl
     * @param params
     * @param charset
     * @return
     */
    public static String doPost(String apiUrl, Map<String, String> params, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("http.protocol.content-charset", charset);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(charset)));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            int responseStatus = response.getStatusLine().getStatusCode();
            System.out.println(responseStatus);
            if(responseStatus == 200){
                httpStr = EntityUtils.toString(entity, charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, String> params) {
        CloseableHttpClient httpClient =
                HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr)
                .setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("http.protocol.content-charset", "utf-8");
            List<NameValuePair> pairList = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     * @param apiUrl
     * @param params
     * @param charset
     * @return
     */
    public static String doPostSSL(String apiUrl,Map<String, String> params, String charset) {
        CloseableHttpClient httpClient = HttpClients.custom().
                setSSLSocketFactory(createSSLConnSocketFactory()).
                setConnectionManager(connMgr).
                setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = null;
        HttpResponse response = null;
        String httpStr = null;

        try {
            httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("http.protocol.content-charset", charset);
            List<NameValuePair> list = new ArrayList<>();
            if(params!=null && params.size()>0){
                for (Map.Entry<String, String> entry : params.entrySet()){
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     * @param apiUrl
     * @param params
     * @param charset
     * @return
     */
    public static InputStream doPostSSLWithInputStream(String apiUrl,Map<String, String> params,String charset) {
        CloseableHttpClient httpClient = HttpClients.custom().
                setSSLSocketFactory(createSSLConnSocketFactory()).
                setConnectionManager(connMgr).
                setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = null;
        HttpResponse response = null;
        try {
            httpPost = new HttpPost(apiUrl);
            httpPost.setConfig(requestConfig);
            //httpPost.setHeader("http.protocol.content-charset", charset);
            List<NameValuePair> list = new ArrayList<>();
            if(params!=null && params.size()>0){
                for (Map.Entry<String, String> entry : params.entrySet()){
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            InputStream stream = new ByteArrayInputStream(IOUtils.toByteArray(entity.getContent()));
            return stream;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }


    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        //merId=808080211880315&
        // merDate=20160805&
        // merSeqId=2016080500000010&
        // cardNo=6225882106897891&
        // usrName=赵钱孙&
        // openBank=工商银行&
        // prov=上海&
        // city=上海&
        // transAmt=000000000001&
        // purpose=稿费&
        // subBank=&
        // flag=00&
        // version=20150304&
        // chkValue=95BC6C37ABDD5E773FF2CAA46CC9B2DC2F4682247735E0A519993CE93D2A7CCC9F7C7DC56E44469F5EB2639228DB177C44B16BA0E6A15C51E3536E5245956057CB56283D5A5C20AC068CC4E713D2758F9C71410B0FEF072B56FFBB4B11DD1DCD76EEEC9A76D540E709FE0EE18627979B514CFF3D30DA703B5A56F6BBB4A5C30F&
        // signFlag=1&
        // termType=
        params.put("merId", "808080211880315");
        params.put("merDate", "20160813");
        params.put("merSeqId", "2016080500000011");
        params.put("cardNo", "6225882106897891");
        params.put("usrName", "赵钱孙");
        params.put("openBank", "工商银行");
        params.put("prov", "上海");
        params.put("city", "上海");
        params.put("transAmt", "000000000001");
        params.put("purpose", "稿费");
        params.put("subBank", "");
        params.put("flag", "00");
        params.put("version", "20150304");
        params.put("chkValue", "47EEE33697969C4564C106A237582E4861EC4B60894FF5B55DC62C34B6AD8D8FE49BF13E0579021DB49F9B336F0976F651936AA022EBEEB44703DA16FE6788AC8906A6AC7A3F626635F1C2F9F33390F78C508DD8E42DB3F6DCFFA6DE5019E817DC6E1B9DB22F4D216447F8703589C136FADBF5354936098F7D5F6C1641842D88");
        params.put("signFlag", "1");
        params.put("termType", "");
        String responseBody = doPost("http://sfj-test.chinapay.com/dac/SinPayServletGBK",params, "GBK");
        System.out.println(responseBody);
//        Map<String, String> p = new HashMap<String, String>();
//        p.put("phone", "15601643300");
//        p.put("password", "abc123");
//        String responseBody = doPostSSL("https://api.zhengshijr.com/api/user/login_pwd",p, "GBK");
        System.out.println(responseBody);
    }

//    public String sendHttpPost(String url, Map<String, String> params, String charset){
////        HttpClient httpClient = new HttpClient();
//    }
}