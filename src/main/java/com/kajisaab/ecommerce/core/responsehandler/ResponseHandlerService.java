package com.kajisaab.ecommerce.core.responsehandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHandlerService {
    private  int status;
    private  String message;
    private  Object data;
}
