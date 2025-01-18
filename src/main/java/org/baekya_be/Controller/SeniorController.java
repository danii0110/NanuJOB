package org.baekya_be.Controller;

import lombok.RequiredArgsConstructor;
import org.baekya_be.Domain.Senior;
import org.baekya_be.Service.SeniorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SeniorController {
    private final SeniorService seniorService;

    @GetMapping("/get-all-seniors")
    public ResponseEntity<List<Senior>> getUsers() throws Exception{
        List<Senior> list = seniorService.getSeniors();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/filter-senior/{filter}")
    public ResponseEntity<List<Senior>> filterSenior(@PathVariable String filter) throws Exception{
        List<Senior> list = seniorService.seniorsFilter(filter);
        return ResponseEntity.ok(list);
    }
}
