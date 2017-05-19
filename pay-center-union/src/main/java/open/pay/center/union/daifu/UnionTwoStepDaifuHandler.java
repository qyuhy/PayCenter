package open.pay.center.union.daifu;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import open.pay.center.core.daifu.request.QueryTwoStepDaifuRequest;
import open.pay.center.core.daifu.request.SubmitTwoStepDaifuRequest;
import open.pay.center.core.daifu.response.QueryTwoStepDaifuResponse;
import open.pay.center.core.daifu.response.SubmitTwoStepDaifuResponse;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.model.MerInfo;
import open.pay.center.core.model.ResponseStatus;
import open.pay.center.core.util.http.Param;
import open.pay.center.union.AbstractUnionPayCenter;
import open.pay.center.union.daifu.request.UnionQueryTwoStepDaifuRequest;
import open.pay.center.union.daifu.request.UnionSubmitTwoStepDaifuRequest;
import open.pay.center.union.daifu.response.UnionQueryTwoStepDaifuResponse;
import open.pay.center.union.daifu.response.UnionSubmitTwoStepDaifuResponse;
import open.pay.center.union.exception.UnionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 15:39
 * Email: qyuhy@qq.com
 */
public class UnionTwoStepDaifuHandler extends AbstractUnionPayCenter implements TwoStepDaifu {
    private Logger logger = LoggerFactory.getLogger(UnionTwoStepDaifuHandler.class);

    @Override
    protected TwoStepDaifu injectTwoStepDaifu() {
        return this;
    }

    public <T extends SubmitTwoStepDaifuResponse> T submitTwoStepDaifu(SubmitTwoStepDaifuRequest submitTwoStepDaifuRequest) {
        UnionSubmitTwoStepDaifuRequest req = ((UnionSubmitTwoStepDaifuRequest) submitTwoStepDaifuRequest);
        MerInfo merInfo = this.installPrivateKeyAndPublicMerInfo(req.getMerInfo());
        PrivateKey privateKey = ((PrivateKey) merInfo.getPriKey());
        PrivateKey publicKey = ((PrivateKey) merInfo.getPubKey());
        String plainSignData = req.getPlainSignData();
        String plainData = null;
        UnionSubmitTwoStepDaifuResponse daifuResponse;
        try {
            plainData = new String(Base64.encode(plainSignData.getBytes("GBK")));
            SecureLink secureLink = new SecureLink(privateKey);
            String chkValue = secureLink.Sign(plainData);
            //构建请求参数
            Param param = Param.build()
                    .of("merId",req.getMerId()) //在收付捷平台中开通的商户编号
                    .of("merDate",req.getMerDate())//标志该笔交易发生的日期,格式为YYYYMMDD，请填写当天的日期。
                    .of("merSeqId",req.getMerSeqId())//商户号+商户流水号+商户日期唯一标示一笔交易订单
                    .of("cardNo",req.getCardNo())//银行卡号或者存折号
                    .of("usrName",req.getUsrName())//收款人在银行开户时留存的开户姓名
                    .of("openBank",req.getOpenBank())//开户银行名称
                    .of("prov",req.getProv())//收款人开户行所在省
                    .of("city",req.getCity())//收款人开户行所在地区
                    .of("transAmt",req.getTransAmt())//金额,货币种类为人民币，以分为单位
                    .of("purpose",req.getPurpose())//存款用途
                    .of("flag",req.getFlag())//对公对私标记。“00”对私，“01”对公。
                    .of("version",req.getVersion())//版本号,固定为“20160530”
                    .of("signFlag",req.getSignFlag())//签名标志
                    .of("termType",req.getTermType())//表示商户代付业务使用场景，（业务参数）
                    .of("payMode",req.getPayMode())//表示商户代付业务交易模式
                    .of("chkValue",chkValue);//签名值
            //发送Http请求
            logger.info("union daifu submit request:银联代付交易请求参数为[{}]", (req.isEncrpyt() ? this.encryptLog(param.toString(),req.getEncryptPassword()) : param.toString()));
            String response = this.send(req.getUrl(), param,"GBK", req.getConnectionTimeout(), req.getReadTimeout());
            logger.info("union daifu submit response:银联代付交易请求返回值为[{}]",(req.isEncrpyt() ? this.encryptLog(response,req.getEncryptPassword()) : response));
            daifuResponse = new UnionSubmitTwoStepDaifuResponse(response);
            //处理验签结果
            String responseCheckValue = daifuResponse.getData().getChkValue();
            String responsePlainSign = response.substring(0,response.lastIndexOf("&"));
            String base64Data = new String(Base64.encode(responsePlainSign.getBytes("GBK")));
            SecureLink verifySignSecureLink = new SecureLink(publicKey);
            //没有验签通过，数据被篡改，设置
            if(!verifySignSecureLink.verifyAuthToken(base64Data, responseCheckValue)){
                daifuResponse.getData().setTransState(ResponseStatus.PENDING);
                daifuResponse.setTip("返回报文验签失败");
                daifuResponse.setStatus(ResponseStatus.PENDING);
            }
        } catch (UnsupportedEncodingException e) {
            throw UnionException.ENCODE_ERROR.newInstance("不支持的编码[%s]",e.getMessage());
        }
        return (T) daifuResponse;
    }

    public <T extends QueryTwoStepDaifuResponse> T queryTwoStepDaifu(QueryTwoStepDaifuRequest queryTwoStepDaifuRequest) {
        UnionQueryTwoStepDaifuRequest req = ((UnionQueryTwoStepDaifuRequest) queryTwoStepDaifuRequest);
        MerInfo merInfo = this.installPrivateKeyAndPublicMerInfo(req.getMerInfo());
        PrivateKey privateKey = ((PrivateKey) merInfo.getPriKey());
        PrivateKey publicKey = ((PrivateKey) merInfo.getPubKey());
        String plainSignData = req.getPlainSignData();
        String plainData = null;
        UnionQueryTwoStepDaifuResponse queryResponse = null;
        try {
            plainData = new String(Base64.encode(plainSignData.getBytes("GBK")));
            SecureLink secureLink = new SecureLink(privateKey);
            String chkValue = secureLink.Sign(plainData);
            //构建请求参数
            Param param = Param.build()
                    .of("merId",req.getMerId()) //在收付捷平台中开通的商户编号
                    .of("merDate",req.getMerDate())//标志该笔交易发生的日期,格式为YYYYMMDD，请填写当天的日期。
                    .of("merSeqId",req.getMerSeqId())//商户号+商户流水号+商户日期唯一标示一笔交易订单
                    .of("version",req.getVersion())//版本号,固定为“20160530”
                    .of("signFlag",req.getSignFlag())//签名标志
                    .of("chkValue",chkValue);//签名值
            //发送Http请求
            logger.info("union daifu query request:银联代付查询请求参数为[{}]", (req.isEncrpyt() ? this.encryptLog(param.toString(),req.getEncryptPassword()) : param.toString()));
            String response = this.send(req.getUrl(), param,"GBK", req.getConnectionTimeout(), req.getReadTimeout());
            logger.info("union daifu query response:银联代付查询请求返回值为[{}]",(req.isEncrpyt() ? this.encryptLog(response,req.getEncryptPassword()) : response));
            queryResponse = new UnionQueryTwoStepDaifuResponse(response);
            //处理验签结果
            String responseCheckValue = queryResponse.getData().getChkValue();
            String responsePlainSign = response.substring(0,response.length() - 256);
            String base64Data = new String(Base64.encode(responsePlainSign.getBytes("GBK")));
            SecureLink verifySignSecureLink = new SecureLink(publicKey);
            //没有验签通过，数据被篡改，设置
            if(!verifySignSecureLink.verifyAuthToken(base64Data, responseCheckValue)){
                queryResponse.getData().setTransState(ResponseStatus.PENDING);
                queryResponse.setTip("返回报文验签失败");
                queryResponse.setStatus(ResponseStatus.PENDING);
            }
        } catch (UnsupportedEncodingException e) {
            throw UnionException.ENCODE_ERROR.newInstance("不支持的编码[%s]",e.getMessage());
        }
        return (T) queryResponse;
    }
}
