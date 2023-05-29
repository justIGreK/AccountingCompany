package Server.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@RequiredArgsConstructor
public class Activity implements Serializable {
    private Integer id;
    private String nameOfEvent;
    private String descriptionOfEvent;
}
