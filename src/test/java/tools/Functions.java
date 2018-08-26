package tools;

import io.restassured.response.Response;
import org.junit.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static io.restassured.RestAssured.get;

public class Functions {
    public String getCurrentDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public void getRate(String link){
        Response response = get(link);
        Assert.assertFalse("Нет ответа от апи",200!=response.statusCode());

        storeNullOrBoolean("received success", response.jsonPath().get("success"));
        storeNullOrBoolean("received historical", response.jsonPath().get("historical"));
        Store.getStore().put("received base",response.jsonPath().get("base"));
        Store.getStore().put("received date",response.jsonPath().get("date"));
        Map<String,String> currencyes = response.jsonPath().get("rates");
        Store.getStore().put("EUR",String.valueOf(currencyes.get("EUR")));
        Store.getStore().put("RUB",String.valueOf(currencyes.get("RUB")));
    }

    private void storeNullOrBoolean(String key, Boolean storeMe){
        if(null==storeMe){
            Store.getStore().put(key, "NOT ABSENT");
        } else{
            Store.getStore().put(key, String.valueOf(storeMe));
        }
    }
}
