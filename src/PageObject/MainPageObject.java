package PageObject;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import Utilities.Base;
import Utilities.GeneralFunctions;


public class MainPageObject extends Base {
public WebDriver driver;

   GeneralFunctions gf = new GeneralFunctions();
   // Constractor
   public MainPageObject(WebDriver driver) 
   {
   this.driver = driver; 
   } 
    
    // Objects declarations
   @FindBy(how = How.XPATH, using = ".//*[@id='top-navigation'][2]/li/a")
   public WebElement searchField;
   
    //Page Functions
   public void Entrance() throws Exception 
   {
	 gf.DisplayObject(searchField);
     searchField.click(); 
   } 
}
