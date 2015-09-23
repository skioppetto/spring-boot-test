package me.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 23/09/15.
 */

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    String home(){
        return "Hello World!";
    }

    @RequestMapping("/helloparams")
    String withParams (@RequestParam(value="name", defaultValue = "Default Name") String name){
        return "Hello ".concat(name).concat("!");
    }
}
