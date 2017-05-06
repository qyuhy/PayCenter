package open.pay.center.baofu.daifu;

import open.pay.center.baofu.AbstractBaoFuPayCenter;
import open.pay.center.baofu.daifu.request.BaofuQueryTwoStepDaifuRequest;
import open.pay.center.baofu.daifu.request.BaofuSubmitTwoStepDaifuRequest;
import open.pay.center.baofu.daifu.response.BaofuQueryTwoStepDaifuResponse;
import open.pay.center.baofu.daifu.response.BaofuSubmitTwoStepDaifuResponse;
import open.pay.center.core.daifu.request.QueryTwoStepDaifuRequest;
import open.pay.center.core.daifu.request.SubmitTwoStepDaifuRequest;
import open.pay.center.core.daifu.response.QueryTwoStepDaifuResponse;
import open.pay.center.core.daifu.response.SubmitTwoStepDaifuResponse;
import open.pay.center.core.daifu.way.TwoStepDaifu;
import open.pay.center.core.model.MerInfo;
import open.pay.center.core.util.http.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * User: hyman
 * Date: 2017/4/24 0024
 * Time: 15:39
 * Email: qyuhy@qq.com
 */
public class BaofuTwoStepDaifuHandler extends AbstractBaoFuPayCenter implements TwoStepDaifu{
    private Logger logger = LoggerFactory.getLogger(BaofuTwoStepDaifuHandler.class);

    @Override
    protected TwoStepDaifu injectTwoStepDaifu() {
        return this;
    }

    public <T extends SubmitTwoStepDaifuResponse> T submitTwoStepDaifu(SubmitTwoStepDaifuRequest submitTwoStepDaifuRequest) {
        BaofuSubmitTwoStepDaifuRequest req = ((BaofuSubmitTwoStepDaifuRequest) submitTwoStepDaifuRequest);
        MerInfo merInfo = this.installPrivateKeyAndPublicMerInfo(req.getMerInfo());
        PrivateKey privateKey = merInfo.getPrivateKey();
        PublicKey publicKey = merInfo.getPublicKey();
        //构建请求参数
        Param param = Param.build()
                .of("version",req.getVersion())
                .of("member_id",merInfo.getMerId())
                .of("data_type",req.getDataType())
                .of("terminal_id",merInfo.getTerminalId())
                .of("data_content",this.encodeBase64AndEncryptByPrivateKey(req.getDataContent(),privateKey));
        logger.info("宝付代付请求加密信息为:["+req.getDataContent()+"]");
        //发送Http请求
        String response = this.send(req.getUrl(), param,"UTF-8", req.getConnectionTimeout(), req.getReadTimeout());
        //解析数据
        String decodedText = this.decryptByPublicKeyAndDecodeBase64(response, publicKey);
        logger.info("宝付代付响应原文为:[" + decodedText + "]");
        BaofuSubmitTwoStepDaifuResponse retValue = new BaofuSubmitTwoStepDaifuResponse(decodedText);
        logger.info("宝付代付响应原文解析到对象为;["+retValue+"]");
        return (T)retValue;
    }

    public <T extends QueryTwoStepDaifuResponse> T queryTwoStepDaifu(QueryTwoStepDaifuRequest queryTwoStepDaifuRequest) {
        BaofuQueryTwoStepDaifuRequest req = ((BaofuQueryTwoStepDaifuRequest) queryTwoStepDaifuRequest);
        MerInfo merInfo = this.installPrivateKeyAndPublicMerInfo(req.getMerInfo());
        PrivateKey privateKey = merInfo.getPrivateKey();
        PublicKey publicKey = merInfo.getPublicKey();
        //构建请求参数
        Param param = Param.build()
                .of("version",req.getVersion())
                .of("member_id",merInfo.getMerId())
                .of("data_type",req.getDataType())
                .of("terminal_id",merInfo.getTerminalId())
                .of("data_content",this.encodeBase64AndEncryptByPrivateKey(req.getDataContent(),privateKey));
        //发送Http请求
        logger.info("宝付代付查询请求加密信息为:["+req.getDataContent()+"]");
        String response = this.send(req.getUrl(), param,"UTF-8", req.getConnectionTimeout(), req.getReadTimeout());
        //解析数据
        String decodedText = this.decryptByPublicKeyAndDecodeBase64(response, publicKey);
        logger.info("宝付代付查询响应原文为:[" + decodedText + "]");
        BaofuQueryTwoStepDaifuResponse retValue = new BaofuQueryTwoStepDaifuResponse(decodedText);
        logger.info("宝付代查询付响应原文解析到对象为;["+retValue+"]");
        return (T)retValue;
    }
}
