import org.springframework.web.bind.annotation.RequestMapping;
public class HomeController {
    @Value("${spring.application.name}")
    private String appName;
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
