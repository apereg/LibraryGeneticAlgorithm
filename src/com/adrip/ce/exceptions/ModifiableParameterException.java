package com.adrip.ce.exceptions;

import java.io.IOException;
import java.io.Serial;

public class ModifiableParameterException extends IOException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ModifiableParameterException(String message) {
        super(message);
    }

}
