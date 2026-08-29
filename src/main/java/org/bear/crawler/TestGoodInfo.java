package org.bear.crawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestGoodInfo {

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption(
                "debuggerAddress",
                "127.0.0.1:9222");

        WebDriver driver =
                new ChromeDriver(options);

        System.out.println(
                "Title = " + driver.getTitle());

        System.out.println(
                "URL = " + driver.getCurrentUrl());

        String html =
                driver.getPageSource();

        System.out.println(
                "HTML Length = " + html.length());

        // 不要 quit()
        // 否則會把你的 Chrome 一起關掉
    }
}