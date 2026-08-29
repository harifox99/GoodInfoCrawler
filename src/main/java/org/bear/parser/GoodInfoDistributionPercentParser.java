package org.bear.parser;
import org.bear.dao.StockDistributionDao;
import org.bear.dao.StockDistributionEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoodInfoDistributionPercentParser 
{
	String dateString;
	public void parse(String responseString, String[] dateString, String[] week, 
			StockDistributionDao dao, String stockId, boolean isCurrentMonth)
	{
		try
		{
			for (int i = 0; i < dateString.length; i++)
			{
				this.dateString = dateString[i];			
				Document xmlDoc = Jsoup.parse(responseString);
		        //(要解析的文件,timeout)
				Elements tables = xmlDoc.select("table#tblDetail");
				Element table = tables.get(0);
				Elements rows = table.select("tr");
		        for (int j = 0; j < rows.size(); j++)
		        {
		        	Element td = rows.get(j);
	        		Elements tdList = td.select("td");
	        		String data = "";
	        		if (tdList.size() > 0)
	        		{
	        			//得到第1個欄位
	        			data = tdList.get(0).text().trim();
	        			if (!data.equals(week[i]))
	        				continue;
	        		}
	        		else
	        			continue;
	        		StockDistributionEntity entity = dao.query(stockId, this.dateString, "StockDistributionWeekly");
	        		for (int k = 0; k < tdList.size(); k++)
	        		{
	        			double finalData = 0;
	        			if (k == 0)
	        			{}
	        			else if (k == 1 || k == 2 || k == 3 || k == 4 || k == 5)
	        				continue;
	        			else
	        			{
	        				data = tdList.get(k).text().trim();
	        				finalData = Double.parseDouble(data);
	        			}
	        			if (k == 6)
	        				entity.setP1(finalData);
	        			else if (k == 7)
	        				entity.setP1000(finalData);
	        			else if (k == 8)
	        				entity.setP5000(finalData);
	        			else if (k == 9)
	        				entity.setP10000(finalData);
	        			else if (k == 10)
	        				entity.setP15000(finalData);
	        			else if (k == 11)
	        				entity.setP20000(finalData);
	        			else if (k == 12)
	        				entity.setP30000(finalData);
	        			else if (k == 13)
	        				entity.setP40000(finalData);
	        			else if (k == 14)
	        				entity.setP50000(finalData);
	        			else if (k == 15)
	        				entity.setP100000(finalData);
	        			else if (k == 16)
	        				entity.setP200000(finalData);
	        			else if (k == 17)
	        				entity.setP400000(finalData);
	        			else if (k == 18)
	        				entity.setP600000(finalData);
	        			else if (k == 19)
	        				entity.setP800000(finalData);
	        			else if (k == 20)
	        				entity.setP1000000(finalData);
	        		}        		
	        		dao.update(entity, entity.getStockID(), this.dateString, "StockDistributionWeekly");
		        }
			}
		}
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	}
}
