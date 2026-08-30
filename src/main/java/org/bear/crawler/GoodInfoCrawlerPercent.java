package org.bear.crawler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.bear.dao.StockDistributionDao;
import org.bear.parser.GoodInfoDistributionPercentParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.openqa.selenium.JavascriptExecutor;
public class GoodInfoCrawlerPercent extends ImportStockID
{
	private static final String DEBUG_HOST = "127.0.0.1";
	private static final int DEBUG_PORT = 9222;
	public static void main(String[] args)
	{
		String[] dateString = {"20260814"};		
		String[] week = {"26W33"};
		GoodInfoCrawlerPercent distribution = new GoodInfoCrawlerPercent();
		distribution.conn(dateString, week);
	}
	public void conn(String[] dateString, String[] week)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
		StockDistributionDao dao = (StockDistributionDao)context.getBean("stockDistributionDao");	
		//SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		List<String> stockList = new ArrayList<String>();
		String STOCK_ID = "";
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/bear/Desktop/StockList.txt"));
			String readData;			
			while((readData = reader.readLine()) != null)
			{
				stockList.add(readData.trim());
			}
			reader.close();			
			//
			// Step 1.
			// 檢查 9222 是否已啟動
			//
			if (!isDebugChromeRunning())
			{
				System.out.println("Chrome Debug Mode未啟動，準備啟動...");
				startChromeDebug();
				//
				// 等待 Chrome 啟動
				//
				Thread.sleep(5000);
			}
			else
			{
				System.out.println("Chrome Debug Mode已存在");
			}

			//
			// Step 2.
			// Attach Existing Chrome
			//
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
			WebDriver driver = new ChromeDriver(options);
			//
			// Step 3.
			// 開啟 GoodInfo
			//
			for (int j = 0; j < stockList.size(); j++)
			{
				STOCK_ID = stockList.get(j);				
				driver.get("https://goodinfo.tw/tw/EquityDistributionClassHis.asp?STOCK_ID=" + STOCK_ID);
				Thread.sleep(3000);
    			//
    			// Step 4.
    			// 取得資料
    			//
    			/*
    			System.out.println("Title = " + driver.getTitle());
    			System.out.println("URL = " + driver.getCurrentUrl());
    			String html = driver.getPageSource();
    			System.out.println("HTML Length = " + html.length());*/
				String postString =
				        "STOCK_ID=" + STOCK_ID
				        + "&CHT_CAT=WEEK"
				        + "&STEP=DATA"
				        + "&DISPLAY_CAT=%E6%8C%81%E6%9C%89%E6%AF%94%E4%BE%8B%E5%8D%80%E9%96%93%E5%88%86%E7%B4%9A%E4%B8%80%E8%A6%BD%28%E5%AE%8C%E6%95%B4%29";

				((JavascriptExecutor) driver).executeScript("window.goodinfoResult = null;");

				String script =
				        "fetch('https://goodinfo.tw/tw/data/EquityDistributionClassHis.asp', {"
				      + "method:'POST',"
				      + "headers:{"
				      + "'Content-Type':'application/x-www-form-urlencoded'"
				      + "},"
				      + "body:'" + postString + "'"
				      + "})"
				      + ".then(r => r.text())"
				      + ".then(d => window.goodinfoResult = d);";

				((JavascriptExecutor) driver).executeScript(script);
				
				String html = null;
				for (int retry = 0; retry < 10; retry++)
				{
					Thread.sleep(1000);
					html = (String) ((JavascriptExecutor) driver).executeScript("return window.goodinfoResult;");
				    if (html != null)
				    {
				        break;
				    }
				}
    			//
    			// 5. Parser
    			//
    			GoodInfoDistributionPercentParser parser = new GoodInfoDistributionPercentParser();
    			parser.parse(html, dateString, week, dao, STOCK_ID, true);
    			Thread.sleep(5000);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * 檢查 9222 Port 是否存在
	 */
	private boolean isDebugChromeRunning()
	{
		try (Socket socket = new Socket(DEBUG_HOST, DEBUG_PORT))
		{
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	/**
	 * 啟動 Chrome Debug Mode
	 */
	private void startChromeDebug() throws Exception
	{
		String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
		ProcessBuilder pb = new ProcessBuilder(chromePath, "--remote-debugging-port=9222",
				"--user-data-dir=C:\\ChromeDebug");
		pb.start();
		System.out.println("Chrome Debug Mode 已啟動");
	}
}