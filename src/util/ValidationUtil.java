package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(HashMap<TextField,Pattern>map, JFXButton btn) {
        for (TextField key:map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()) {
                addError(key,btn);
                return key;
            }
            removeError(key,btn);

        }
        return true;
    }

    public static void removeError(TextField textField,JFXButton btn) {
        textField.setStyle("-fx-border-color: none");
        btn.setDisable(false);
    }

    public static void addError(TextField textField,JFXButton btn) {
        if(textField.getLength()>0){
            textField.setStyle("-fx-border-color: red");

        }
       btn.setDisable(true);

    }
}
