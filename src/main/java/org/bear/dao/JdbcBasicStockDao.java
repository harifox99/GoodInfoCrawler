package org.bear.dao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bear.crawler.BasicStockWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcBasicStockDao extends SimpleJdbcDaoSupport implements BasicStockDao 
{
	@Override
	public List<BasicStockWrapper> findAllData() 
	{
		// TODO Auto-generated method stub
		List <BasicStockWrapper> wrapperList = null;
		try
		{
			String sql = "select * from StockData where enabled <> 0";
			wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(BasicStockWrapper.class));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		//Iterator <BasicStockWrapper> iterator = wrapperList.iterator();
		return wrapperList;
	}

	@Override
	public void insertBatch(List<BasicStockWrapper> entity) {
		// TODO Auto-generated method stub
		String sql = "insert into StockData(StockID, StockName, StockType, StockBranch, Enabled) " + 
		"values (:stockID, :stockName, :stockType, :stockBranch, :enabled)";
		List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (BasicStockWrapper iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}

	@Override
	public List<BasicStockWrapper> findStockTypeData(String stockBranch) {
		// TODO Auto-generated method stub
		List <BasicStockWrapper> wrapperList = null;
		String sql = "select * from StockData where enabled <> 0 and StockType <> 17 and stockBranch = '" + stockBranch + "'";
		wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(BasicStockWrapper.class));
		//Iterator <BasicStockWrapper> iterator = wrapperList.iterator();
		return wrapperList;
	}

	@Override
	public void updateCapital(String stockID, String capital) {
		// TODO Auto-generated method stub
		String sql = "update StockData set capital = '" + capital +
		"' where stockID = '" + stockID + "'";
		this.getSimpleJdbcTemplate().update(sql);
	}

	@Override
	public BasicStockWrapper findBasicData(String stockID) {
		// TODO Auto-generated method stub
		try
		{
			String sql = "select * from StockData where stockID = ?";
			BasicStockWrapper entity = this.getSimpleJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(BasicStockWrapper.class), stockID);
			return entity;
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	@Override
	public List<BasicStockWrapper> findSpecificDate() 
	{		
		Date date = new Date();
		//�]�w����榡
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		//�i���ഫ
		String dateString = dateFormat.format(date);
		int year = Integer.parseInt(dateString.substring(0, 4));
		int month = Integer.parseInt(dateString.substring(4, 6));
		if (month == 1)
		{
			month = 12;
			year = year - 1;
		}
		else
		{
			month = month - 1;
		}
		
		String sql = "select operatingRevenue.StockID, StockData.StockName from operatingRevenue inner join StockData " +
				"on operatingRevenue.StockID = StockData.StockID " +
			    "where (DATEPART(year, YearMonth) = '" + year + "') AND (DATEPART(month, YearMonth) = '" + 
				month + "')" + " and StockData.enabled = 1 order by yearMonth desc";
		System.out.println("SQL: " + sql);
		List <BasicStockWrapper> entityList = this.getSimpleJdbcTemplate().query(sql, 
						BeanPropertyRowMapper.newInstance(BasicStockWrapper.class));
		return entityList;
	}

	@Override
	public List<BasicStockWrapper> findAllDataDesc() 
	{
		List <BasicStockWrapper> wrapperList = null;
		try
		{
			String sql = "select * from StockData where enabled <> 0 order by stockId desc";
			wrapperList = this.getSimpleJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(BasicStockWrapper.class));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		//Iterator <BasicStockWrapper> iterator = wrapperList.iterator();
		return wrapperList;
	}

	@Override
	public void updateSharesOutstanding(String stockID, int share) 
	{
		String sql = "update StockData set SharesOutstanding = '" + share +
				"' where stockID = '" + stockID + "'";
				this.getSimpleJdbcTemplate().update(sql);
		
	}

	@Override
	public Map<String, Integer> getSharesOutstanding() 
	{
		List<BasicStockWrapper> list = this.findAllData();
		Map<String, Integer> mapShares = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++)
			mapShares.put(list.get(i).getStockID(), list.get(i).getSharesOutstanding());
		return mapShares;
	}

}
