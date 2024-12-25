package com.weavusys.hrd.controller;

import com.weavusys.hrd.entity.Accrual;
import com.weavusys.hrd.entity.Amount;
import com.weavusys.hrd.service.AccrualService;
import com.weavusys.hrd.calCulBatch.BatchAccrualService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class AccrualController {

    private final AccrualService accrualService;
    private final BatchAccrualService batchAccrualService;

    @GetMapping("/accrual")
    public List<Accrual> getAllEmployees() {
        List<Accrual> accrualList = accrualService.findAll();
        return accrualList;
    }

    @GetMapping("/{id}/accrual")
    public Accrual getAccrual(@PathVariable String id) {
        Accrual accrual = accrualService.findByEmployeeId(id).orElseThrow();
        return accrual;
    }

    @GetMapping("/admin/setting")
    public List<Amount> GetAdminSetting() {
        List<Amount> amountList = accrualService.findAllAmount();
        return amountList;
    }

    @PutMapping("/admin/setting")
    public void updateAmounts(@RequestBody List<Amount> amounts) {
        try{
            for (Amount amount : amounts) {
                accrualService.updateAmount(amount); // 각 항목을 처리
            }
        }catch (RuntimeException e){

        }
    }

    @GetMapping ("/accruals/year/{year}")
    public long getYearlyAccrualTotal(@PathVariable int year) {
        return batchAccrualService.calculateYearlyAccrualTotal(year);
    }
}