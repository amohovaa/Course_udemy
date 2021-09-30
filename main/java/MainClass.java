import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainClass {

    static WebDriver driverCH;

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mokhova_aa\\IdeaProjects\\Course_udemy\\drivers\\chromedriver.exe");
        driverCH = new ChromeDriver();
        driverCH.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driverCH.manage().window().maximize();

        driverCH.get("http://www.google.com/"); //открытие браузера
        driverCH.navigate().to("http://test.goodfin.test/"); //переход по ссылке

        WebElement username = driverCH.findElement(By.xpath("//input[@name = 'username' and @type = 'text']"));
        WebElement password = driverCH.findElement(By.xpath("//div[@class='shb-login__content-item']//input[@name = 'password' and @type = 'password']"));
        WebElement button = driverCH.findElement(By.xpath("//div[@class = 'shb-login__content-item']/button[@class='shb-button shb-button_blue']"));

        username.sendKeys("mokhova_agent");
        password.sendKeys("mokhova_agent1!");
        button.click();

        WebElement role = driverCH.findElement(By.xpath("//button[@class='shb-select-role__list-item-btn' and text() = ' Администратор компании-агента ']"));
        role.click();

        WebElement mycompany = driverCH.findElement(By.xpath("//a[@href='/my-company']"));
        mycompany.click();

        selectRadioButton("OSNO");
        try {
            WebElement save = driverCH.findElement(By.xpath("//button[text() = ' Сохранить ']"));
            save.click();
            WebElement close = driverCH.findElement(By.xpath("//button[text() = ' Закрыть ']"));
            close.click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driverCH;
            executor.executeScript("arguments[0].click();", driverCH.findElement(By.xpath("//button[text() = ' Сохранить ']")));
        }

        WebElement deals = driverCH.findElement(By.xpath("//a[@href='/deals']"));
        deals.click();

        List<WebElement> checkboxes= driverCH.findElements(By.xpath("//div[@class = 'box box-body box-primary']//shb-deals-list-common//div[@class='shb-input-checkbox ng-star-inserted']"));
        for(WebElement checkbox : checkboxes) {
            checkbox.click();
        }

        Actions actions = new Actions(driverCH);
        actions.moveToElement(checkboxes.get(4)).build().perform(); //переход на пятую сделку (индекс 4)

//        driverCH.quit(); //автоматическое закрытие браузера

    }

    public static void selectRadioButton(String name){
        String pattern = "//input[@value='%s']";
        try {
            driverCH.findElement(By.xpath(String.format(pattern, name))).click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driverCH;
            executor.executeScript("arguments[0].click();", driverCH.findElement(By.xpath(String.format(pattern, name))));
        }
    }
}
