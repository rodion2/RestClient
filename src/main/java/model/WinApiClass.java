package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"functions", "id"})
public class WinApiClass {
    long id;

    String name;

    String description;

    List<WinApiFunction> functions;
}
