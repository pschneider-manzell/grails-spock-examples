/*
	This is the Geb configuration file.

	See: http://www.gebish.org/manual/current/configuration.html
*/


import org.openqa.selenium.Dimension
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

// Use phantomjs as the default
//

driver = {
    DesiredCapabilities capabilities =  DesiredCapabilities.phantomjs()
    PhantomJSDriver driver = new PhantomJSDriver(capabilities)
    //Required because the scaffolding plugin create a responsive table which hides columns if the screen size is too small
    driver.manage().window().setSize(new Dimension(1024, 786))
    return driver
}

environments {

    // run as “grails -Dgeb.env=chrome test-app”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chrome {
        driver = { new ChromeDriver() }
    }

    // run as “grails -Dgeb.env=firefox test-app”
    // See: http://code.google.com/p/selenium/wiki/FirefoxDriver
    firefox {
        driver = { new FirefoxDriver() }
    }


}