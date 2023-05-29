package Server.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class Reprimand implements Serializable {
    private Integer id;
    private String employee_id;
    private String description;
}
