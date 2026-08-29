package org.bear.dao;
import java.util.List;
import java.util.Set;

public interface GoodInfoDao 
{
	public void insertBatch(List<GoodInfoEntity> entity);
	public int update(String indexName, String indexValue, String date, String stockId);
	public GoodInfoEntity getData(String stockID, String priceDate);
	public Set<String> getData(String priceDate);
}
