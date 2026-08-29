package org.bear.dao;

import java.util.List;
import java.util.Map;

import org.bear.crawler.BasicStockWrapper;


public interface BasicStockDao 
{
	public void insertBatch(List<BasicStockWrapper> entity);
	//列出所有股票
	public List<BasicStockWrapper> findAllData();
	//列出所有股票，從最後開始
	public List<BasicStockWrapper> findAllDataDesc();
	//列出上市(branchType=1)或上櫃(branchType=2)股票
	public List<BasicStockWrapper> findStockTypeData(String stockBranch);
	//更新資本額
	public void updateCapital(String stcokID, String capital);
	public BasicStockWrapper findBasicData(String stockID);
	//列出有最新營收的股票
	public List<BasicStockWrapper> findSpecificDate();
	//更新在外流通股數
	public void updateSharesOutstanding(String stockID, int share);
	//查詢在外流通股數
	public Map<String, Integer> getSharesOutstanding();
}
