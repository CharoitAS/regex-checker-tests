package mari;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class regex {

     WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void regexGo()
    {
        driver.navigate().to("https://v1470335.hosted-by-vdsina.ru/regex/");
        WebElement createButton = driver.findElement(By.id("sidebar-create-btn"));
        WebElement refreshButton = driver.findElement(By.id("sidebar-refresh-btn"));
        createButton.click();
        //refreshButton.click();
        driver.findElement(By.id("dialog-create-name")).sendKeys("My Regex");
        driver.findElement(By.id("dialog-create-confirm-btn")).click();
    }
    
}
