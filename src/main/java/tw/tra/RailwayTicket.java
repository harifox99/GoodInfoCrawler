package tw.tra;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * 台鐵訂票
 */
public class RailwayTicket
{
	private static final String URL = "https://www.trc.com.tw/tra-tip-web/tip/tip001/tip121/query";
	private static String PID = "A124776119";
	private static String START_STATION = "1000-臺北";
	private static String END_STATION = "4220-臺南";
	private static String DATE = "";
	private static String TRAIN_NO = "131";
	private static String TICKET_COUNT = "1";
	public static void main(String[] args) throws Exception
	{
		System.out.println("請至少填2個參數，1. 日期, 2. 車次，日期格式為YYYY/MM/DD，預設為1張票");
		System.out.println("或是6個參數，依序為1. 身分證, 2. 出發站, 3. 抵達站, 4. 搭車日期, 5. 車次, 6. 車票張數，日期格式為YYYY/MM/DD");
		if (args.length != 2 && args.length != 6 && args.length != 0)
		{
			System.out.println("輸入格式錯誤，程式結束");
			System.exit(0);
		}
		else if (args.length == 2)
		{
			DATE = args[0];
			TRAIN_NO = args[1];
		}
		else if (args.length == 6)
		{
			PID = args[0];
			START_STATION = args[1];
			END_STATION = args[2];
			DATE = args[3];
			TRAIN_NO = args[4];
			TICKET_COUNT = args[5];
		}
		checkChromeDebug();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
		WebDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		driver.get(URL);
		Thread.sleep(3000);
		fillForm(driver);
		System.out.println();
		System.out.println("======================");
		System.out.println("請人工完成以下步驟");
		System.out.println("1. 確認資料");
		System.out.println("2. 完成驗證碼");
		System.out.println("3. 完成 reCAPTCHA");
		System.out.println("======================");
		System.out.println();
		System.out.println("完成後按 Enter");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		submit(driver);
		System.out.println("訂票已送出");
	}

	private static void fillForm(WebDriver driver) throws Exception
	{
		click(driver, "personlType");
		setValue(driver, "pid", PID);
		setValue(driver, "startStation", START_STATION);
		setValue(driver, "endStation", END_STATION);
		setValue(driver, "normalQty", TICKET_COUNT);
		setDate(driver, DATE);
		setValue(driver, "trainNoList1", TRAIN_NO);
		System.out.println("資料填寫完成");
	}

	private static void submit(WebDriver driver)
	{
		WebElement submitBtn = driver.findElement(By.cssSelector("input[type='submit'].btn.btn-3d"));
		submitBtn.click();
	}

	private static void click(WebDriver driver, String id)
	{
		try
		{
			driver.findElement(By.id(id)).click();
			System.out.println("CLICK : " + id);
		}
		catch (Exception ex)
		{
			System.out.println("找不到 : " + id);
		}
	}

	private static void setValue(WebDriver driver, String id, String value)
	{
		try
		{
			WebElement element = driver.findElement(By.id(id));
			element.clear();
			element.sendKeys(value);
			System.out.println(id + " = " + value);
		}
		catch (Exception ex)
		{
			System.out.println("找不到欄位 : " + id);
		}
	}

	private static void setDate(WebDriver driver, String dateValue)
	{
		try
		{
			WebElement element = driver.findElement(By.id("rideDate1"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].value = arguments[1];"
					+ "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));", element, dateValue);
			System.out.println("rideDate1 = " + dateValue);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private static void checkChromeDebug() throws Exception
	{
		if (isPortOpen("127.0.0.1", 9222))
		{
			System.out.println("Chrome Debug 已啟動");
			return;
		}
		System.out.println("Chrome Debug 未啟動");
		startChrome();
		int retry = 0;
		while (retry < 20)
		{
			if (isPortOpen("127.0.0.1", 9222))
			{
				System.out.println("Chrome 啟動成功");
				return;
			}
			Thread.sleep(1000);
			retry++;
		}
		throw new RuntimeException("Chrome 啟動失敗");
	}

	private static void startChrome() throws IOException
	{
		String chromeCmd = "\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\" "
				+ "--remote-debugging-port=9222 " + "--user-data-dir=C:\\ChromeDebug";

		new ProcessBuilder("cmd", "/c", chromeCmd).start();
	}

	private static boolean isPortOpen(String host, int port)
	{
		try (Socket socket = new Socket())
		{
			socket.connect(new InetSocketAddress(host, port), 2000);
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
}