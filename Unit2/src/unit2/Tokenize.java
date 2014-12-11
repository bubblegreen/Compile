package unit2;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenize {
    
    private static int ROW = 1;
    private static int COLUMN = 0;

    public static void main(String[] args) {
        // TODO code application logic here
        String path = args[0];
        File file = new File(path);
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file),Charset.forName("ASCII"));
            start(reader);
        } catch (Exception e) {
            System.out.println(file.getAbsolutePath());
        }
        
    }

    private static void start(Reader reader) {
        String idRegex = "_|[a-h]|[j-z]|[A-H]|[J-Z]";
        Pattern pattern = Pattern.compile(idRegex);
        try{
            int tempChar = reader.read();
            COLUMN++;
            if(tempChar == -1)
            {
                System.exit(0);
            }
            Matcher matcher = pattern.matcher(String.valueOf((char)tempChar));
            if((char)tempChar == 'i')
            {
                ifToken(reader, "i");
            }
            else if (matcher.matches())
            {
                idToken(reader,String.valueOf((char)tempChar));
            }
            else if ((char)tempChar == '\n' || (char)tempChar == '\r')
            {
                COLUMN = 0;
                ROW++;
                reader.read();
                //System.out.println();
                start(reader);
            }
            else if ((char)tempChar == ' ')
            {
                //System.out.print(" ");
                start(reader);
            }
            else
            {
                start(reader);
            }
        }
        catch(Exception e){
        }
    }

    private static void ifToken(Reader reader, String i) {
        String idRegex = "_|[a-e]|[g-z]|[A-Z]|[0-9]";
        String regex = "_|[a-z]|[A-Z]|[0-9]";
        Pattern pattern = Pattern.compile(idRegex);
        try {
            int tempChar = reader.read();
            COLUMN++;
            if(tempChar == -1)
            {
                //System.out.printf("ID(%s)(%d,%d)\n",i, ROW, COLUMN);
                printID(i);
                System.exit(0);
            }
            Matcher matcher = pattern.matcher(String.valueOf((char)tempChar));
            if((char)tempChar == 'f')
            {
                int nextChar = reader.read();
                COLUMN++;
                if (nextChar == -1)
                {
                    //System.out.printf("%s(%d,%d)\n", "IF", ROW, COLUMN);
                    printIf();
                    System.exit(0);
                }
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(String.valueOf((char)nextChar));
                if (matcher.matches())
                {
                    idToken(reader,i + String.valueOf((char)tempChar) + String.valueOf((char)nextChar));
                }
                else if ((char)nextChar == '\n' || (char)nextChar == '\r')
                {
                    printIf();
                    COLUMN = 0;
                    ROW++;
                    reader.read();
                    start(reader);
                }
                else if ((char)nextChar == ' ')
                {
                    printIf();
                    start(reader);
                }
                else
                {
                    start(reader);
                }
            }
            else if (matcher.matches())
            {
                idToken(reader,i + String.valueOf((char)tempChar));
            }
            else if ((char)tempChar == '\n' || (char)tempChar == '\r')
            {
                printID(i);
                COLUMN = 0;
                ROW++;
                reader.read();
                start(reader);
            }
            else if ((char)tempChar == ' ')
            {
                printID(i);
                start(reader);
            }
            else
            {
                start(reader);
            }
        } catch (Exception e) {
        }
    }

    private static void idToken(Reader reader, String idName) {
        String idRegex = "_|[a-z]|[A-Z]|[0-9]";
        Pattern pattern = Pattern.compile(idRegex);
        try {
            int tempChar = reader.read();
            COLUMN++;
            if (tempChar == -1)
                {
                    printID(idName);
                    System.exit(0);
                }
            Matcher matcher = pattern.matcher(String.valueOf((char)tempChar));
            if (matcher.matches())
            {
                idToken(reader,idName + String.valueOf((char)tempChar));
            }
            else if ((char)tempChar == '\n' || (char)tempChar == '\r')
            {
                printID(idName);
                COLUMN = 0;
                ROW++;
                reader.read();
                //System.out.printf("ID(%s)\n",idName);
                start(reader);
            }
            else if ((char)tempChar == ' ')
            {
                //System.out.printf("ID(%s) ",idName);
                printID(idName);
                start(reader);
            }
            else
            {
                start(reader);
            }
        } catch (Exception e) {
        }
    }

    private static void printID(String idName) {
        System.out.printf("ID(%s)(%d,%d)\n",idName, ROW, COLUMN - idName.length());
    }

    private static void printIf() {
        System.out.printf("%s(%d,%d)\n", "IF", ROW, COLUMN - 2);
    }
}
