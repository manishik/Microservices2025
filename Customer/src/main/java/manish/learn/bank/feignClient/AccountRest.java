package manish.learn.bank.feignClient;

import manish.learn.bank.entities.CustomerAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "BANKACCOUNTAPP")
public interface AccountRest {

    @PostMapping(path = "/account/createAccount")
    public CustomerAccount createAccount(CustomerAccount customerAccount);

}
