package podo.odeego.web.api.hello;

import org.springframework.web.bind.annotation.*;

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
}
