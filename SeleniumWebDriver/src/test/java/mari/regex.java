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

    private String url = "https://v1470335.hosted-by-vdsina.ru/regex/";
    private By sidebarCreateButtonLocator = By.id("sidebar-create-btn");
    private By sidebarRefreshButtonLocator = By.id("sidebar-refresh-btn");
    private By dialogCreateNameLocator = By.id("dialog-create-name");
    private By dialogCreateConfirmButtonLocator = By.id("dialog-create-confirm-btn");
    private By regexLocator = By.cssSelector("#sidebar-types option[value=\"regex\"]");
    private By createRegexInputLocator = By.id("create-regex-input");
    private By dialogCreateRegexConfirmButtonLocator = By.id("dialog-create-regex-confirm-btn");
    private By deleteButtonLocator = By.id("delete-button");
    private By viewerObjNameLocatir = By.id("viewer_obj_name");
    
    

    @Test
    public void regex1() throws InterruptedException
    {
        String name = "My Regex";
        driver.navigate().to(url);
        createRegex(name, "a(a|b)*" );
        Thread.sleep(1000);
        String text = driver.findElement(viewerObjNameLocatir).getText();
        Assertions.assertEquals(name, text);
        deleteCurrentObject();
    }

    @Test
    public void regex2() throws InterruptedException
    {
        String name = "Long";
        driver.navigate().to(url);
        createRegex(name, "a|b(c|d)*z|x*v*r(samsa|f|g)" );
        Thread.sleep(1000);
        String text = driver.findElement(viewerObjNameLocatir).getText();
        Assertions.assertEquals(name, text);
        deleteCurrentObject();
    }

    public void createRegex(String name, String value)
    {
        driver.findElement(regexLocator).click();
        driver.findElement(sidebarCreateButtonLocator).click();
        driver.findElement(dialogCreateNameLocator).sendKeys(name);
        driver.findElement(dialogCreateConfirmButtonLocator).click();
        driver.findElement(createRegexInputLocator).sendKeys(value);
        driver.findElement(dialogCreateRegexConfirmButtonLocator).click();
    }

    public void deleteCurrentObject()
    {
        driver.findElement(deleteButtonLocator).click();
        driver.switchTo().alert().accept(); //нажать "ок" во всплывающем окне
    }
    
}
