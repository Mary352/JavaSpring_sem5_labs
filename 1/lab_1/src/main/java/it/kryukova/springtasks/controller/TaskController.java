package it.kryukova.springtasks.controller;

import it.kryukova.springtasks.forms.TaskForm;
import it.kryukova.springtasks.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {
    private static List<Task> tasks = new ArrayList<>();

    static {
        tasks.add(new Task("check rw tickets", "+"));
        tasks.add(new Task("buy rw ticket", ""));
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);

        return modelAndView;
    }

    @RequestMapping(value = {"/alltasks"}, method = RequestMethod.GET)
    public ModelAndView taskList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
        model.addAttribute("tasks", tasks);
        return modelAndView;
    }

    @RequestMapping(value = {"/addtask"}, method = RequestMethod.GET)
    public ModelAndView showAddTaskPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        TaskForm taskForm = new TaskForm();
        model.addAttribute("taskform", taskForm);

        return modelAndView;
    }

    @RequestMapping(value = {"/addtask"}, method = RequestMethod.POST)
    public ModelAndView saveTask(Model model, @ModelAttribute("taskform") TaskForm taskForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
        String title = taskForm.getTitle();
        String yesNoMark = taskForm.getYesNoMark();

        if (title != null && title.length() > 0
                && yesNoMark != null && yesNoMark.length() > 0)
        {
            Task newTask = new Task(title, yesNoMark);
            tasks.add(newTask);
            model.addAttribute("tasks", tasks);
            modelAndView.setViewName("addtask");
            return modelAndView;
        }

        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addtask");
        return modelAndView;
    }
}
