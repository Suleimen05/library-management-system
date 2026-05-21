package com.yeldossuly.suleimen.librarymanagement.controller;

import com.yeldossuly.suleimen.librarymanagement.dto.LibrarySummaryDto;
import com.yeldossuly.suleimen.librarymanagement.service.YeldossulySuleimenAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class YeldossulySuleimenReportController {

    private final YeldossulySuleimenAsyncService asyncService;

    @GetMapping("/summary")
    public CompletableFuture<LibrarySummaryDto> getLibrarySummary() {
        return asyncService.buildLibrarySummary();
    }
}
