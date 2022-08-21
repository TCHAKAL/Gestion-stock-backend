package dz.tchakal.gds.exception.handler;

import dz.tchakal.gds.exception.ErrorCode;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {

    private Integer httpCode;

    private ErrorCode errorCode;

    private String message;

    private List<String> errors = new ArrayList<>();

}
