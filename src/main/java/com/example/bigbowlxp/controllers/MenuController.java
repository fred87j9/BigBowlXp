package com.example.bigbowlxp.controllers;

import com.example.bigbowlxp.models.Menu;
import com.example.bigbowlxp.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MenuController {

    private MenuService menuService;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    @GetMapping("/mad")
    public String showFood(Model foodModel){
        foodModel.addAttribute("Menu", menuService.fetchMenu());
        return "/mad";
    }

    @PostMapping("/changePrice({id}")
    public String changeFoodPrice(@PathVariable("id") int id, @ModelAttribute int price){
        menuService.changeFoodPrice(price, id);
        return "redirect:/mad";
    }
}
