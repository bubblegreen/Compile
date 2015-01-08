%{
    #include <stdio.h>
    #include <stdlib.h>
    int yylex();
%}
%token NUM
%left '+' '-'
%left '*' '/'

%%
lines: line
     | line lines
;

line: exp '\n';

exp: NUM
   | exp '+' exp
   | exp '-' exp
   | exp '*' exp
   | exp '/' exp
   | '(' exp ')'
;

%%

int yylex()
{
    int c;
  /* Skip white space.  */
  while ((c = getchar ()) == ' ' || c == '\t')
    {}

  /* Process numbers.  */
  if (isdigit (c))
    {
      yylval = c - '0';
      while (isdigit (c = getchar ()))
        {
          yylval = yylval * 10 + c - '0';
        }
      ungetc (c, stdin);
      return NUM;
    }
  /* Return end-of-input.  */
  if (c == EOF)
    return 0;

  return c;

}

void yyerror (char *s)
{
    fprintf (stderr, "%s\n", s);
    return;
}

int main(int argc, char **argv)
{
    yyparse();
    return 0;
}
            
