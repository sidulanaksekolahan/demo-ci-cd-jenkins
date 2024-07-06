package id.my.mirfanduri.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class Home {

    @GetMapping
    public ResponseEntity<String> home(){
        return new ResponseEntity<>("Hello Muhammad Irfan, you are using Jenkins!", HttpStatus.OK);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<String> printMyName(@PathVariable String name) {
        return new ResponseEntity<>("Hai " + name +
                ", you are using CI/CD with Jenkins. Congratulations!", HttpStatus.OK);
    }
}
