package com.kajisaab.ecommerce.feature.ping;

import com.kajisaab.ecommerce.core.responseHandler.ResponseDataHandler;
import com.kajisaab.ecommerce.core.responseHandler.ResponseHandler;
import com.kajisaab.ecommerce.core.responseHandler.ResponseHandlerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/ping")
@Tag(name = "Ping")
public class PingController {

    @GetMapping("")
    public ResponseEntity<Object> ping(){

        ResponseDataHandler responseData = new ResponseDataHandler();
        responseData.setMessage("Ping successfully");

        return ResponseHandler.responseBuilder(responseData);
    }
}
