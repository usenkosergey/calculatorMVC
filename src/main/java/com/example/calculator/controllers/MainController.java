package com.example.calculator.controllers;

import com.example.calculator.api.requests.Data;
import com.example.calculator.api.responses.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String getPayment() {
        logger.info("/");
        return "index";
    }

    @GetMapping("/calc")
    public String showPayment(@ModelAttribute(value = "data") Data data, Model model) {
        logger.info("/calc");
        LocalDate dt = LocalDate.parse(data.getDate());
        double balanceAmount = data.getCreditAmount();
        LocalDate datePayment = LocalDate.parse(data.getDate());
        List<Payment> payments = new ArrayList<>();

        double monthlyRate = data.getInterestRate() / 100 / 12;
        double monthlyPayment = BigDecimal.valueOf(data.getCreditAmount() * (monthlyRate + monthlyRate / (Math.pow((1 + monthlyRate), data.getQuantityMonths()) - 1))).setScale(4, RoundingMode.HALF_UP).doubleValue();

        for (int i = 1; i <= data.getQuantityMonths(); i++) {
            Payment currentPayment = new Payment();
            double percentMonths = new BigDecimal(balanceAmount * monthlyRate).setScale(4, RoundingMode.HALF_UP).doubleValue();
            double debt = new BigDecimal(monthlyPayment - percentMonths).setScale(4, RoundingMode.HALF_UP).doubleValue();
            balanceAmount = new BigDecimal(balanceAmount - debt).setScale(4, RoundingMode.HALF_UP).doubleValue();
            balanceAmount = balanceAmount < 1 ? 0 : balanceAmount;

            currentPayment.setNumberPayment(i);
            currentPayment.setMonthlyPayment(convert(monthlyPayment));
            currentPayment.setPercentMonths(convert(percentMonths));
            currentPayment.setDebt(convert(debt));
            currentPayment.setBalanceAmount(convert(balanceAmount));
            currentPayment.setDatePayment(datePayment.plusMonths(i - 1));

            payments.add(currentPayment);
        }
        model.addAttribute("payments", payments);
        return "calc";
    }

    private String convert(double convertData) {
        return String.format(Locale.US, "%.2f", convertData);
    }
}
