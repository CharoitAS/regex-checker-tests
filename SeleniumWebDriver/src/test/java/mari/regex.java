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
import java.util.*;

public class regex {

     WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        deleteAllObjects();
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
    private By viewerObjNameLocator = By.id("viewer_obj_name");
    private By sidebarObjectsLocator = By.cssSelector("#sidebar-objects option");
    
    

    @Test
    public void regex1() throws InterruptedException
    {
        String name = "My Regex";
        driver.navigate().to(url);
        createRegex(name, "a(a|b)*" );
        Thread.sleep(1000);
        String text = driver.findElement(viewerObjNameLocator).getText();
        Assertions.assertEquals(name, text);
        deleteCurrentObject();
    }

    @Test
    public void regex2() throws InterruptedException
    {
        String name = "<script>alert('XSS')</script>";
        driver.navigate().to(url);
        createRegex(name, "a|b(c|d)*z|x*v*r(samsa|f|g)" );
        Thread.sleep(1000);
        String text = driver.findElement(viewerObjNameLocator).getText();
        Assertions.assertEquals("scriptalert(XSS)/script", text);
        deleteCurrentObject();
    }

     @Test
    public void regex3() throws InterruptedException
    {
        String name = "My Regex";
        driver.navigate().to(url);
        createRegex(name, "*a" );
        Thread.sleep(1000);
        driver.switchTo().alert().accept();
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

    public void deleteAllObjects() throws InterruptedException
    {
        driver.navigate().to(url);
        
        while (true){
            List<WebElement> objects = driver.findElements(sidebarObjectsLocator);
            if (objects.isEmpty()) {
                break;
            } 
            objects.get(0).click();
            Thread.sleep(500);
            deleteCurrentObject();
            Thread.sleep(500);
        }
        
    }
}
