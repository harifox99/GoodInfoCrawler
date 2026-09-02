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
	private static final String CHROME_PROFILE = "C:\\ChromeDebug";
	private GoodInfoParser parser = new GoodInfoParser();
	private Set <String> kdGolden;
	/**
	 * 
	 */
	public GoodInfoRequest()
	{
		try
		{
			if (!isDebugChromeRunning())
			{
				startChromeDebug();
				boolean ok = false;
				for (int i = 0; i < 10; i++)
				{
					if (isDebugChromeRunning())
					{
						ok = true;
						break;
					}
					Thread.sleep(1000);
				}
				if (!ok)
				{
					throw new RuntimeException("Chrome Debug Mode 啟動失敗");
				}
			}
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("debuggerAddress", DEBUG_HOST + ":" + DEBUG_PORT);
			driver = new ChromeDriver(options);
			System.out.println("Chrome Debug Mode 連線成功");
			System.out.println("Chrome Profile : " + CHROME_PROFILE);
		}
		catch (Exception e)
		{
			throw new RuntimeException("初始化 ChromeDriver 失敗", e);
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
			Thread.sleep(5000);
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
	
    /**
     * 
     * @throws Exception
     */
	private void startChromeDebug() throws Exception
	{
		String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
		ProcessBuilder pb = new ProcessBuilder(chromePath, "--remote-debugging-port=" + DEBUG_PORT,
				"--user-data-dir=" + CHROME_PROFILE, "--no-first-run", "--disable-default-apps");
		pb.start();
		System.out.println("Chrome Debug Mode 已啟動");
	}

	/**
	 * 增加一個關閉 Chrome 的方法：
	 */
	public void close()
	{
		try
		{
			if (driver != null)
			{
				driver.quit();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}