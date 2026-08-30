package org.bear.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bear.dao.StockDistributionDao;
import org.bear.dao.StockDistributionEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoodInfoDistributionParser
{
    String dateString;

    public void parse(String responseString, String[] dateString, String[] week, StockDistributionDao dao,
	    String stockId, boolean isCurrentMonth)
    {
	for (int i = 0; i < dateString.length; i++)
	{
	    this.dateString = dateString[i];
	    try
	    {
		Document xmlDoc = Jsoup.parse(responseString);
		// (要解析的文件,timeout)
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
			// 得到第1個欄位
			data = tdList.get(0).text().trim();
			if (!data.equals(week[i]))
			    continue;
		    } else
			continue;
		    StockDistributionEntity entity = new StockDistributionEntity();
		    for (int k = 0; k < tdList.size(); k++)
		    {
			long finalData = 0;
			if (k == 0)
			{
			    entity.setStockID(stockId);
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			    if (dateString[i].length() == 8)
			    {
				dateFormat = new SimpleDateFormat("yyyyMMdd");
				Date date = dateFormat.parse(dateString[i]);
				entity.setYearMonth(date);
			    } else if (isCurrentMonth)
			    {
				String month = dateString[i].substring(4, 6);
				String year = dateString[i].substring(0, 4);
				Date date = dateFormat.parse(year + month);
				entity.setYearMonth(date);
			    } else
			    {
				Date date = dateFormat.parse(this.convertMonth());
				entity.setYearMonth(date);
			    }
			} else if (k == 1 || k == 2 || k == 3 || k == 4 || k == 5)
			    continue;
			else
			{
			    double numeral = 0;
			    data = tdList.get(k).text().trim().replace(",", "");
			    numeral = Double.parseDouble(data);
			    numeral = numeral * 10000 * 1000;
			    finalData = Double.valueOf(numeral).longValue();
			}
			if (k == 6)
			    entity.setD1(finalData);
			else if (k == 7)
			    entity.setD1000(finalData);
			else if (k == 8)
			    entity.setD5000(finalData);
			else if (k == 9)
			    entity.setD10000(finalData);
			else if (k == 10)
			    entity.setD15000(finalData);
			else if (k == 11)
			    entity.setD20000(finalData);
			else if (k == 12)
			    entity.setD30000(finalData);
			else if (k == 13)
			    entity.setD40000(finalData);
			else if (k == 14)
			    entity.setD50000(finalData);
			else if (k == 15)
			    entity.setD100000(finalData);
			else if (k == 16)
			    entity.setD200000(finalData);
			else if (k == 17)
			    entity.setD400000(finalData);
			else if (k == 18)
			    entity.setD600000(finalData);
			else if (k == 19)
			    entity.setD800000(finalData);
			else if (k == 20)
			    entity.setD1000000(finalData);
		    }
		    /*
		     * if (entity.getD1() == 0 && entity.getP1000000() == 0) throw new
		     * TdccException(); else
		     */
		    dao.insert(entity, "StockDistributionWeekly");
		}
	    } catch (Exception ex)
	    {
		ex.printStackTrace();
	    }
	}
    }

    private String convertMonth()
    {
	String month = dateString.substring(4, 6);
	String year = dateString.substring(0, 4);
	if (month.equals("01"))
	{
	    int intYear = Integer.parseInt(year);
	    intYear--;
	    return String.valueOf(intYear) + "12";
	} 
	else
	{
	    int intMonth = Integer.parseInt(month);
	    intMonth--;
	    return year + String.valueOf(intMonth);
	}
    }
}
