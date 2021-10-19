package pages;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
public class ArticlePageObject extends MainPageObject{

    public static final String
            
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "id:More options",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[contains(@text,'Add to reading list')]",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "id:android:id/button1",
            CLOSE_ARTICLE_BUTTON = "id:Navigate up",
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";

    public ArticlePageObject(AppiumDriver<?> driver) {
        super(driver);
    }
    public WebElement waitForTitleElement() {

        return this.waitForElementPresent(
                
                TITLE,
                "Cannot find article title on page",
                15
        );
    }
    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }
    public void checkArticleTitlePresent() {

        this.assertElementPresent(
               
                TITLE,
                "Cannot find article title"
        );
    }

    public void swipeToFooter() {

        this.swipeUpToFindElement(
                
                FOOTER_ELEMENT,
                "cannot find the end of article",
                20
        );
    }
    public void addArticleToMyListForTheFirstTime(String name_of_folder) {

        this.waitForElementAndClick(
                
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                10
        );

        this.waitForElementAndClick(
                
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                10
        );

        this.waitForElementAndClick(
                
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                10
        );

        this.waitForElementAndClear(
                
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                10
        );

        this.waitForElementAndSendKeys(
                
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into article folder input",
                10
        );

        this.waitForElementAndClick(
                
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                10
        );
    }
    public void addArticleIntoExistingMyList(String name_of_folder) {

        this.waitForElementAndClick(
                
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                10
        );

        this.waitForElementAndClick(
                
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                10
        );

        String folder_xpath = getFolderByName(name_of_folder);
        this.waitForElementAndClick(
                
                folder_xpath,
                "Cannot find option to add article to My lists",
                10
        );
    }
    public void closeArticle() {

        this.waitForElementAndClick(
                
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X button",
                10
        );
    }
    private static String getFolderByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
}


