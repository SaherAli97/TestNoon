import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.time.Duration;


public class NoonTestNG {
    WebDriver driver;
    WebDriverWait wait;
    WebElement wishlistButton;
    WebElement homePageButton;
    Actions action;
    WebElement searchBar;
    WebElement cartButton;

 

    @BeforeTest
    public void PrepareDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.noon.com/egypt-en/");
        driver.manage().window().maximize();
    }

    @AfterTest
    public void CloseDriver() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }


    @Test(priority = 1)
    public void OpenSigninPage() {
        WebElement signInButton = driver.findElement(By.xpath("//span[@id='dd_header_signInOrUp']"));
        signInButton.click();
    }

    @Test(priority = 2)
    public void EmailAndPassword() {
        driver.findElement(By.xpath("//input[@id='emailInput']")).sendKeys(//username or email);
        driver.findElement(By.xpath("//p[contains(text(),'Continue')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Log in with password')]"))).click();
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(//password);
    }

    @Test(priority = 3)
    public void ClickOnLogin() {
        driver.findElement(By.xpath("//p[contains(text(),'Log in')]")).click();

        WebElement userName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='userName']")));
        Assert.assertTrue(userName.isDisplayed(), "Login failed: Username not displayed.");
    }

    @Test(priority = 4)
    public void Wishlist () throws InterruptedException {
        wishlistButton = driver.findElement(By.xpath("//span[contains(text(),'Wishlist')]"));
        wishlistButton.click();
        Thread.sleep(5000);
    }
    @Test(priority = 5)
    public void HomePage () throws InterruptedException {
        homePageButton = driver.findElement(By.xpath("//header/div[1]/div[2]/a[1]/div[1]/img[1]"));
        homePageButton.click();
        Thread.sleep(5000);

    }
    @Test(priority = 6)
    public void ReOpenWishList () throws InterruptedException {
        wishlistButton.click();
        Thread.sleep(5000);
    }
    @Test(priority = 7)
    public void Hoover () throws InterruptedException {
        action = new Actions(driver);
        WebElement mobileCategory = driver.findElement(By.xpath("//span[contains(text(),'Mobiles')]"));
        action.moveToElement(mobileCategory).perform();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li:nth-child(1) a:nth-child(1) img:nth-child(1)"))).click();
        Thread.sleep(3000);

    }
    @Test(priority = 8)
    public void SearchBar () throws InterruptedException {
        searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='searchBar']")));
        searchBar.sendKeys("Headphones");
        searchBar.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
    }
    @Test(priority = 9)
    public void PriceFilter () throws InterruptedException {
        WebElement minPrice = driver.findElement(By.xpath("//input[@name='min']"));
        minPrice.clear();
        minPrice.sendKeys("500");
        WebElement maxPrice = driver.findElement(By.xpath("//input[@name='max']"));
        maxPrice.clear();
        maxPrice.sendKeys("2500");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='go']"))).click();
        Thread.sleep(5000);
    }
    @Test(priority = 10)
    public void SliderRating () throws InterruptedException {
        WebElement ratingSlider = driver.findElement(By.className("slider"));
        Actions move = new Actions(driver);
        move.clickAndHold(ratingSlider).moveByOffset(50,0).release().perform();
        Thread.sleep(10000);
    }
    @Test(priority = 11)
    public void AddToCart () throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[id='productBox-N53409767A'] div img[alt='add-to-cart']"))).click();
        cartButton = driver.findElement(By.xpath("//span[normalize-space()='Cart']"));
        cartButton.click();
    }

    @Test(priority = 12)
    public void Checkout () {
        cartButton.click();
        WebElement checkoutButton = driver.findElement(By.xpath("//span[@class='checkout']"));
        checkoutButton.click();
    }

     @AfterMethod
    public void failedScreenshots (ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus())
        {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File ("C:\\FailedTest" + result.getName()+".png"));
        }




    

}












