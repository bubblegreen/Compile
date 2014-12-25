%{
#include "stdio.h"
#include "stdlib.h"
int column = 0;
int row = 1;
char *yytext;
int yyleng;
%}
letter [_a-zA-Z]
number [0-9]
IF (if)
ID {letter}({letter}|{number})*
enter [\r\n]
%%;
{enter} {row++; column = 0;}
{IF} {printf("IF(%d,%d)\n",row,column + 1); column += 3;}
{ID} {printf("ID(%s)(%d,%d)\n",yytext, row, column + 1); column += yyleng + 1;}
%%  
int main() //执行主函数  
{  
    yylex();  //开始解析语句,内置函数   
}  
int yywrap()  
{  
    return 1;  
}
