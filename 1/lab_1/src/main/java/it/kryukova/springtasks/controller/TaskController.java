package it.kryukova.springtasks.controller;

import it.kryukova.springtasks.forms.TaskForm;
import it.kryukova.springtasks.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Value("${error.messageNotFoundTask}")
    private String messageNotFoundTask;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }

    @RequestMapping(value = {"/alltasks"}, method = RequestMethod.GET)
    public ModelAndView taskList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
        model.addAttribute("tasks", tasks);
        log.info("/alltasks was called");
        return modelAndView;
    }

    @RequestMapping(value = {"/addtask"}, method = RequestMethod.GET)
    public ModelAndView showAddTaskPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        TaskForm taskForm = new TaskForm();
        model.addAttribute("taskform", taskForm);
        log.info("/addtask GET was called");
        return modelAndView;
    }

    @RequestMapping(value = {"/addtask"}, method = RequestMethod.POST)
    public ModelAndView saveTask(Model model, @ModelAttribute("taskform") TaskForm taskForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
        String title = taskForm.getTitle();
        String yesNoMark = taskForm.getYesNoMark();

        log.info("/addtask POST was called");
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

    @RequestMapping(value = {"/deltask"}, method = RequestMethod.GET)
    public ModelAndView showDelTaskPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        TaskForm taskFormDel = new TaskForm();
        model.addAttribute("taskformDel", taskFormDel);
        log.info("/deltask GET was called");
        return modelAndView;
    }

    @RequestMapping(value = {"/deltask"}, method = RequestMethod.POST)
    public ModelAndView deleteTask(Model model, @ModelAttribute("taskformDel") TaskForm taskFormDel) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
        String title = taskFormDel.getTitle();
        String yesNoMark = taskFormDel.getYesNoMark();

        log.info("/deltask POST was called");

        for (Task task : tasks)
        {
            if (task.getTitle().equals(title) && task.getYesNoMark().equals(yesNoMark))
            {
                tasks.remove(task);
                model.addAttribute("tasks", tasks);
                modelAndView.setViewName("deltask");
                return modelAndView;
            }
        }

        // если выполнение дошло до этого блока, значит задача не найдена
        if (true)
        {
            model.addAttribute("errorMessage", messageNotFoundTask);
            modelAndView.setViewName("deltask");
            return modelAndView;
        }

        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("deltask");
        return modelAndView;
    }

    @RequestMapping(value = {"/updtask"}, method = RequestMethod.GET)
    public ModelAndView showUpdTaskPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        TaskForm taskFormFrom = new TaskForm();
        TaskForm taskFormTo = new TaskForm();
        model.addAttribute("taskformFrom", taskFormFrom);
        model.addAttribute("taskformTo", taskFormTo);
        log.info("/updtask GET was called");
        return modelAndView;
    }

    @RequestMapping(value = {"/updtask"}, method = RequestMethod.POST)
    public ModelAndView updateTask(Model model, @ModelAttribute("taskformFrom") TaskForm taskFormFrom, @ModelAttribute("taskformTo") TaskForm taskFormTo) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");

        log.info("/updtask POST was called");

        String titleFrom = taskFormFrom.getTitle();
        String yesNoMarkFrom = taskFormFrom.getYesNoMark();
        String titleTo = taskFormTo.getTitle();
        String yesNoMarkTo = taskFormTo.getYesNoMark();

        Task taskFrom = new Task(titleFrom, yesNoMarkFrom);
        if (!tasks.contains(taskFrom))
        {
            model.addAttribute("errorMessage", messageNotFoundTask);
            modelAndView.setViewName("updtask");
            return modelAndView;
        }

        Task taskTo = new Task();

        if((yesNoMarkTo.equals("") || yesNoMarkTo.equals(null)) && (titleTo.equals("") || titleTo.equals(null)))
        {
            model.addAttribute("errorMessage", errorMessage);
            modelAndView.setViewName("updtask");
            return modelAndView;
        }
        else if (yesNoMarkTo.equals("") || yesNoMarkTo.equals(null))
        {
            taskTo = new Task(titleTo, yesNoMarkFrom);
        }
        else if (titleTo.equals("") || titleTo.equals(null))
        {
            taskTo = new Task(titleFrom, yesNoMarkTo);
        }
        else
        {
            taskTo = new Task(titleTo, yesNoMarkTo);
        }

        tasks.set(tasks.indexOf(taskFrom), taskTo);

        model.addAttribute("tasks", tasks);
        modelAndView.setViewName("updtask");
        return modelAndView;

//        for (Task task : tasks)
//        {
//            if (task.getTitle().equals(titleFrom) && task.getYesNoMark().equals(yesNoMarkFrom))
//            {
//                tasks.set(tasks.indexOf(), )
//                tasks.remove(task);
//                model.addAttribute("tasks", tasks);
//                modelAndView.setViewName("deltask");
//                return modelAndView;
//            }
//        }

//        // если выполнение дошло до этого блока, значит задача не найдена
//        if (true)
//        {
//            model.addAttribute("errorMessage", messageNotFoundTask);
//            modelAndView.setViewName("deltask");
//            return modelAndView;
//        }

//        model.addAttribute("errorMessage", errorMessage);
//        modelAndView.setViewName("deltask");
//        return modelAndView;
    }


}
