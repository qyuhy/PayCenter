package open.pay.center.core;

import open.pay.center.core.daifu.DaifuWayFactory;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.exception.PayException;
import open.pay.center.core.pay.PayWayFactory;
import open.pay.center.core.pay.query.PayQuery;
import open.pay.center.core.pay.request.PayCoreQueryRequest;
import open.pay.center.core.pay.response.PayCoreQueryResponse;
import open.pay.center.core.pay.way.ProtocolPay;
import open.pay.center.core.pay.way.ScanPay;
import open.pay.center.core.util.http.HttpClient;
import open.pay.center.core.util.http.Param;

import java.util.Map;

/**
 * User: hyman
 * Date: 2017/4/20 0020
 * Time: 14:45
 * Email: qyuhy@qq.com
 */
public abstract class AbstractPayCenterFactory implements PayWayFactory, PayQuery ,DaifuWayFactory{

    public ProtocolPay createProtocolPay() {
        return injectProtocolPay();
    }

    public ScanPay createScanPay(){
        return injectScanPay();
    }


    public PayCoreQueryResponse payQuery(PayCoreQueryRequest request) {
        return doPayQuery(request);
    }


    public TwoStepDaifu createTwoStepDaifu(){
        return injectTwoStepDaifu();
    }

    /**
     * 两阶段代付
     * @return
     */
    protected abstract TwoStepDaifu injectTwoStepDaifu();

    /**
     * 子类实现具体的支付查询结果
     *
     * @param request
     * @return
     */
    protected abstract PayCoreQueryResponse doPayQuery(PayCoreQueryRequest request);

    /**
     * 子类注入实现
     *
     * @return
     */
    protected abstract ProtocolPay injectProtocolPay();

    /**
     * 子类注入扫码支付类
     * @return
     */
    protected abstract ScanPay injectScanPay();

    /**
     * 发送POST请求
     * @param url               请求地址
     * @param param              请求数据
     * @param encoding          请求编码
     * @param connectionTimeout 链接超时时间
     * @param readTimeOut       读取数据超时时间
     * @return
     * @throws Exception
     */
    protected String send(String url, Param param, String encoding, int connectionTimeout, int readTimeOut){
        HttpClient httpClient = new HttpClient(url,connectionTimeout,readTimeOut);
        try {
            httpClient.send(param,encoding);
        } catch (Exception e) {
            throw new PayException("发送POST请求异常!",e);
        }
        return httpClient.getResult();
    }

    /**
     * 发送get请求
     * @param url               请求地址
     * @param encoding          编码
     * @param connectionTimeout 建立链接超时时间
     * @param readTimeOut       读取数据超时时间
     * @return
     */
    protected String sendGet(String url,String encoding,int connectionTimeout, int readTimeOut){
        HttpClient httpClient = new HttpClient(url,connectionTimeout,readTimeOut);
        try {
            httpClient.sendGet(encoding);
        } catch (Exception e) {
            throw new PayException("发送GET请求异常!",e);
        }
        return httpClient.getResult();
    }
}
