package id.my.mirfanduri.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/home")
public class Home {

    @GetMapping
    public ResponseEntity<String> home(){
        log.info(">>> request to home");
        return new ResponseEntity<>("Hello Muhammad Irfan, you are using Jenkins!. Your dockerhub is mirfanduri",
                HttpStatus.OK);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<String> printMyName(@PathVariable String name) {
        log.info(">>> request to printMyName");
        return new ResponseEntity<>("Hai " + name +
                ", you are using CI/CD with GitHub and Jenkins. Congratulations!", HttpStatus.OK);
    }
}
