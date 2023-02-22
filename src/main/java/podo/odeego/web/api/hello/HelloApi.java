package podo.odeego.web.api.hello;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloApi {

    @GetMapping("/simple")
    public String string() {
        return "hello";
    }

    @GetMapping("/query-parameter")
    public HelloResponse queryParam(@RequestParam String query) {
        return new HelloResponse("hello " + query);
    }

    @PostMapping("/body")
    public HelloResponse requestBody(@RequestBody HelloRequest request) {
        return new HelloResponse("hello " + request.message());
    }

    @PutMapping("/put")
    public String put() {
        return "hello put";
    }

    @DeleteMapping("/delete")
    public String delete() {
        return "hello delete";
    }
}
