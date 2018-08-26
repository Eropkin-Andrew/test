package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import tools.Functions;
import tools.Store;

public class Stepdefs {
    private String latest = "http://data.fixer.io/api/latest?symbols=EUR,RUB&access_key=2a537a1a45682824cfea8b11349f36ff";
    private String historical = "http://data.fixer.io/api/%s?symbols=EUR,RUB&access_key=2a537a1a45682824cfea8b11349f36ff";
    private Functions functions = new Functions();

    @When("^User get current exchange rate$")
    public void getRate(){
        Store.getStore().put("expected date", functions.getCurrentDate());
        functions.getRate(latest);
    }

    @When("^User get exchange rate on date \"([^\"]*)\"$")
    public void getRate(String date){
        Store.getStore().put("expected date", date);
        functions.getRate(String.format(historical, date));
    }

    @Then("^User check base currency \"([^\"]*)\"$")
    public void userCheckBaseCurrency(String expectedBase){
        Assert.assertEquals(expectedBase, Store.getStore().safeGet("received base"));
    }

    @Then("^User check historical is absent$")
    public void userCheckHistoricalIsAbsent(){
        Assert.assertEquals("NOT ABSENT", Store.getStore().safeGet("received historical"));
    }

    @Then("^User check historical \"([^\"]*)\"$")
    public void checkHistorical(String expectedHistorical){
        Assert.assertEquals(expectedHistorical, Store.getStore().safeGet("received historical"));
    }

    @Then("^User check date$")
    public void checkDate(){
        Assert.assertEquals(Store.getStore().safeGet("expected date"), Store.getStore().safeGet("received date"));

    }

    @Then("^User check rate \"([^\"]*)\"$")
    public void checkRate(String currency){
        String sum = Store.getStore().safeGet(currency);
        Assert.assertNotEquals("Отсутствует курс для 'EUR'", "NOT FOUND", sum);
        switch(currency){
            case "EUR":
                checkBase(sum);
                break;
            default:
                checkOther(currency, sum);
        }
    }

    private void checkBase(String sum){
        Assert.assertEquals("1", sum);
    }

    private void checkOther(String currency, String sum){
        try{
            Float rub = Float.parseFloat(sum);
            Assert.assertTrue("Курс обмена должен быть положительным", rub > 0 );
        } catch (NumberFormatException e){
            Assert.fail("сохраненное значение '" + currency + "' равное '" + sum +"' не может быть преобразовано в число.");
        }
    }

}