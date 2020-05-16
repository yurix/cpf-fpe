package org.yx;

import com.idealista.fpe.config.Alphabet;

public class NumberAlphabet implements Alphabet{

    private static final char[] LOWER_CASE_CHARS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'};

    @Override
    public char[] availableCharacters() {
        return LOWER_CASE_CHARS;
    }

    @Override
    public Integer radix() {
        return LOWER_CASE_CHARS.length;
    }
}

