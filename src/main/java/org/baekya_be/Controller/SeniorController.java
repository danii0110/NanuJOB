package org.baekya_be.Controller;

import lombok.RequiredArgsConstructor;
import org.baekya_be.DTO.SeniorSearchDTO;
import org.baekya_be.Domain.Senior;
import org.baekya_be.Service.SeniorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/search-senior")
    public ResponseEntity<List<Senior>> searchSenior(@RequestBody SeniorSearchDTO dto) throws Exception{
        String keyword = dto.getKeyword();
        List<Senior> list = seniorService.searchSeniors(keyword);
        return ResponseEntity.ok(list);
    }
}
