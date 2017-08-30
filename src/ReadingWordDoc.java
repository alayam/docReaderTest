import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReadDocFile
{
    Map<String, Integer> inputWords = new HashMap<>();
    Map<String, Integer> resultWords = new HashMap<>();

    private void readFile(String fieleName, Map<String, Integer> words) {

        System.out.println("==================================" + fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                String[] split = sCurrentLine.split(" ");
                for(String s: split) {
                    Integer count = words.get(s);
                    if(count !=null ) {
                        count = count + 1;
                        words.put(s ,count);
                    }else {
                        words.put(s ,1);
                    }
                }
                System.out.println(sCurrentLine);
            }


            //Print out word count
//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public boolean compareDocuments() {
        if(resultWords.size() != inputWords.size()) {
            return false;
        }

        Set<String> words = resultWords.keySet();
        Iterator<String> iterator = words.iterator();

        while(iterator.hasNext()) {
            String s = iterator.next();
            Integer count = inputWords.get(s);
            if( count == null || !inputWords.get(s).equals(resultWords.get(s))) {
                return false;
            }
            inputWords.remove(s);
        }

        return inputWords.isEmpty();
    }

    public static void main(String[] args) {
        ReadDocFile r = new ReadDocFile();
        r.readInputFile("/tmp/1.txt","/tmp/2.txt");
        r.readResultFile("/tmp/3.txt");

        System.out.println("Documents isEqual "+r.compareDocuments());

    }

    public void readInputFile(String s1, String s2) {
        readFile(s1, inputWords);
        readFile(s2, inputWords);
    }

    public void readResultFile(String s) {
        readFile(s,resultWords);
    }

    public static void main1(String[] args)
    {
        File file = null;
        WordExtractor extractor = null;
        try
        {

            file = new File("test.txt");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] fileData = extractor.getParagraphText();
            for (int i = 0; i < fileData.length; i++)
            {
                if (fileData[i] != null)
                    System.out.println(fileData[i]);
            }
        }
        catch (Exception exep)
        {
            exep.printStackTrace();
        }
    }
}