package com.example.fruitsapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fruitsapi.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/do-login")
    public String doLogin(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            Model model) {
        if ("admin".equals(username) && "a".equals(password)) {
            String token = jwtUtil.generateToken(username);
            // Simpan token ke cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(30 * 60); // 30 menit
            response.addCookie(jwtCookie);

            return "redirect:/buah";
        } else {
            model.addAttribute("error", "Username atau password salah");
            return "login";

        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "/login";
    }

    private static final String JWT_COOKIE_NAME = "jwt";

    @GetMapping("/logout")
    public String logout(Model model, HttpServletResponse response) {
        // Buat cookie baru dengan nama yang sama untuk menimpa cookie lama
        Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, "");
        jwtCookie.setHttpOnly(true); // Pastikan tetap HttpOnly agar aman
        jwtCookie.setSecure(true); // Gunakan secure jika aplikasi berjalan di HTTPS
        jwtCookie.setPath("/"); // Pastikan path sesuai saat cookie dibuat
        jwtCookie.setMaxAge(0); // Kedaluwarsa segera

        // Tambahkan cookie ke dalam respons
        response.addCookie(jwtCookie);

        return "redirect:/login";
    }
}
