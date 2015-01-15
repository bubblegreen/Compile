#include "ast.h"

extern Exp_t tree;
void yyparse ();

//////////////////////////////////////////////
// Data structures for the Stack language.
enum Stack_Kind_t {STACK_ADD, STACK_MINUS, STACK_TIMES, STACK_DIVISION, STACK_PUSH};
struct Stack_t
{
  enum Stack_Kind_t kind;
};

struct Stack_Add
{
  enum Stack_Kind_t kind;
};

struct Stack_Minus
{
    enum Stack_Kind_t kind;
}; 

struct Stack_Times
{
    enum Stack_Kind_t kind;
};

struct Stack_Division
{
    enum Stack_Kind_t kind;
};   

struct Stack_Push
{
  enum Stack_Kind_t kind;
  int i;
};

// "constructors"
struct Stack_t *Stack_Add_new ()
{
  struct Stack_Add *p = malloc (sizeof(*p));
  p->kind = STACK_ADD;
  return (struct Stack_t *)p;
}

struct Stack_t *Stack_Minus_new ()
{
  struct Stack_Minus *p = malloc (sizeof(*p));
  p->kind = STACK_MINUS;
  return (struct Stack_t *)p;
}

struct Stack_t *Stack_Times_new ()
{
  struct Stack_Times *p = malloc (sizeof(*p));
  p->kind = STACK_TIMES;
  return (struct Stack_t *)p;
}

struct Stack_t *Stack_Division_new ()
{
  struct Stack_Division *p = malloc (sizeof(*p));
  p->kind = STACK_DIVISION;
  return (struct Stack_t *)p;
}

struct Stack_t *Stack_Push_new (int i)
{
  struct Stack_Push *p = malloc (sizeof(*p));
  p->kind = STACK_PUSH;
  p->i = i;
  return (struct Stack_t *)p;
}

/// instruction list
struct List_t
{
  struct Stack_t *instr;
  struct List_t *next;
};

struct List_t *List_new (struct Stack_t *instr, struct List_t *next)
{
  struct List_t *p = malloc (sizeof (*p));
  p->instr = instr;
  p->next = next;
  return p;
}

// "printer"
void List_reverse_print (struct List_t *list)
{
  if (list->next != 0){
    List_reverse_print(list->next);
  }
  switch(list->instr->kind){
      case STACK_ADD:{
          printf ("\nADD");
          break;
      }
      case STACK_MINUS:{
          printf ("\nMINUS");
          break;
      }
      case STACK_TIMES:{
          printf ("\nTIMES");
          break;
      }
      case STACK_DIVISION:{
          printf ("\nDIVISION");
          break;
      }
      case STACK_PUSH:{
          struct Stack_Push *p = (struct Stack_Push *)(list->instr);
          printf ("\nPUSH %d", p->i);
          break;
      }
      default:
          break;        
  } 
}

//////////////////////////////////////////////////
// a compiler from Sum to Stack
struct List_t *all = 0;

void emit (struct Stack_t *instr)
{
  all = List_new (instr, all);
}

void compile (struct Exp_t *exp)
{
  switch (exp->kind){
  case EXP_INT:{
    struct Exp_Int *p = (struct Exp_Int *)exp;
    emit (Stack_Push_new (p->n));
    break;
  }
  case EXP_ADD:{
    struct Exp_Add *p = (struct Exp_Add *)exp;
    compile (p->left);
    compile (p->right);
    emit (Stack_Add_new());
    break;
  }
  case EXP_MINUS:{
    struct Exp_Minus *p = (struct Exp_Minus*)exp;
    compile (p->left);
    compile (p->right);
    emit (Stack_Minus_new());
    break;
  }
  case EXP_TIMES:{
    struct Exp_Times *p = (struct Exp_Times*)exp;
    compile (p->left);
    compile (p->right);
    emit (Stack_Times_new());
    break;
  }
  case EXP_DIVISION:{
    struct Exp_Division *p = (struct Exp_Division*)exp;
    compile (p->left);
    compile (p->right);
    emit (Stack_Division_new());
    break;
  }
  default:
    break;
  }
}

int main (int argc, char **argv)
{
  yyparse();
  
  // print out this tree:
  printf ("The expression is:\n");
  Exp_print (tree);
  printf ("\n");
  
  // compile this tree to Stack machine instructions
  compile (tree);

  // print out the generated Stack instructons:
  printf ("\nThe generated Stack instructions is:");
  List_reverse_print (all);
  
  printf("\nCompile finished\n");
  
  return 0;
}
