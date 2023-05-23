package challenge18.hotdeal.controller;

import challenge18.hotdeal.dto.ProductResponseDto;
import challenge18.hotdeal.service.JmeterTestService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JmeterTestController {

    private final JmeterTestService jmeterTestService;

    @GetMapping("/jmeter-test")
    public List<ProductResponseDto> test() {
        return jmeterTestService.test();
    }
}
