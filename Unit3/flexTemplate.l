%{  
//����һЩ��������  
%}  
//�����������ʽ  
//����:  
//num ([0-9])+   //���Ϊ���ţ��ұ�Ϊ�������ʽ  
%%  
//����һϵ�еĶ����������ƥ�������涨���ĳ���������ʽ֮��Ķ���,�˶�����C���Ե����  
//���磺  
//{num} {printf("num");} //���Ϊ{ģʽ},�ұ�Ϊ{C���};  
%%  
void main() //ִ��������  
{  
    yylex();  //��ʼ�������,���ú���  
    //����C�������  
}  
int yywrap()  
{  
    return 1;  
}