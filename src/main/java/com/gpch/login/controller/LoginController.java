package com.gpch.login.controller;

import com.gpch.login.model.User;
import com.gpch.login.service.UserService;
import com.gpch.login.storage.StorageFileNotFoundException;
import com.gpch.login.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            String message = "User has been registered successfully. </br>\n" +
                    "                          Go back to <a href=\"@{/login}\" style=\"color: #c05c7e\" type=\"Submit\"><u>LOGIN</u></a> page.";
            modelAndView.addObject("successMessage", message);
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @GetMapping(value="/admin/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome, " + user.getName());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @Autowired
    StorageService storageService;

    @GetMapping(value = "/paintings/thegreatwave")
    public ModelAndView thegreatwave(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(LoginController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        modelAndView.setViewName("admin/thegreatwave");
        return modelAndView;
    }

    @GetMapping("/painting/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/paintings/thegreatwave")
    public String handleFileUploadWave(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        storageService.store(file, "the_wave");
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:thegreatwave";
    }


    /****   COMPOSITION VII   *****/

    @GetMapping(value = "/paintings/composition")
    public ModelAndView composition(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(LoginController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        modelAndView.setViewName("admin/composition");
        return modelAndView;
    }

    @PostMapping("/paintings/composition")
    public String handleFileUploadComposition(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {
        storageService.store(file, "composition_vii");
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:composition";
    }




    /****   LA MUSE  *****/

    @GetMapping(value = "/paintings/lamuse")
    public ModelAndView lamuse(){
        System.out.println("Got to get!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(LoginController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        modelAndView.setViewName("admin/lamuse");
        return modelAndView;
    }

    @PostMapping("/paintings/lamuse")
    public String handleFileUploadLamuse(@RequestParam("file") MultipartFile file,
                                              RedirectAttributes redirectAttributes) {
        storageService.store(file, "la_muse");
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:lamuse";
    }




    /****   STARRY NIGHT  *****/

    @GetMapping(value = "/paintings/starrynight")
    public ModelAndView starrynight(){
        System.out.println("Got to get!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(LoginController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        modelAndView.setViewName("admin/starrynight");
        return modelAndView;
    }

    @PostMapping("/paintings/starrynight")
    public String handleFileUploadStarryNight(@RequestParam("file") MultipartFile file,
                                              RedirectAttributes redirectAttributes) {
        storageService.store(file, "starry_night");
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:starrynight";
    }



    /****   THE SCREAM  *****/

    @GetMapping(value = "/paintings/thescream")
    public ModelAndView thescream(){
        System.out.println("Got to get!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(LoginController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        modelAndView.setViewName("admin/thescream");
        return modelAndView;
    }

    @PostMapping("/paintings/thescream")
    public String handleFileUploadTheScream(@RequestParam("file") MultipartFile file,
                                              RedirectAttributes redirectAttributes) {
        storageService.store(file, "the_scream");
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:thescream";
    }


    /****   JUNE TREE (CANDY)  *****/

    @GetMapping(value = "/paintings/junetree")
    public ModelAndView junetree(){
        System.out.println("Got to get!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(LoginController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        modelAndView.setViewName("admin/junetree");
        return modelAndView;
    }

    @PostMapping("/paintings/junetree")
    public String handleFileUploadJunetree(@RequestParam("file") MultipartFile file,
                                              RedirectAttributes redirectAttributes) {
        storageService.store(file, "candy");
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:junetree";
    }



    /****   UDNIE *****/

    @GetMapping(value = "/paintings/udnie")
    public ModelAndView udnie(){
        System.out.println("Got to get!");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(LoginController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        modelAndView.setViewName("admin/udnie");
        return modelAndView;
    }

    @PostMapping("/paintings/udnie")
    public String handleFileUploadUdnie(@RequestParam("file") MultipartFile file,
                                              RedirectAttributes redirectAttributes) {
        storageService.store(file, "udnie");
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:udnie";
    }







    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }





}
