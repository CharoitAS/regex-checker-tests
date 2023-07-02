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
import java.lang.Thread;

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

    private By sidebarCreateButtonLocator = By.id("sidebar-create-btn");
    private By sidebarRefreshButtonLocator = By.id("sidebar-refresh-btn");
    private By dialogCreateNameLocator = By.id("dialog-create-name");
    private By dialogCreateConfirmButtonLocator = By.id("dialog-create-confirm-btn");
    private By regexLocator = By.id("sidebar-types");
    private By createRegexInputLocator = By.id("create-regex-input");
    private By dialogCreateRegexConfirmButtonLocator = By.id("dialog-create-regex-confirm-btn");
    private By deleteButtonLocator = By.id("delete-button");
    

    @Test
    public void regexGo()
    {
        driver.navigate().to("https://v1470335.hosted-by-vdsina.ru/regex/");
        //WebElement createButton = driver.findElement(By.id("sidebar-create-btn"));
        //WebElement refreshButton = driver.findElement(By.id("sidebar-refresh-btn"));
        driver.findElement(sidebarRefreshButtonLocator);
        driver.findElement(regexLocator);
        driver.findElement(sidebarCreateButtonLocator).click();
        driver.findElement(dialogCreateNameLocator).sendKeys("My Regex");
        driver.findElement(dialogCreateConfirmButtonLocator).click();
        driver.findElement(createRegexInputLocator).sendKeys("a(a|b)*");
        driver.findElement(dialogCreateRegexConfirmButtonLocator).click();
        driver.findElement(deleteButtonLocator).click();
        driver.switchTo().alert().accept(); //нажать "ок" во всплывающем окне

    }
    
}
