package open.pay.center.core.model;


import open.pay.center.core.util.DataUtil;

import java.text.DecimalFormat;

/**
 * 金钱封装，统一使用金钱；
 * User:Hyman yu
 * Date:2016/6/29 0029
 * Time:10:19
 */
public class Money {
    /**
     * 单位：元
     */
    private double yuan;
    /**
     * 单位：分
     */
    private long fen;

    private enum UNIT{
        YUAN,FEN;
    }

    private Money(String input, UNIT unit){
        switch (unit) {
            case YUAN:
                this.fen = DataUtil.multi(String.valueOf(input),"100");
                this.yuan = DataUtil.divDouble(String.valueOf(this.fen),"100",2);
                break;
            case FEN:
                this.fen = Long.parseLong(input);
                this.yuan = DataUtil.divDouble(input,"100",2);
                break;
        }
    }

    /**
     *已分为单位获取
     * @param fen 金额，已分为单位,扩大100的数字
     * @return
     */
    public static Money newByFen(String fen){
        return new Money(fen, UNIT.FEN);
    }

    /**
     * 以元为单位获取
     * @param yuan
     * @return
     */
    public static Money newByYuan(String yuan){
        return new Money(yuan, UNIT.YUAN);
    }

    /**
     * @param fen 金额，已分为单位,扩大100的数字
     * @param fen
     * @return
     */
    public static Money newByFen(Long fen){
        return Money.newByFen(String.valueOf(fen));
    }

    /**
     *以元为单位获取
     * @param yuan
     * @return
     */
    public static Money newByYuan(Double yuan){
        return Money.newByYuan(String.valueOf(yuan));
    }

    /**
     * 相加
     * @param money
     * @return
     */
    public Money add(Money money){
        return Money.newByFen(this.getFen()+money.getFen());
    }

    /**
     * 相减
     * @param money
     * @return
     */
    public Money sub(Money money){
        return Money.newByFen(this.getFen()-money.getFen());
    }

    /**
     * 相乘
     * @param money
     * @return
     */
    public Money mul(Money money){
        return Money.newByYuan(DataUtil.divDouble(String.valueOf(this.getFen() * money.getFen()), "10000", 2));
    }

    /**
     * 相除
     * @param money
     * @return
     */
    public Money div(Money money){
        return Money.newByYuan(DataUtil.divDouble(String.valueOf(this.getYuan()),String.valueOf(money.getYuan()),2));
    }


    public double getYuan() {
        return yuan;
    }

    public long getFen() {
        return fen;
    }

    public String getFormatYuan(){
        return new DecimalFormat("0.##").format(this.yuan);
    }

    public static void main(String[] args) {
        Money money1 = Money.newByYuan("1000000001.10");
        System.out.println("格式化："+money1.getFormatYuan());
        System.out.println(money1.getFen());
        System.out.println(money1.getYuan());
        System.out.println(DataUtil.dataConstarateToLongFromDouble("100.567"));

        Money money2 = Money.newByFen("10011");
        System.out.println(money2.getYuan());

        System.out.println(money1.div(money2).getYuan());
    }
}
