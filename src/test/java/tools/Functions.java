package tools;

import io.restassured.response.Response;
import org.junit.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.get;

public class Functions {
    public String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public void getRate(String link){
        Response response = get(link);

        Assert.assertFalse("unsuccessful query",200!=response.statusCode() || !getSafeBoolean(response.jsonPath().get("success")));
        Store.getStore().put("received historical", String.valueOf(getSafeBoolean(response.jsonPath().get("historical"))));
        Store.getStore().put("received base",response.jsonPath().get("base"));
        Store.getStore().put("received date",response.jsonPath().get("date"));
        Map<String,String> currencyes = response.jsonPath().get("rates");
        Store.getStore().put("EUR",String.valueOf(currencyes.get("EUR")));
        Store.getStore().put("RUB",String.valueOf(currencyes.get("RUB")));
    }

    private Boolean getSafeBoolean(Boolean checkMe){
        return Optional.ofNullable(checkMe).orElse(false);
    }

}
