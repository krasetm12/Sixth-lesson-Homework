package pages;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
public class MyListsPageObject extends MainPageObject{

    public static final String
            
            FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
    public void openFolderByName(String folder_name) {

        String folder_name_after = getFolderByName(folder_name);
        this.waitForElementAndClick(
                
                folder_name_after,
                "Cannot find folder by name " + folder_name,
                10
        );
    }
    public void openArticleByTitle(String article_title) {

        String article_xpath = getSavedArticleByTitle(article_title);
        this.waitForElementAndClick(
                
                article_xpath,
                "Cannot find folder by name " + article_title,
                10
        );
    }
    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);

        String article_xpath = getFolderByName(article_title);
        this.swipeElementToLeft(
                
                article_xpath,
                "Cannot find saved article"
        );

        this.waitForArticleToDisappearByTitle(article_title);
    }
    public void waitForArticleToDisappearByTitle(String article_title) {

        String article_xpath = getFolderByName(article_title);
        this.waitForElementNotPresent(
                
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }
    public void waitForArticleToAppearByTitle(String article_title) {

        String article_xpath = getFolderByName(article_title);
        this.waitForElementPresent(
                
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15
        );
    }
    private static String getFolderByName(String folder_name) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }
    private static String getSavedArticleByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
}