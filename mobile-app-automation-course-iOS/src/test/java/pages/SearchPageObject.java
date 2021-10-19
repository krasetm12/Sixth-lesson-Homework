package pages;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
public class SearchPageObject extends MainPageObject{

    private static final String
            
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_RESULT_ELEMENT_BY_ORDER_TPL = "xpath://android.widget.LinearLayout[{NUMBER}][@resource-id='org.wikipedia:id/page_list_item_container']//android.widget.TextView[1]",
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{TITLE}']/..//*[@text='{DESCRIPTION}']";

    public SearchPageObject(AppiumDriver<?> driver) {
        super(driver);
    }
    public void initSearchInput() {

        this.waitForElementPresent(
                
                SEARCH_INIT_ELEMENT,
                "Cannot find field search init"
        );
        this.waitForElementAndClick(
                
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                10
        );
    }
    public void waitForCancelButtonToAppear() {

        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button"
        );
    }

    public void waitForCancelButtonToDisappear() {

        this.waitForElementNotPresent(
                
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is steel present",
                10
        );
    }
    public void clickCancelSearch() {

        this.waitForElementAndClick(
               
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                10
        );
    }
    public void typeSearchLine(String search_line) {

        this.waitForElementAndSendKeys(
                
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search init",
                10
        );
    }
    public void verifyTextInSearchLine(String search_line) {

        this.waitForElementPresent(
                
                SEARCH_INPUT,
                "Cannot find entry field",
                10
        );

        this.assertElementHasText(
                
                SEARCH_INPUT,
                search_line,
                "Cannot find expected text in entry field"
        );
    }
    public void waitForSearchResult(String substring) {

        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                
                search_result_xpath,
                "Cannot find search result with substring " + substring
        );
    }
    public void clickByArticleWithSubstring(String substring) {

        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10
        );
    }
    public void waitForElementByTitleAndDescription(String title, String description) {

        String search_result_xpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(
                
                search_result_xpath,
                String.format("Cannot find search result with title '%s' and description '%s'", title, description),
                10
        );
    }
    public int getAmountOfFoundArticles() {

        this.waitForElementPresent(
               
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {

        this.waitForElementPresent(
                
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element"
        );
    }

    public void assertThereIsNoResultOfSearch() {

        this.assertElementNotPresent(
                
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

    public void checkCorrectnessOfSearchResults(String search_text) {

        this.waitForElementPresent(
                
                SEARCH_RESULT_ELEMENT,
                "Cannot find search results",
                10
        );
        int articles_number = driver.findElements(By.xpath(SEARCH_RESULT_ELEMENT)).size();
        int number = 0;
        while (articles_number > number) {
            number++;
            String search_element_xpath = getSearchElementByOrder("" + number);
            WebElement element = this.waitForElementPresent(
                    
                    search_element_xpath,
                    "Search element not found"
            );

            Assertions.assertTrue(
                    element.getAttribute("text").contains(search_text),
                    "Search result does not contain the search word "
            );
        }
    }
    /**
     * TEMPLATEs METHOD
     * @param substring
     * @return
     */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getSearchElementByOrder(String substring) {
        return SEARCH_RESULT_ELEMENT_BY_ORDER_TPL.replace("{NUMBER}", substring);
    }
    private static String getResultSearchElementByTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}",title).replace("{DESCRIPTION}", description);
    }
}