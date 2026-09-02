package org.bear.crawler;
import java.net.Socket;
import java.util.Set;
import org.bear.parser.GoodInfoParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
/**
 * GoodInfo KD Request
 */
public class GoodInfoRequest
{	
	private static final String DEBUG_HOST = "127.0.0.1";
	private static final int DEBUG_PORT = 9222;
	private final WebDriver driver;
	private GoodInfoParser parser = new GoodInfoParser();
	private Set <String> kdGolden;
	public GoodInfoRequest()
	{
		/*
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
		this.driver = new ChromeDriver(options);*/
		try
		{
			// Step 1.
			// 檢查 9222 是否已啟動
			if (!isDebugChromeRunning())
			{
				startChromeDebug();
				Thread.sleep(3000);
			}
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
			driver = new ChromeDriver(options);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	public GoodInfoRequest(WebDriver driver)
	{
		this.driver = driver;
	}

	public void conn(boolean isDay, String dateString)
	{
		try
		{
			String url;
			if (isDay)
			{
				url = "https://goodinfo.tw/tw/StockList.asp?RPT_TIME=&MARKET_CAT=%E6%99%BA%E6%85%A7%E9%81%B8%E8%82%A1&INDUSTRY_CAT=%E6%97%A5KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89%40%40%E6%97%A5KD%E7%9B%B8%E4%BA%92%E4%BA%A4%E5%8F%89%40%40KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89";

			} 
			else
			{
				url = "https://goodinfo.tw/tw/StockList.asp?RPT_TIME=&MARKET_CAT=%E6%99%BA%E6%85%A7%E9%81%B8%E8%82%A1&INDUSTRY_CAT=%E9%80%B1KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89%40%40%E9%80%B1KD%E7%9B%B8%E4%BA%92%E4%BA%A4%E5%8F%89%40%40KD%E4%BD%8E%E6%96%BC20%E9%BB%83%E9%87%91%E4%BA%A4%E5%8F%89";
			}
			// Step 2.
			// Attach Existing Chrome
			System.out.println("GoodInfo Starting...");
			System.out.println(url);
			// Step 3.
			// 開啟 GoodInfo
			driver.get(url);
			Thread.sleep(3000);
			// Step 4.
			// 取得資料
			String html = driver.getPageSource();
			System.out.println("html: " + html);
			parser.parse(html, dateString, isDay);
			kdGolden = parser.getKdSet();
			System.out.println("Parse Success");
			System.out.println("KD Count = " + (kdGolden == null ? 0 : kdGolden.size()));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public Set<String> getKdSet()
	{
		return kdGolden;
	}
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
	private void startChromeDebug() throws Exception
	{
		String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
		ProcessBuilder pb = new ProcessBuilder(chromePath, "--remote-debugging-port=9222",
				"--user-data-dir=C:\\ChromeDebug");
		pb.start();
		System.out.println("Chrome Debug Mode 已啟動");
	}
}