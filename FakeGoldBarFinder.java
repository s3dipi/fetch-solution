// Solution written with Java implementation

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class FakeGoldBarFinder {
    static WebDriver driver;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        
        try {
            driver.get("http://sdetchallenge.fetch.com/");
            findFakeGoldBar();
        } finally {
            driver.quit();
        }
    }

    public static void findFakeGoldBar() {
        int fakeBar = -1;
        String result;

        result = weighBars(new int[]{0, 1, 2}, new int[]{3, 4, 5});
        if (result.equals("left")) {
            fakeBar = determineFakeBar(new int[]{0, 1, 2});
        } else if (result.equals("right")) {
            fakeBar = determineFakeBar(new int[]{3, 4, 5});
        } else {
            fakeBar = determineFakeBar(new int[]{6, 7, 8});
        }

        driver.findElement(By.id("button-" + fakeBar)).click();

        System.out.println(driver.switchTo().alert().getText());

        List<WebElement> weighings = driver.findElements(By.cssSelector(".weighings li"));
        System.out.println("Number of weighings: " + weighings.size());
        for (WebElement weighing : weighings) {
            System.out.println(weighing.getText());
        }
    }

    private static String weighBars(int[] leftBars, int[] rightBars) {
        resetScale();
        for (int i = 0; i < leftBars.length; i++) {
            driver.findElement(By.id("left-" + i)).sendKeys(String.valueOf(leftBars[i]));
        }
        for (int i = 0; i < rightBars.length; i++) {
            driver.findElement(By.id("right-" + i)).sendKeys(String.valueOf(rightBars[i]));
        }
        driver.findElement(By.id("weigh")).click();
        return driver.findElement(By.id("result")).getText();
    }

    private static void resetScale() {
        driver.findElement(By.id("reset")).click();
    }

    private static int determineFakeBar(int[] bars) {
        String result = weighBars(new int[]{bars[0]}, new int[]{bars[1]});
        if (result.equals("left")) {
            return bars[0];
        } else if (result.equals("right")) {
            return bars[1];
        } else {
            return bars[2];
        }
    }
}
