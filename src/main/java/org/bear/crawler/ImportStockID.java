package org.bear.crawler;

import java.util.List;

import org.bear.dao.BasicStockDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author edward
 * 去資料庫擷取所有股票代碼
 */
public class ImportStockID 
{
	protected BasicStockDao basicStockDao;
	protected List <BasicStockWrapper> wrapperList;
	public ImportStockID()
	{
		//擷取所有股票代碼
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		basicStockDao = (BasicStockDao)context.getBean("basicStockDao");
		wrapperList = basicStockDao.findAllData();
		//wrapperList = basicStockDao.findStockTypeData("1");
		//wrapperList = basicStockDao.findStockTypeData("2");
	}
}
