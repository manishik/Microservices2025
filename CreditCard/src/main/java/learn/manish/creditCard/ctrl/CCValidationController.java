package learn.manish.creditCard.ctrl;


import learn.manish.creditCard.model.CreditCard;
import learn.manish.creditCard.service.CCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

//Validation by Credit Card Number

@RestController
@RequestMapping(path = "/validate")
public class CCValidationController {

	private static final Logger logger = LoggerFactory.getLogger(CCValidationController.class);

	private final CCService ccService;

	public CCValidationController(CCService ccService) {
		this.ccService = ccService;
	}

	@PostMapping(path = "/creditCard/{ccNumber}")
	public CreditCard validateCC(@PathVariable("ccNumber") String ccNumber) {
		logger.info("Validating credit card: {}", maskCcNumber(ccNumber));
		return ccService.validateCC(ccNumber);
	}

	private static String maskCcNumber(String ccNumber) {
		if (ccNumber == null || ccNumber.length() < 4) {
			return "****";
		}
		return "****" + ccNumber.substring(ccNumber.length() - 4);
	}

}
