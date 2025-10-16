package learn.manish.controller;

import learn.manish.model.TokenReqRes;
import learn.manish.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class Controller {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/generate-token")
    public ResponseEntity<Object> generateToken(@RequestBody TokenReqRes tokenReqRes) {
        logger.info("Inside generateToken...");
        if (tokenReqRes.getUsername().equals("manish@gmail.com") && tokenReqRes.getPassword().equals("manish123")) {
            String token = jwtTokenUtil.generateToken(tokenReqRes.getUsername());
            TokenReqRes tokenResponse = new TokenReqRes();
            tokenResponse.setToken(token);
            tokenResponse.setExpirationTime("10 mins");
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Object> validateToken(@RequestBody TokenReqRes tokenReqRes) {
        logger.info("Inside validateToken...");
        return ResponseEntity.ok(jwtTokenUtil.validateToken(tokenReqRes.getToken()));
    }

    @GetMapping("/secured-end-point")
    public ResponseEntity<?> getAllFruits(@RequestHeader(value = "Authorization", required = false) String token) {
        logger.info("Inside secured-end-point");
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Token is required");
        } else {
            String realToken = token.substring(7); // Since Header field adds "Bearer" at the beginning of the token
            String tokenCheckResult = jwtTokenUtil.validateToken(realToken);
            if (tokenCheckResult.equalsIgnoreCase("Valid token")) {
                List<String> messageList = List.of("This is a top secret message in a very secure end point, if you're reading this, you are validated User", "Champion", "Apple");
                return new ResponseEntity<>(messageList, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized due to: " + tokenCheckResult);
            }
        }
    }
}
