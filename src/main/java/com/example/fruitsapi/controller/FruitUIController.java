package com.example.fruitsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.fruitsapi.model.Fruit;
import com.example.fruitsapi.service.FruitService;
import com.example.fruitsapi.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/buah")
public class FruitUIController {

    @Autowired
    private FruitService fruitService;

    private final JwtUtil jwtUtil = new JwtUtil();

    // Tampilkan tabel data buah
    @GetMapping
    public String viewBuahTable(Model model, HttpServletRequest req) {
        if (!jwtUtil.validateJWTFromCookies(req.getCookies()))
            return "redirect:/login";
        model.addAttribute("fruits", fruitService.getAllFruits());
        return "buah-list";

    }

    // Tampilkan form input data buah
    @GetMapping("/form")
    public String showBuahForm(Model model, HttpServletRequest req) {
        if (!jwtUtil.validateJWTFromCookies(req.getCookies()))
            return "redirect:/login";
        model.addAttribute("fruit", new Fruit());
        return "buah-form";
    }

    // Simpan data buah
    @PostMapping("/save")
    public String saveBuah(@ModelAttribute("fruit") Fruit fruit) {
        fruitService.saveFruit(fruit);
        return "redirect:/buah";
    }

    // Update data buah
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Fruit fruit = fruitService.getFruitById(id);
        model.addAttribute("fruit", fruit);
        return "buah-form";
    }

    // Soft delete data buah
    @GetMapping("/delete/{id}")
    public String deleteBuah(@PathVariable("id") Long id) {
        fruitService.softDeleteFruit(id);
        return "redirect:/buah";
    }
}
