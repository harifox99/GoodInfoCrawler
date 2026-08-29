package org.bear.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * GoodInfo KD Index DAO
 * @author capital20180413
 *
 */
public class JdbcGoodInfoDao extends SimpleJdbcDaoSupport implements GoodInfoDao 
{
	public void insertBatch(List<GoodInfoEntity> entity) 
	{
		String sql = "insert into GoodInfo(ExchangeDate, StockId, Price, Day_K, Day_D, " +
			"Week_K, Week_D, Day_KD_20, Week_KD_20) " +
			"values (:exchangeDate, :stockId, :price, :day_k, :day_d, " +
			":week_k, :week_d, :day_kd_20, :week_kd_20)";
			List <SqlParameterSource> parameters = new ArrayList <SqlParameterSource>();
		for (GoodInfoEntity iterator:entity)
		{
			parameters.add(new BeanPropertySqlParameterSource(iterator));
		}		
		this.getSimpleJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
	}
	public int update(String indexName, String indexValue, String date, String stockId) {
		// TODO Auto-generated method stub
		String sql = "UPDATE GoodInfo SET " + indexName + " = ? where Exchangedate = '" + date + "' and stockId = '" + stockId + "'";
		System.out.println("SQL, GoodInfo: " + sql);
		int result = this.getSimpleJdbcTemplate().update(sql, indexValue);
		return result;
	}
	
	public GoodInfoEntity getData(String stockID, String priceDate)
	{
		try
		{
			String sql = "select * from GoodInfo where stockID = '" + stockID + "' and exchangeDate = '" + priceDate + "'";
			GoodInfoEntity entity = this.getSimpleJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(GoodInfoEntity.class));
			return entity;
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	public Set<String> getData(String priceDate)
	{
		try
		{
			String sql = "select stockid from GoodInfo where exchangeDate = '" + priceDate + "' and Day_KD_20 = 'Y' and Week_KD_20 = 'Y'";
			//System.out.println("SQL: " + sql);
			List<String> data = (List<String>)getJdbcTemplate().queryForList(sql, String.class);
			Set<String> entity = new HashSet<String>(data);
			return entity;
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}
