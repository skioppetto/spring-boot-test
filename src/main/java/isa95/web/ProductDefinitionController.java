package isa95.web;

import org.springframework.web.bind.annotation.*;

/**
 * Created by user on 23/09/15.
 */

@RestController
@RequestMapping("/product-definitions")
public class ProductDefinitionController {

    @RequestMapping(method = RequestMethod.POST)
    String create(){
        return "Hello World!";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    String retrieve(@PathVariable("id") String productDefinitionID){
        return "retrieve ".concat(productDefinitionID);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    String update(@PathVariable("id") String productDefinitionID){
        return "updating object";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    String delete(@PathVariable("id") String productDefinitionID){
        return "deleting ".concat(productDefinitionID);
    }

}
