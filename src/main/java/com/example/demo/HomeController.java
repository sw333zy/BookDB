package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    BookRepository bookRepository;

    @RequestMapping("/")
    public String bookList(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "bookform";
    }

    @PostMapping("/process")
    public String processBook(@Valid @ModelAttribute Book book, BindingResult result){
        if(result.hasErrors()){
            return "bookform";
        }

        if(book.getNumInStock()==0){
            book.setInStock(false);
        } else {book.setInStock(true);}
        bookRepository.save(book);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id")
    public String bookDetail(@PathVariable("id") long id, Model model){
        model.addAttribute("book", bookRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String bookUpdate(@PathVariable("id")long id, Model model){
        model.addAttribute("book", bookRepository.findById(id).get());
        return "bookform";
    }

    @RequestMapping("/delete/{id}")
    public String bookDelete(@PathVariable("id") long id, Model model){
        bookRepository.deleteById(id);
        return "redirect:/";
    }
}
