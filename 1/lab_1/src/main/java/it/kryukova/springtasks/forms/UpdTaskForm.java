package it.kryukova.springtasks.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdTaskForm {
    private String titleFrom;
    private String yesNoMarkFrom;
    private String titleTo;
    private String yesNoMarkTo;
}
