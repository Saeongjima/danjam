package site.danjam.mate.mate_service.mate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.mate_service.mate.service.ElasticSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final ElasticSearchService elasticSearchService;

    @GetMapping("/es-connect")
    public void testEsConnect(){
        elasticSearchService.checkConnection();
    }
}
