import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReadWrite {
    public static void main(String []args) {
        try {
            FileReader fr = new FileReader("D:\\\\test.txt");
            FileWriter fw = new FileWriter("E:\\\\testwrite.txt");

            int c;

            while((c=fr.read())!=-1) {
                fw.write(c);
                System.out.print((char)c);
            }

            fr.close();
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


