MulCal by James Brooks
====

Objective
----

A calculator application that deals with: 

1. currency conversion
1. VAT
1. date ranges
1. the usual equations (trig, logs, rounding)

Every equation evaluated is stored as a history which can be annotated and used in later equations as constants. This history can be saved to use in excel.

Quick Start
----

Running `mulCal.main.TextInput` will give you a text based calculator, start each equation with 'e ', quit by entering 'q'. The results are given an ID this can be used a a constant in other calculations.

    > e 1+1
    A ::= 1+1 = 2
    > e A*2
    B ::= A*2 = 4
    > e cos PI
    D ::= cos PI = -1
    > q
    Done.
    
Milestones
----

1. Text Based Calculator (v1)
1. Add Currency Special Forms (v2)
1. Add Calendar Special Forms (v3)
1. Add GUI (v4)

Current Progress
----

Version one is done. The command line calculator works - there are some known annoyances (see github:issues) but version one is complete.

Requirements
----

1. parsing command line arguments
1. parsing dates and calculating difference between dates
1. parsing maths equations
1. parsing and writing csv file
1. parsing and writing cfg file
1. downloading information from internet
1. convert number to arbitrary base with arbitrary encoding

Command line Options 
----

    q[uit]                   -- exit the program
    h[elp]                   -- display this message
    s[ave]     <filename>    -- save history to csv file for excel import
    r[eset]                  -- clear history
    l[oad]     <filename>    -- replace history with csv file
    e[quation] <equation>    -- evaluate equation and add to history
    c[omment]  <id> <string> -- add comment to history item
    p[rint]    <id?>         -- show history item; blank for all
    u[pdate]                 -- download new currency conversion values
    f[rom]     <date>        -- update the from date (and selects it)
    t[ill]     <date>        -- update the till date (and selects it)
    d[ays]     <number>      -- update selected date to give correct days
    w[eeks]    <number>      -- update selected date to give correct weeks
    m[onths]   <number>      -- update selected date to give correct months
    y[ears]    <number>      -- update selected date to give correct years

CSV File Format
----

    rule TOP	  { <line>* }
    rule line	  { <id> ',' <equation> ',' <value> ',' <comment> }
    rule id	  { <lower_char>* }    # autoincrement unique
    rule equation { <quoted_string> } 
    rule value	  { <real_number> } 
    rule comment  { <quoted_string> } 

CSV Example
----

    A, "7.23"                                  , 7.23   , "pounds per hour" 
    B, "a * 8"                                 , 57.84  , "daily wage" 
    C, "(b/8)/3"                               , 2.41   , "" 
    D, "GBP_USD b"                             , 76.93  , "daily USD" 
    E, "d * [days 2010/2/4 2010/2/11]"         , 846.20 , "final value" 

Equation Format 
----

    rule TOP	 { <entry> }
    rule entry	 { <number> | <unary_function> | <binary_func> | <bracketed> | <date_calc> | <constant> }
    rule unaryfunction	{ <un_func_name> <entry> }
    rule binary_func	{ <entry> <bi_func_name> <entry> }
    rule bracketed	 { '(' <entry> ')' }
    rule date_calc	 { '[' <timespan> <date> <date> ']' }
    rule constant	{ <lower_char>+ }
    rule timespan	{ 'days' | 'weeks' | 'months' | 'years' }
    rule date		{ <number> '/' <number> '/' <number> }

Equation Examples 
----

    7                 # the simplest possible 'equation'
    +6.8123e-2        # scientific notation
    7 + 1             # a function that doesn't need brackets
    4.1^2             # spaces are optional
    ((((-2.7))))      # extra brackets are ok
    sin -2            # a space is needed to seperate the function and the negative number
    5---2             # '-' can be part of a number, unary, or binary (one of each here)
    1 + 2 * 3 + 4     # order of precedence means this is different to the next one
    (1 + 2) * (3 + 4) # as the rules get changed as normal by brackets
    A + 1             # using the result of a previous calculation as a constant
    sin cos pi        # brackets are not enforced 
    [GBP_USD 70]      # currency converting 70 from pounds to dollars
    tan [USD_YEN pi]  # brackets are not enforced 
    [weeks 2010/2/4 2010/2/11]   # finds number of weeks (inc. fractional weeks) in a date range
    5 + 3 * 2 - 4 / (8 + 1) % A  # 
    1+2-3*4/5^6                           # infix operators
    sin cos tan exp log sqrt signum abs E # prefix functions
    
Functions and Constants 
----

(constants must be only upper case chars functions cannot be)

    binary 	basic:    	 + - * / % ^
    unary  	basic:    	 sin cos tan asin acos atan log ln exp ! sqrt
    binary 	logic:    	 < > <= >= == <> || &&
    unary   rounding: 	 ceil floor round abs
    constants:        	 PI E 

Settings File Example (cfg format) 
----

    vat = 17.5 
    USD = 1     # all currency is relative to GBP
    GBP = 70
    EUR = 60
 
GUI Keys 
----

**Global**

 + ctrl-s   : save history
 + ctrl-del : clear history
 + ctrl-o   : load history
 + ctrl-u   : update currency
 + tab      : select the next item in order (a, d, e, f, g, k, eqn-line)
 + alt-[asdfjk] : select item (a, d, e, f, g, k, eqn-line)
 + buttons and other entry boxes:
 + enter   : same as mouse click

**Calendar-selector**

 + arrow keys  : move by day/week (wrap on edges)
 + pg_up/down  : move my month
 + home/end    : start/end of week
 + shift-arrow : move by month
 + ins	 : move to current day
 + enter	 : select from - click on current cell
 + shift-enter : select to - click on current cell
 + calendar-results/vat/currency:
 + arrow keys  : move by cell (no wrap on edges)
 + pg_up/down  : first/last row
 + home/end    : first/last column
 + shift-arrow : first/last row/column
 + enter	 : same as click on current cell
 + shift-enter : edit cell where possible

**History**

 + arrow keys  : move by cell (no wrap on edges)
 + pg_up/down  : page up/down
 + home/end    : first/last column
 + shift-arrow : first/last row/column
 + enter	 : same as click on current cell

**Equation-line**

 + enter       : same as click on (n)
 + ctrl-a      : select all text in line 
 + pg_up/down  : page up/down history

Extensions 
----

1. Use http://www.apfloat.org/apfloat_java/ to get arbitrary precision arithmetic for functions. 
1. allow new functions to be defined in the setting file (written in lua)
1. allow the user to specify the common functions that get displayed
1. allow the user to add new currencies (maybe by separating cfg file into [default] and [currencies])
1. make separate command line display of calendar, vat, currency and history
1. make new calendar results entries for work-days, weekends, work-hours
1. make a new block for binary operations like bit-shifting and viewing in hex/binary/octal
1. make a block for most commonly used function for that user
1. make a block for most commonly used history items in the current session
1. accessibility mode for GUI - pressing esc should give floating id's to each click-able, typing that id clicks the thing.
1. make `display-precision = 2` & `eval-precision = 20` in settings and to allow better maths.
1. package using a launcher so all mac people need is to double click and windows sees an exe.
1. it may may more sense for comments, save & load to require quoted text.

Classes 
----

1. `History` -- a history of all equations typed
1. `Currency` -- holds the currency conversion gathered from settings file, defaults or internet
1. `Calendar` -- holds the selected from and till dates and does calculations on them
1. `Settings` -- reads/writes a cfg file with the last updated currencies ant vat
1. `Main` -- the controller between the interface, e.g. GUI or command line, and the other classes
1. `TextInput` -- Gets run by the user to run as a command line application. Passes commands to Main.
1. `GUI` -- Gets run by the user to run as a GUI application. Passes commands to Main.

-------------------------------------------------------------------------
