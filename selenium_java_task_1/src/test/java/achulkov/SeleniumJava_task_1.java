package achulkov;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SeleniumJava_task_1 {
    WebDriver driver;
    String baseUrl;
    public void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
    @Before
    public void beforeTest(){
        System.setProperty("webdriver.chrome.driver", "drx/chromedriver.exe");
        baseUrl = "http://www.sberbank.ru/ru/person";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @Test
    public void testinsurance(){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);

        //Нажатие на кнопку Страхование
        driver.findElement(By.xpath("//span[contains(text(),'Страхование')]")).click();

        //Нажатие на кнопку Путешествия и покупки
        WebElement buttonTravel = driver.findElement(By.xpath("//a[@class='lg-menu__sub-link'][contains(text(),'Путешествия и покупки')]"));
        wait.until(ExpectedConditions.visibilityOf(buttonTravel)).click();

        //Проверка наличии текста на странице
        WebElement textInsurationTravel = driver.findElement(By.xpath("//div[@data-pid = 'SBRF-TEXT-1016295']//h3"));
        Assert.assertEquals("Страхование путешественников",textInsurationTravel.getText());

        //Нажатие на кнопку Оформить онлайн
        driver.findElement(By.xpath("//div[@data-pid = 'SBRF_ColList_sb_bundle-9624889']//a[@href='https://online.sberbankins.ru/store/vzr/index.html']")).click();

        //Получение массива вкладок
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());

        //Переход на новую вкладку
        driver.switchTo().window(tabs.get(1));

        //Выбор минимальной страховки
        WebElement buttonMinInsurance = driver.findElement(By.xpath("//div[contains(text(),'Минимальная')]"));
        wait.until(ExpectedConditions.visibilityOf(buttonMinInsurance)).click();

        //Нажатие на кнопку Оформить
        WebElement buttonRegister = driver.findElement(By.xpath("//span[@ng-click='save()' and contains(text(),'Оформить')]"));
        wait.until(ExpectedConditions.visibilityOf(buttonRegister)).click();

        //Заполнение полей
        fillField(By.name("insured0_surname"),"Ivanov");
        fillField(By.name("insured0_name"),"Ivan");
        fillField(By.name("insured0_birthDate"),"01.02.1990");
        fillField(By.name("surname"),"Иванов");
        fillField(By.name("name"),"Иван");
        fillField(By.name("middlename"),"Иванович");
        fillField(By.name("birthDate"),"01.02.1990");
        driver.findElement(By.name("male")).sendKeys("0");
        fillField(By.name("passport_series"),"1111");
        fillField(By.name("passport_number"),"111111");
        fillField(By.name("issuePlace"),"ТП №1 по г. Москва");
        fillField(By.name("issueDate"),"15.02.2008");

        //Проверка на корректное заполнение полей
        Assert.assertEquals("Ivanov",
                driver.findElement(By.xpath("//input[@name='insured0_surname']")).getAttribute("value"));
        Assert.assertEquals("Ivan",
                driver.findElement(By.xpath("//input[@name='insured0_name']")).getAttribute("value"));
        Assert.assertEquals("01.02.1990",
                driver.findElement(By.xpath("//input[@name='insured0_birthDate']")).getAttribute("value"));
        Assert.assertEquals("Иванов",
                driver.findElement(By.xpath("//input[@name='surname']")).getAttribute("value"));
        Assert.assertEquals("Иван",
                driver.findElement(By.xpath("//input[@name='name']")).getAttribute("value"));
        Assert.assertEquals("Иванович",
                driver.findElement(By.xpath("//input[@name='middlename']")).getAttribute("value"));
        Assert.assertEquals("01.02.1990",
                driver.findElement(By.xpath("//input[@name='birthDate']")).getAttribute("value"));
        Assert.assertEquals("0",
                driver.findElement(By.xpath("//input[@name='male']")).getAttribute("value"));
        Assert.assertEquals("1111",
                driver.findElement(By.xpath("//input[@name='passport_series']")).getAttribute("value"));
        Assert.assertEquals("111111",
                driver.findElement(By.xpath("//input[@name='passport_number']")).getAttribute("value"));
        Assert.assertEquals("ТП №1 по г. Москва",
                driver.findElement(By.xpath("//textarea[@name='issuePlace']")).getAttribute("value"));
        Assert.assertEquals("15.02.2008",
                driver.findElement(By.xpath("//input[@name='issueDate']")).getAttribute("value"));

        //Нажатие на кнопку Продолжить
        WebElement buttonContinue = driver.findElement(By.xpath("//span[@ng-click='save()' and contains(text(),'Продолжить')]"));
        wait.until(ExpectedConditions.visibilityOf(buttonContinue)).click();

        //Проверка наличии текста на странице
        WebElement errorFill = driver.findElement(By.xpath("//div[@ng-show='tryNext && myForm.$invalid']"));
        Assert.assertEquals("Заполнены не все обязательные поля",errorFill.getText());
    }

    @After
    public void afterTest(){
        driver.quit();
    }
}
