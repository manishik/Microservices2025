package learn.manish.creditCard.ctrl;


import learn.manish.creditCard.model.CreditCard;
import learn.manish.creditCard.service.CCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Validation by Credit Card Number

@RestController
@RequestMapping(path = "/validate")
public class CCValidationController {

	Logger logger = LoggerFactory.getLogger(CCValidationController.class);

	@Autowired
	CCService ccService;

	@RequestMapping(path = "/creditCard/{ccNumber}", method = RequestMethod.POST)
	public CreditCard validateCC(@PathVariable("ccNumber") String ccNumber) throws Exception {
		logger.info("CCValidationController: Credit Card Number at Controller Layer = {}", ccNumber);
		return ccService.validateCC(ccNumber);
	}

}
