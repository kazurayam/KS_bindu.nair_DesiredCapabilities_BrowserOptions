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
 * TC2
 */

TestObject makeTestObject(String id, String xpath) {
	TestObject tObj = new TestObject(id)
	tObj.addProperty('xpath', ConditionType.EQUALS, xpath)
	return tObj
}

String username = 'kazuakiurayama'
Path outdir = Paths.get("/Users/${username}/tmp")
Files.createDirectories(outdir)
Path downloadedFile = outdir.resolve('SDG_Guidelines_AUG_2019_Final.pdf')
if (Files.exists(downloadedFile)) {
	Files.delete(downloadedFile)
}

WebUI.openBrowser('')

WebUI.navigateToUrl('https://www.un.org/sustainabledevelopment/news/communications-material/')
WebUI.maximizeWindow()

TestObject anchor = makeTestObject('guidlines', '//section[@id="content"]//a[contains(., "guidelines")]')
WebUI.verifyElementPresent(anchor, 10)
WebUI.click(anchor)
WebUI.delay(10)

WebUI.closeBrowser()

// make sure if the pdf file has been successfully downloaded
assert Files.exists(downloadedFile)