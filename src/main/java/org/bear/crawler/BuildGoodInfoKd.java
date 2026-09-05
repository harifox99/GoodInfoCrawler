package org.bear.crawler;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bear.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * GoodInfo KD資料
 * @author edward
 *
 */
public class BuildGoodInfoKd {

	/**
	 * @param args
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
	public static void main(String[] args)
	{
		//String[] date = {"115/08/28"};
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date today = new Date();        
		String dateString = dateFormat.format(today);
		//dateString = StringUtil.convertChineseYear(dateString.substring(0, 4)) + dateString.substring(4, 10);
		dateString = "115/09/04";
		String[] date = {dateString};
		BuildGoodInfoKd trader = new BuildGoodInfoKd();
		trader.update(date);
	}
	public void update(String[] date)
	{
		for (int i = 0; i < date.length; i++)
		{
			//把民國轉換成西元
			String[] dateArray = date[i].split("/");
			String westenDate = StringUtil.convertYear(dateArray[0]);
			//String westenYear = westenDate;
			westenDate = westenDate + "/" + dateArray[1] + "/" + dateArray[2];
			//KD指標	
			GoodInfoRequest request = null;
			try
			{
				request = new GoodInfoRequest();
    			request.conn(true, westenDate);
    			request.conn(false, westenDate);
			}
			finally
			{
				if (request != null)
				{
					request.close();
				}
    			System.out.println(westenDate + " End!");	
			}
		}
	}
}
