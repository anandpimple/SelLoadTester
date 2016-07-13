package gov.tfl.selenium.load;

import org.openqa.selenium.WebDriver;

/**
 * Created by dev on 20/06/16.
 */
public class ScreenPrinterServiceImpl implements ScreenPrinterService {
    @Override
    public void print(WebDriver driver) {
        if(null != driver){
            //
            System.out.println("In print screen functionality");
        }
    }
}
