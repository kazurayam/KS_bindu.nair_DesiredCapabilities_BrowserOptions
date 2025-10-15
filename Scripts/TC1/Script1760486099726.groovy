import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * TC1
 */

// make a Test Object
TestObject makeTestObject(String id, String xpath) {
	TestObject tObj = new TestObject(id)
	tObj.addProperty('xpath', ConditionType.EQUALS, xpath)
	return tObj	
}

// define a folder to save a PDF
String username = 'kazuakiurayama'
Path outdir = Paths.get("/Users/${username}/tmp")
Files.createDirectories(outdir)

// remove the pdf file that were previously downloaded
Path downloadedFile = outdir.resolve('SDG_Guidelines_AUG_2019_Final.pdf')
if (Files.exists(downloadedFile)) {
	Files.delete(downloadedFile)	
}

// setup ChromeOptions
Map<String, Object> prefs = new HashMap<String, Object>();
prefs.put("download.default_directory", outdir.toString());
prefs.put("download.prompt_for_download", false);
prefs.put("download.directory_upgrade",true)
prefs.put("plugins.always_open_pdf_externally", true);
ChromeOptions options = new ChromeOptions();
options.setExperimentalOption("prefs", prefs);

// opewn Chrome browser
WebDriver driver = new ChromeDriver(options);
DriverFactory.changeWebDriver(driver);

// navigate to a URL
WebUI.navigateToUrl('https://www.un.org/sustainabledevelopment/news/communications-material/')
WebUI.maximizeWindow()

// click a link to download & save a PDF into a custom location
TestObject anchor = makeTestObject('guidlines', '//section[@id="content"]//a[contains(., "guidelines")]')
WebUI.verifyElementPresent(anchor, 10) 
WebUI.click(anchor)
WebUI.delay(10)

WebUI.closeBrowser()

// make sure if the pdf file has been successfully downloaded
assert Files.exists(downloadedFile)