package open.pay.center.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DataUtil {
	
	/**
	 * 两个数相减得到一个double结果
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double addDouble(String dataOne,String dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);   
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal subtract = b1.add(b2);
		return subtract.doubleValue();
	}
	
	/**
	 * 两个数相减得到一个double结果
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double subDouble(String dataOne,String dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);   
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal subtract = b1.subtract(b2);
		subtract.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		return subtract.doubleValue();
	}
	/**
	 * 小数相减
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double subDoubleString(double dataOne,double dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);   
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal subtract = b1.subtract(b2);
		subtract.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
		return subtract.doubleValue();
	}


	/**
	 * 两个long进行相加。
	 * @param dataOne
	 * @param dataTwo
     * @return
     */
	public static long addLong(long dataOne, long dataTwo) {
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal sumLong = b1.add(b2);
		return sumLong.longValue();
	}

	/**
	 * 两个long型进行相减。
	 * @param dataOne
	 * @param dataTwo
     * @return
     */
	public static long subLong(long dataOne, long dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal subtract = b1.subtract(b2);
		return subtract.longValue();
	}

	/**
	 * 处理两个大数据相乘
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static long multi(String dataOne,String dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);   
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal multiply = b1.multiply(b2);
		return multiply.longValue();
	}

	/**
	 * 处理两个数据相乘
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static int multi(float dataOne,int dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal multiply = b1.multiply(b2);
		return multiply.intValue();
	}
	
	/**
	 * 处理两个数据相乘
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static int multi(String dataOne,int dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal multiply = b1.multiply(b2);
		return multiply.intValue();
	}
	
	/**
	 * 处理两个大数据相乘返回double类型数据
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double multiDouble(String dataOne,String dataTwo){
		BigDecimal b1 = new BigDecimal(dataOne);   
		BigDecimal b2 = new BigDecimal(dataTwo);
		BigDecimal multiply = b1.multiply(b2);
		return multiply.doubleValue();
	}
	/**
	 * 除法精确小数点后两位
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double divDouble(String dataOne,String dataTwo){
        return divDouble(dataOne,dataTwo,2);

	}
	/**
	 * 除法精确小数点后两位
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double divDoubleForFour(String dataOne,String dataTwo){
        return divDouble(dataOne,dataTwo,4);

	}
	/**
	 * 除法精确小数点后十位
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double divDoubleForNine(String dataOne,String dataTwo){
		return divDouble(dataOne, dataTwo,9);
	}
	
    /**
     * 除法精确小数点后n位
     * @param dataOne
     * @param dataTwo
     * @param n
     * @return
     */
    public static double divDouble(String dataOne,String dataTwo,int n){
        BigDecimal b1 = new BigDecimal(dataOne);
        BigDecimal b2 = new BigDecimal(dataTwo);
        return b1.divide(b2,n,BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

	/**
	 * 数据转换成double后除以100
	 * @param sourceData
	 * @return
	 */
	public static double dataDivHundrend(Long sourceData){
		double divDouble = DataUtil.divDouble(Long.toString(sourceData),"100");//数据库中取出来的除以100
		return divDouble;
	}
	/**
	 * Integer数据转换成double后除以100
	 * @param sourceData
	 * @return
	 */
	public static double dataDivHundrendInteger(Integer sourceData){
		double divDouble = DataUtil.divDouble(Long.toString(sourceData),"100");//数据库中取出来的除以100
		return divDouble;
	}
	/**
	 * Long数据出库除以100
	 * @param sourceData
	 * @return
	 */
	public static long dataDivHundrendLong(Long sourceData) {
		BigDecimal bd = new BigDecimal(sourceData);
		return bd.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_DOWN).longValue();
	}
	/**
	 * int类型数据除以100后转换成字符串
	 * @param sourceData
	 * @return
	 */
	public static String dataDivHundredOfInt(Integer sourceData){
		if(sourceData == null){
			return null;
		}
		return formatSourceOfDouble(DataUtil.divDouble(Integer.toString(sourceData),"100"));//数据库中取出来的除以100
	}
	
	/**
	 * int类型数据除以100后转换成字符串(小数位保留n位）
	 * @param sourceData
	 * @return
	 */
	public static String dataDivHundredOfInt(Integer sourceData,int n){
		if(sourceData == null)
			return null;
		return formatSourceOfDouble(DataUtil.divDouble(Integer.toString(sourceData),"100"),n,RoundingMode.DOWN);
	}
	
	/**
	 * long类型数据除以100后转换成字符串(小数位保留n位）
	 * @param sourceData
	 * @return
	 */
	public static String dataDivHundredOfLong(Long sourceData,int n){
		if(sourceData == null)
			return null;
		return formatSourceOfDouble(DataUtil.divDouble(Long.toString(sourceData),"100"),n,RoundingMode.DOWN);
	}
	
	/**
	 * 带小数点的字符串扩大一百倍后转换成long类型
	 * @param sourceData
	 * @return
	 */
	public static long dataConstarateToLongFromDouble(String sourceData){
		if(org.apache.commons.lang.StringUtils.isEmpty(sourceData)){
			return 0l;
		}
		return StringUtil.parseDoubleToLongString(formatSourceOfDouble(multiDouble(sourceData,"100")));
	}
	
	/**
	 * 格式化double类型数据
	 * @param souce
	 * @return
	 */
	public static String formatSourceOfDouble(double souce){
		 DecimalFormat df = new DecimalFormat("########0.00");
		 return df.format(souce);
	}

	/**
	 * 格式化double类型数据(小数点后面n位）
	 * @param souce
	 * @return
	 */
	public static String formatSourceOfDouble(double souce,int n){
		StringBuffer  decimalPartSb = new StringBuffer();
		for(int i=0;i<n;i++){
			decimalPartSb.append("0");
		}
		DecimalFormat df = new DecimalFormat("########0."+decimalPartSb.toString());
		return df.format(souce);
	}

	/**
	 * 格式化double类型数据(小数点后面n位）
	 * @param souce
	 * @return
	 */
	public static String formatSourceOfDouble(double souce, int n, RoundingMode roundingMode){
		StringBuffer  decimalPartSb = new StringBuffer();
		for(int i=0;i<n;i++){
			decimalPartSb.append("0");
		}
		DecimalFormat df = new DecimalFormat("########0."+decimalPartSb.toString());
		df.setRoundingMode(roundingMode);
		return df.format(souce);
	}

	/**
	 * 除以100并格式化
	 * @param sourceData
	 * @return
     */
	public static String dataDivHundredAndFormat(Long sourceData){
		return formatSourceOfDouble(dataDivHundrend(sourceData));
	}
	/**
	 * 格式化double类型数据
	 * @param souce
	 * @return
	 */
	public static String formatSourceOfDoubleForFour(double souce){
		 DecimalFormat df = new DecimalFormat("########0.0000");
		 return df.format(souce);
	}
	
	/**
	 * 两个long类型的数据相除,精确小数点后两位
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double divDoubleForTwoLong(long dataOne,long dataTwo){
		double data = (double)dataOne/dataTwo;
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		return Double.parseDouble(df.format(data));
	}
	
	/**
	 * 两个long类型的数据相除,精确小数点后4位
	 * @param dataOne
	 * @param dataTwo
	 * @return
	 */
	public static double divDoubleForTwoLongFor4Decimal(long dataOne,long dataTwo){
		double data = (double)dataOne/dataTwo;
		DecimalFormat df = new DecimalFormat("0.0000");//格式化小数，不足的补0
		return Double.parseDouble(df.format(data));
	}

	/**
	 * bigDecimal数值,精确到小数点后面2位
	 * @param bigDecimal
     * @return
     */
	public static String decimalFormat(BigDecimal bigDecimal){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(bigDecimal.doubleValue());
	}

	/**
	 * bigdecimal除以100后 精确到小数点后面n位
	 * @param bigDecimal
	 * @param n
     * @return
     */
	public static float divHundrendFloat(BigDecimal bigDecimal,int n){
		BigDecimal result = bigDecimal.divide(new BigDecimal("100"),n,BigDecimal.ROUND_HALF_DOWN);
		return result.floatValue();
	}

	/**
	 * data转成bigdecimal后 乘以100 返回
	 * @param data
	 * @return
     */
	public static BigDecimal multiHundrendFloat(float data){
		BigDecimal b1 = new BigDecimal(""+data);
		BigDecimal b2 = new BigDecimal("100");
		return b1.multiply(b2);
	}

	public static boolean isNumber(String str){
		String reg = "^[0-9]+(.[0-9]+)?$";
		return str.matches(reg);
	}

	public static Long double2Long(Double data) {
		if (data == null) {
			return null;
		}
		return data.longValue();
	}

	public static Long nullPoint2Zero(Long num) {
		return num == null ? 0L : num;
	}


	public static void main(String[] args) {
		
		long multi2 = multi("262.44", "100");
		System.out.println("multi2=" + multi2);
		
		double subDouble2 = subDouble("26900", "26244");
		System.out.println("subDouble2=" + subDouble2);
		
		System.out.println(divDouble(String.valueOf(subDouble2), "100"));
		
		
		String dataOne = "1000000000010";
		double dataTwo = 10.00;
		BigDecimal b1 = new BigDecimal(dataOne);
		double doubleValue = b1.doubleValue();
		System.out.println(formatSourceOfDouble(doubleValue - dataTwo));
		
		String formatSourceOfDouble = formatSourceOfDouble(1.01);
		System.out.println(formatSourceOfDouble);
		
		double divDouble = divDouble("10", "3");
		System.out.println("divDouble=" + divDouble);
		
		double subDouble = subDouble("1.0", "0.9");
		System.out.println(subDouble);
		
		BigDecimal bd = new BigDecimal("1000000022555");
		System.out.println(bd.doubleValue());
		double doubleValue2 = bd.doubleValue();
		
		System.out.println(formatSourceOfDouble(doubleValue2));
		
		System.out.println(multi(String.valueOf(doubleValue2),"100"));
		
		
		double divDouble2 = divDouble("300","10000");
		System.out.println("divDouble2=" + divDouble2);
		double divDoubleForTen = divDoubleForNine("0.014", "360");
		
		System.out.println("-------------===================");
		double preIncome = divDoubleForTen * 370 * 1000000;
		System.out.println(preIncome);
		System.out.println(DataUtil.formatSourceOfDoubleForFour(preIncome));
		long multi = multi(DataUtil.formatSourceOfDoubleForFour(preIncome),"100");
		
		long dd = 599999999 / 1000000;
		System.out.println(dd);
		System.out.println(divDoubleForTen);
		System.out.println(multi);
		
		
		System.out.println("################################################");
		
		double divDouble3 = DataUtil.divDouble("730", "100");
		System.out.println(DataUtil.formatSourceOfDouble(DataUtil.divDouble("730", "100")));
		

	}
}
