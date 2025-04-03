package manish.learn.bank.feignClient;

import manish.learn.bank.entities.CustomerAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", path = "/account", url = "${account-service.url}")
public interface AccountRest {

    @PostMapping(path = "/createAccount")
    public ResponseEntity<CustomerAccount> createAccount(@RequestBody CustomerAccount customerAccount);

}
