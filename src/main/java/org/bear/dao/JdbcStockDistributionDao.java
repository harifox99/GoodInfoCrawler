package org.bear.dao;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class JdbcStockDistributionDao extends SimpleJdbcDaoSupport implements StockDistributionDao 
{
	@Override
	public void insert(StockDistributionEntity entity) {
		// TODO Auto-generated method stub
		String sql = "insert into StockDistribution(StockID, YearMonth, D1, D1000, " +
		"D5000, D10000, D15000, D20000, D30000, D40000, D50000, D100000, D200000, " +
		"D400000, D600000, D800000, D1000000, D3Big, " +
		"P1, P1000, P5000, P10000, P15000, P20000, P30000, P40000, P50000, P100000, " +
		"P200000, P400000, P600000, P800000, P1000000, P3Big) values " +
		"(:stockID, :yearMonth, :d1, :d1000, :d5000, :d10000, :d15000, :d20000, :d30000, :d40000, " +
		":d50000, :d100000, :d200000, :d400000, :d600000, :d800000, :d1000000, :d3Big, " +
		":p1, :p1000, :p5000, :p10000, :p15000, :p20000, :p30000, :p40000, :p50000, :p100000, " +
		":p200000, :p400000, :p600000, :p800000, :p1000000, :p3Big)";
		//System.out.println("sql: " + sql);
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
		this.getSimpleJdbcTemplate().update(sql, parameterSource);
	}

	@Override
	public List<StockDistributionEntity> latest(String stockID, int duration) 
	{
		duration++;
		String sql = "select top (" + duration + ") * from StockDistribution " +
				"where stockID = '" + stockID + "' order by YearMonth desc"; 
		List <StockDistributionEntity> entityList = this.getSimpleJdbcTemplate().query(sql, 
				BeanPropertyRowMapper.newInstance(StockDistributionEntity.class));
		return entityList;
	}

	@Override
	public StockDistributionEntity query(String stockID, String dateString, String tableName) 
	{
		try
		{
			String sql = "select * from " + tableName + " where stockID = ? and yearMonth = ?";
			System.out.println(sql);
			StockDistributionEntity entity = getJdbcTemplate().queryForObject(sql,
					new Object[]{stockID, dateString}, 
					BeanPropertyRowMapper.newInstance(StockDistributionEntity.class));
			return entity;
		}
		catch (EmptyResultDataAccessException ex)
		{
			return null;
		}
	}

	@Override
	public void update(StockDistributionEntity entity, String stockID, String yearMonth, String tableName) 
	{
		String sql = "update " + tableName + " set " + 
	                 "p1 = ?, p1000 = ?, p5000 = ?, p10000 = ?, p15000 = ?, " +
	                 "p20000 = ?, p30000 = ?, p40000 = ?, p50000 = ?, p100000 = ?, " +
	                 "p200000 = ?, p400000 = ?, p600000 = ?, p800000 = ?, p1000000 = ? " +
	                 "where stockID = ? and yearMonth = ?";
	                  
		try
		{
			int result = this.getSimpleJdbcTemplate().update(sql, 
					entity.getP1(), entity.getP1000(), entity.getP5000(), entity.getP10000(), entity.getP15000(), 
					entity.getP20000(), entity.getP30000(), entity.getP40000(), entity.getP50000(), entity.getP100000(), 
					entity.getP200000(), entity.getP400000(), entity.getP600000(), entity.getP800000(), entity.getP1000000(),
					stockID, yearMonth);
			if (result <= 0)
			{
				System.out.println("Update StockDistributionEntity Error!");
				System.exit(result);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void insert(StockDistributionEntity entity, String tableName) 
	{
		String sql = "insert into " + tableName + "(StockID, YearMonth, D1, D1000, " +
				"D5000, D10000, D15000, D20000, D30000, D40000, D50000, D100000, D200000, " +
				"D400000, D600000, D800000, D1000000, D3Big, " +
				"P1, P1000, P5000, P10000, P15000, P20000, P30000, P40000, P50000, P100000, " +
				"P200000, P400000, P600000, P800000, P1000000, P3Big) values " +
				"(:stockID, :yearMonth, :d1, :d1000, :d5000, :d10000, :d15000, :d20000, :d30000, :d40000, " +
				":d50000, :d100000, :d200000, :d400000, :d600000, :d800000, :d1000000, :d3Big, " +
				":p1, :p1000, :p5000, :p10000, :p15000, :p20000, :p30000, :p40000, :p50000, :p100000, " +
				":p200000, :p400000, :p600000, :p800000, :p1000000, :p3Big)";
				//System.out.println("sql: " + sql);
				SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
				this.getSimpleJdbcTemplate().update(sql, parameterSource);		
	}

}
