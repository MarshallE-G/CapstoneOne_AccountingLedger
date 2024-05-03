package com.ps;

/*
 Class for ANSI code
 */

public class ColorsAndGrapics {
        // Colors
    public final String BLACK = "\u001b[30m"; public final String BLACK_BACKGROUND = "\u001b[40m";
    public final String RED = "\u001B[31m"; public final String RED_BACKGROUND = "\u001b[41m";
    public final String GREEN = "\u001B[32m"; public final String GREEN_BACKGROUND = "\u001b[42m";
    public final String YELLOW = "\u001b[33m"; public final String YELLOW_BACKGROUND = "\u001b[43m";
    public final String BLUE = "\u001b[34m"; public final String BLUE_BACKGROUND = "\u001b[44m";
    public final String MAGENTA = "\u001b[35m"; public final String MAGENTA_BACKGROUND = "\u001b[45m";
    public final String CYAN = "\u001b[36m"; public final String CYAN_BACKGROUND = "\u001b[46m";
    public final String WHITE = "\u001b[37m"; public final String WHITE_BACKGROUND = "\u001b[47m";

        // Bright variation of colors
    public final String BRIGHT_BLACK = "\u001b[90m"; public final String BRIGHT_BLACK_BACKGROUND = "\u001b[100m";
    public final String BRIGHT_RED = "\u001b[91m"; public final String BRIGHT_RED_BACKGROUND = "\u001b[101m";
    public final String BRIGHT_GREEN = "\u001b[92m"; public final String BRIGHT_GREEN_BACKGROUND = "\u001b[102m";
    public final String BRIGHT_YELLOW = "\u001b[93m"; public final String BRIGHT_YELLOW_BACKGROUND = "\u001b[103m";
    public final String BRIGHT_BLUE = "\u001b[94m"; public final String BRIGHT_BLUE_BACKGROUND = "\u001b[104m";
    public final String BRIGHT_MAGENTA = "\u001b[95m"; public final String BRIGHT_MAGENTA_BACKGROUND = "\u001b[105m";
    public final String BRIGHT_CYAN = "\u001b[96m"; public final String BRIGHT_CYAN_BACKGROUND = "\u001b[106m";
    public final String BRIGHT_WHITE = "\u001b[97m"; public final String BRIGHT_WHITE_BACKGROUND = "\u001b[107m";
    public final String DEFAULT_COLOR = "\u001b[39m"; public final String DEFAULT_BACKGROUND = "\u001b[49m";
    public final String END_COLOR = "\u001B[0m";

        // Graphics Modes
    public final String BOLD = "\u001b[1m"; public final String END_BOLD = "\u001b[22m";
    public final String ITALIC = "\u001b[3m"; public final String END_ITALIC = "\u001b[23m";
    public final String UNDERLINE = "\u001b[4m"; public final String END_UNDERLINE = "\u001b[24m";

}
