package org.ummjevel.osmapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Array;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(RestException.class)
    public ResponseEntity<Map<String, Object>> handler(RestException e) {
        Map<String, Object> resBody = new HashMap<>();

        resBody.put("result", e.getResult());
        resBody.put("message", e.getMessage());
        resBody.put("body", new Array[0]);

        return new ResponseEntity<>(resBody, e.getStatus());
    }
}
