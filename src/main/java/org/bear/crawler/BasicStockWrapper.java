package org.bear.crawler;
/**
 * @author edward
 * 上市公司股票名稱、代碼、種類
 */
public class BasicStockWrapper 
{
	String stockID;
	String stockName;
	int stockType;
	int stockBranch;
	double capital;
	int enabled;
	/**
	 * 在外流通發行股數
	 */
	int sharesOutstanding;
	public int getSharesOutstanding() {
		return sharesOutstanding;
	}
	public void setSharesOutstanding(int sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public int getStockType() {
		return stockType;
	}
	public void setStockType(int stockType) {
		this.stockType = stockType;
	}
	public int getStockBranch() {
		return stockBranch;
	}
	public void setStockBranch(int stockBranch) {
		this.stockBranch = stockBranch;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
}
