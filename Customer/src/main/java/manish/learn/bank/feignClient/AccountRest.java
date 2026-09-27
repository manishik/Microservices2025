package manish.learn.bank.feignClient;

import manish.learn.bank.entities.CustomerAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BANKACCOUNTAPP")
public interface AccountRest {

    @PostMapping(path = "/account/createAccount")
    public CustomerAccount createAccount(@RequestBody CustomerAccount customerAccount);

}
