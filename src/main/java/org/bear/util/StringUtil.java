package org.bear.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 自製String Utility
 * @author edward
 *
 */
public class StringUtil 
{
	/**
	 * 看起來是移除特殊字元
	 * @param string
	 * @return
	 */
	public static String eraseSpecialChar(String string)
	{
		if (string.contains("$"))
		{
			string = string.replace("$", "");
		}
		if (string.contains("("))
		{
			string = string.replace("(", " ");
		}
		if (string.endsWith(")"))
		{
			string = string.replace(")", "");
			string = "-" + string;
		}
		if (string.contains(","))
		{
			string = string.replace(",", "");
		}
		if (string.contains("."))
		{
			string = string.replace(".", "");
		}
		if (string.contains("　"))
		{
			string = string.replace("　", "");
		}
		return string;
	}
	public static double setPointLength(double number)
	{
		NumberFormat formatter = new DecimalFormat("##.##");
		number = Double.parseDouble(formatter.format(number));
		return number;
	}
	public static double setPointLength(double number, int length)
	{
		String decimal = "##.";
		for (int i = 0; i < length; i++)
		{
			decimal = decimal + "#";
		}
		NumberFormat formatter = new DecimalFormat(decimal);
		number = Double.parseDouble(formatter.format(number));
		return number;
	}
	public static String setPointLength(String number, int length)
	{
		String decimal = "##.";
		for (int i = 0; i < length; i++)
		{
			decimal = decimal + "#";
		}
		NumberFormat formatter = new DecimalFormat(decimal);
		number = formatter.format(Double.parseDouble(number));
		return number;
	}
	/**
	 * 精確的double減法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double sub(double v1, double v2) 
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	/**
	 * 將民國轉成西元年
	 * @param chineseYear
	 * @return
	 */
	public static String convertYear(String chineseYear)
	{
		int intYear = Integer.parseInt(chineseYear) + 1911;
		String year = String.valueOf(intYear);
		return year;
	}
	/**
	 * 將西元年轉成民國
	 * @param year
	 * @return
	 */
	public static String convertChineseYear(String year)
	{
		int intChineseYear = Integer.parseInt(year) - 1911;
		String chineseYear = String.valueOf(intChineseYear);
		return chineseYear;
	}
	/**
	 * 1-9月份數字前+0
	 * @param month
	 * @return
	 */
	public static String addZeroMonth(String month)
	{
		if (month.length() == 1)
			return "0" + month;
		else
			return month;
	}
}
