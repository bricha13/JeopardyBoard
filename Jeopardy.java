/**
 * this class uses the program latex2html, LatexFile class, and RetrieveXML class
 * generates a temporary latex file to convert latex math mode to png files
 * generates test directory that contains index.html this is the Jeopardy Board
 */
import java.io.*;
import java.util.Formatter;
import java.awt.List;
public class Jeopardy {
        private int qcount;
        private RetrieveXML XMLretriever;
        private LatexFile tex_file;
        private List html_questions;
        private String test_name;
/**
         * constructor
         * @throws IOException 
         */
    public Jeopardy(String dpath) throws IOException{
            XMLretriever = new RetrieveXML(dpath);
            html_questions = new List();
            tex_file = new LatexFile("temp.tex",dpath);
            tex_file.WriteLatexHead("temp");       
    }
    
/**
     * creates list of questions to add
     * adds problems to temp.tex
     * @param subject course subject title
     * @param section lesson section
     * @param difficulty difficulty of problems
     * @param questionQuantity number of problems to add
     */    
    public void WritehtmlQuestions(String subject, double section, int difficulty, int questionQuantity){
        List questionsbysubject = XMLretriever.returnByTopic(subject);
        List questionsbysection = XMLretriever.returnBySection(section);
        List questionsbydifficulty = XMLretriever.returnByDifficulty(difficulty);
        List temp_list = new List();
        List LatexQuestions = new List();

        for(int c=0;c<questionsbysubject.getItemCount();c++){
            for(int d=0;d<questionsbysection.getItemCount();d++){
                if(questionsbysubject.getItem(c).compareTo(questionsbysection.getItem(d))==0)
                    temp_list.add(questionsbysection.getItem(d));
            }
        }
        
        for(int c=0;c<temp_list.getItemCount();c++){
            for(int d=0;d<questionsbydifficulty.getItemCount();d++){
               if(temp_list.getItem(c).compareTo(questionsbydifficulty.getItem(d))==0)
                  LatexQuestions.add(questionsbydifficulty.getItem(c));
            }
        }
        tex_file.WriteLatexQuestions(subject, section, difficulty, questionQuantity);
        if(questionQuantity<=LatexQuestions.getItemCount()){
            for(int c=0;c<questionQuantity;c++){
                html_questions.add(LatexQuestions.getItem(c));
            }
        }
        else{
                System.out.println("Not enough questions in database. Adding " + LatexQuestions.getItemCount() + ".");
            for(int c=0;c<LatexQuestions.getItemCount();c++)
                html_questions.add(LatexQuestions.getItem(c));
        }
    }
    
/**
     * depends on latex2html. latex2html generates png files from math mode 
     * sections in the temp.tex file. the directory created by latex2html
     * is moved to a directory named after String tablename. generates index.html
     * and moves it to table directory
     * @param tablename name of html jeopardy board to be generated
     */
    public void GeneratehtmlTable(String tablename){
        String table_dir = tablename;
        table_dir = table_dir.replaceAll(" ", "_");
        try{
            tex_file.WriteLatexFoot();
            Process p;
            p = Runtime.getRuntime().exec("mkdir " + table_dir);
            p.waitFor();
            p=Runtime.getRuntime().exec("latex2html temp.tex");  
            p.waitFor();
            p=Runtime.getRuntime().exec("mv temp " + table_dir);
            p.waitFor();
            p=Runtime.getRuntime().exec("rm temp.tex");
            p.waitFor();
            File html_table = new File("index.html");
            if(!html_table.exists())
                html_table.createNewFile();
            Formatter html_table_io = new Formatter(html_table.getAbsolutePath());
            html_table_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            html_table_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            html_table_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            html_table_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title>" + tablename + "</title>\n");
            html_table_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"HTMLJeopardy/default.css\" />");
            html_table_io.format("<\\head>\n\n");
            html_table_io.format("<body>\n\n");
            html_table_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            html_table_io.format("<tr>\n");
            html_table_io.format("<td>Category</td><td>Category</td><td>Category</td><td>Category</td><td>Category</td><td>Category</td>\n");
            html_table_io.format("</tr>\n");
            html_table_io.format("<tr>\n");
            html_table_io.format("<td><a id=\"value1x1\" href=\"HTMLJeopardy/question1x1.html\">$200</a></td><td><a id=\"value1x2\" href=\"HTMLJeopardy/question1x2.html\">$200</a></td><td><a id=\"value1x3\" href=\"HTMLJeopardy/question1x3.html\">$200</a></td><td><a id=\"value1x4\" href=\"HTMLJeopardy/question1x4.html\">$200</a></td><td><a id=\"value1x5\" href=\"HTMLJeopardy/question1x5.html\">$200</a></td><td><a id=\"value1x6\" href=\"HTMLJeopardy/question1x6.html\">$200</a></td>\n");
            html_table_io.format("</tr>\n");
            html_table_io.format("<tr>\n");
            html_table_io.format("<td><a id=\"value1x1\" href=\"HTMLJeopardy/question1x1.html\">$400</a></td><td><a id=\"value1x2\" href=\"HTMLJeopardy/question1x2.html\">$400</a></td><td><a id=\"value1x3\" href=\"HTMLJeopardy/question1x3.html\">$400</a></td><td><a id=\"value1x4\" href=\"HTMLJeopardy/question1x4.html\">$400</a></td><td><a id=\"value1x5\" href=\"HTMLJeopardy/question1x5.html\">$400</a></td><td><a id=\"value1x6\" href=\"HTMLJeopardy/question1x6.html\">$400</a></td>\n");
            html_table_io.format("</tr>\n");
            html_table_io.format("<tr>\n");
            html_table_io.format("<td><a id=\"value1x1\" href=\"HTMLJeopardy/question1x1.html\">$600</a></td><td><a id=\"value1x2\" href=\"HTMLJeopardy/question1x2.html\">$600</a></td><td><a id=\"value1x3\" href=\"HTMLJeopardy/question1x3.html\">$600</a></td><td><a id=\"value1x4\" href=\"HTMLJeopardy/question1x4.html\">$600</a></td><td><a id=\"value1x5\" href=\"HTMLJeopardy/question1x5.html\">$600</a></td><td><a id=\"value1x6\" href=\"HTMLJeopardy/question1x6.html\">$600</a></td>\n");
            html_table_io.format("</tr>\n");
            html_table_io.format("<tr>\n");
            html_table_io.format("<td><a id=\"value1x1\" href=\"HTMLJeopardy/question1x1.html\">$800</a></td><td><a id=\"value1x2\" href=\"HTMLJeopardy/question1x2.html\">$800</a></td><td><a id=\"value1x3\" href=\"HTMLJeopardy/question1x3.html\">$800</a></td><td><a id=\"value1x4\" href=\"HTMLJeopardy/question1x4.html\">$800</a></td><td><a id=\"value1x5\" href=\"HTMLJeopardy/question1x5.html\">$800</a></td><td><a id=\"value1x6\" href=\"HTMLJeopardy/question1x6.html\">$800</a></td>\n");
            html_table_io.format("</tr>\n");
            html_table_io.format("<tr>\n");
            html_table_io.format("<td><a id=\"value1x1\" href=\"HTMLJeopardy/question1x1.html\">$1000</a></td><td><a id=\"value1x2\" href=\"HTMLJeopardy/question1x2.html\">$200</a></td><td><a id=\"value1x3\" href=\"HTMLJeopardy/question1x3.html\">$1000</a></td><td><a id=\"value1x4\" href=\"HTMLJeopardy/question1x4.html\">$1000</a></td><td><a id=\"value1x5\" href=\"HTMLJeopardy/question1x5.html\">$1000</a></td><td><a id=\"value1x6\" href=\"HTMLJeopardy/question1x6.html\">$1000</a></td>\n");
            html_table_io.format("</tr>\n");
            html_table_io.format("</table>\n\n");
            html_table_io.format("</body>\n\n");
            html_table_io.format("</html>");
            html_table_io.close();
            p=Runtime.getRuntime().exec("mv index.html " + table_dir);        
            p.waitFor();
        }
        catch(IOException e){
            System.out.println("IOexception");
            e.printStackTrace();
        }
        
        catch(InterruptedException d){
            System.out.println("InterruptedException");
        }
    }
    /**
     * depends on latex2html. latex2html generates png files from math mode 
     * sections in the temp.tex file. the directory created by latex2html
     * is moved to a directory named after String questionname. generates default.css
     * and moves it to question directory
     * @param questionname name of CSS file to be generated
     */
    public void GenerateCSS(String questionname){
        String question_dir = questionname;
        question_dir = question_dir.replaceAll(" ", "_");
        try{
            tex_file.WriteLatexFoot();
            Process p;
            p = Runtime.getRuntime().exec("mkdir " + question_dir);
            p.waitFor();
            p=Runtime.getRuntime().exec("latex2html temp.tex");  
            p.waitFor();
            p=Runtime.getRuntime().exec("mv temp " + question_dir);
            p.waitFor();
            p=Runtime.getRuntime().exec("rm temp.tex");
            p.waitFor();
            File modifications = new File("default.css");
            if(!modifications.exists())
                modifications.createNewFile();
            Formatter modifications_io = new Formatter(modifications.getAbsolutePath());
            modifications_io.format("table\n{\n text-align:center;\n color:white;\n background-color:blue;\n }\n\n");
            modifications_io.format("#value1x1\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value1x1:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value1x2\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value1x2:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value1x3\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value1x3:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value1x4\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value1x4:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value1x5\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value1x5:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value1x6\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value1x6:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value2x1\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value2x1:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value2x2\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value2x2:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value2x3\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value2x3:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value2x4\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value2x4:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value2x5\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value2x5:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value2x6\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value2x6:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value3x1\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value3x1:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value3x2\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value3x2:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value3x3\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value3x3:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value3x4\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value3x4:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value3x5\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value3x5:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value3x6\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value3x6:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value4x1\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value4x1:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value4x2\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value4x2:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value4x3\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value4x3:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value4x4\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value4x4:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value4x5\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value4x5:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value4x6\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value4x6:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value5x1\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value5x1:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value5x2\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value5x2:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value5x3\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value5x3:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value5x4\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value5x4:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value5x5\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value5x5:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.format("#value5x6\n {\n color:white;\n text-decoration:none;\n }\n\n");
            modifications_io.format("#value5x6:hover\n {\n color:yellow;\n font-weight:bold;\n }\n\n");
            modifications_io.close();
            p=Runtime.getRuntime().exec("mv default.css " + question_dir);        
            p.waitFor();          
        }
        catch(IOException e){
            System.out.println("IOexception");
            e.printStackTrace();
        }
        
        catch(InterruptedException d){
            System.out.println("InterruptedException");
        }
    }
    /**
     * depends on latex2html. latex2html generates png files from math mode 
     * sections in the temp.tex file. the directory created by latex2html
     * is moved to a directory named after String questionname.
     * generates all question files and moves it to question directory
     * @param questionname name of directory
     */
     public void GenerateQuestions(String questionname){
       try
       {
       String question_dir = questionname;
       question_dir = question_dir.replaceAll(" ", "_");
       Process p;  
       File question1 = new File("question1x1.html");
            if(!question1.exists())
                question1.createNewFile();
            Formatter question1_io = new Formatter(question1.getAbsolutePath());
            question1_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question1_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question1_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question1_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question1_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question1_io.format("<\\head>\n\n");
            question1_io.format("<body>\n\n");
            question1_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question1_io.format("<tr>\n");
            question1_io.format("<td id=\"question1x1\">"+XMLretriever.returnTestData(html_questions.getItem(1), "latex_instructions")+"<\td>\n");
            question1_io.format("<\tr>\n");
            question1_io.format("<\table>\n\n");
            question1_io.format("<\body>\n\n");
            question1_io.format("<\\html>");
            question1_io.close();
            p=Runtime.getRuntime().exec("mv question1x1.html " + question_dir);        
            p.waitFor();          
        
       File question2 = new File("question1x2.html");
            if(!question2.exists())
                question2.createNewFile();
            Formatter question2_io = new Formatter(question2.getAbsolutePath());
            question2_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question2_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question2_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question2_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question2_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question2_io.format("<\\head>\n\n");
            question2_io.format("<body>\n\n");
            question2_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question2_io.format("<tr>\n");
            question2_io.format("<td id=\"question1x2\">"+XMLretriever.returnTestData(html_questions.getItem(2), "latex_instructions")+"<\td>\n");
            question2_io.format("<\\tr>\n");
            question2_io.format("<\\table>\n\n");
            question2_io.format("<\\body>\n\n");
            question2_io.format("<\\html>");
            question2_io.close();
            p=Runtime.getRuntime().exec("mv question1x2.html " + question_dir);        
            p.waitFor();          
        
       File question3 = new File("question1x3.html");
            if(!question3.exists())
                question3.createNewFile();
            Formatter question3_io = new Formatter(question3.getAbsolutePath());
            question3_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question3_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question3_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question3_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question3_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question3_io.format("<\\head>\n\n");
            question3_io.format("<body>\n\n");
            question3_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question3_io.format("<tr>\n");
            question3_io.format("<td id=\"question1x3\">"+XMLretriever.returnTestData(html_questions.getItem(3), "latex_instructions")+"<\td>\n");
            question3_io.format("<\\tr>\n");
            question3_io.format("<\\table>\n\n");
            question3_io.format("<\\body>\n\n");
            question3_io.format("<\\html>");
            question3_io.close();
            p=Runtime.getRuntime().exec("mv question1x3.html " + question_dir);        
            p.waitFor();          
        
       File question4 = new File("question1x4.html");
            if(!question4.exists())
                question4.createNewFile();
            Formatter question4_io = new Formatter(question4.getAbsolutePath());
            question4_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question4_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question4_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question4_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question4_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question4_io.format("<\\head>\n\n");
            question4_io.format("<body>\n\n");
            question4_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question4_io.format("<tr>\n");
            question4_io.format("<td id=\"question1x4\">"+XMLretriever.returnTestData(html_questions.getItem(4), "latex_instructions")+"<\td>\n");
            question4_io.format("<\\tr>\n");
            question4_io.format("<\\table>\n\n");
            question4_io.format("<\\body>\n\n");
            question4_io.format("<\\html>");
            question4_io.close();
            p=Runtime.getRuntime().exec("mv question1x4.html " + question_dir);        
            p.waitFor();          
        
       File question5 = new File("question1x5.html");
            if(!question5.exists())
                question5.createNewFile();
            Formatter question5_io = new Formatter(question5.getAbsolutePath());
            question5_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question5_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question5_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question5_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question5_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question5_io.format("<\\head>\n\n");
            question5_io.format("<body>\n\n");
            question5_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question5_io.format("<tr>\n");
            question5_io.format("<td id=\"question1x5\">"+XMLretriever.returnTestData(html_questions.getItem(5), "latex_instructions")+"<\td>\n");
            question5_io.format("<\\tr>\n");
            question5_io.format("<\\table>\n\n");
            question5_io.format("<\\body>\n\n");
            question5_io.format("<\\html>");
            question5_io.close();
            p=Runtime.getRuntime().exec("mv question1x5.html " + question_dir);        
            p.waitFor();          
        
       File question6 = new File("question1x6.html");
            if(!question6.exists())
                question6.createNewFile();
            Formatter question6_io = new Formatter(question6.getAbsolutePath());
            question6_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question6_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question6_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question6_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question6_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question6_io.format("<\\head>\n\n");
            question6_io.format("<body>\n\n");
            question6_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question6_io.format("<tr>\n");
            question6_io.format("<td id=\"question1x6\">"+XMLretriever.returnTestData(html_questions.getItem(6), "latex_instructions")+"<\td>\n");
            question6_io.format("<\\tr>\n");
            question6_io.format("<\\table>\n\n");
            question6_io.format("<\\body>\n\n");
            question6_io.format("<\\html>");
            question6_io.close();
            p=Runtime.getRuntime().exec("mv question1x6.html " + question_dir);        
            p.waitFor();          
        
       File question7 = new File("question2x1.html");
            if(!question7.exists())
                question7.createNewFile();
            Formatter question7_io = new Formatter(question7.getAbsolutePath());
            question7_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question7_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question7_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question7_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question7_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question7_io.format("<\\head>\n\n");
            question7_io.format("<body>\n\n");
            question7_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question7_io.format("<tr>\n");
            question7_io.format("<td id=\"question2x1\">"+XMLretriever.returnTestData(html_questions.getItem(7), "latex_instructions")+"<\td>\n");
            question7_io.format("<\\tr>\n");
            question7_io.format("<\\table>\n\n");
            question7_io.format("<\\body>\n\n");
            question7_io.format("<\\html>");
            question7_io.close();
            p=Runtime.getRuntime().exec("mv question2x1.html " + question_dir);        
            p.waitFor();          
       
       File question8 = new File("question2x2.html");
            if(!question8.exists())
                question8.createNewFile();
            Formatter question8_io = new Formatter(question8.getAbsolutePath());
            question8_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question8_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question8_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question8_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question8_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question8_io.format("<\\head>\n\n");
            question8_io.format("<body>\n\n");
            question8_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question8_io.format("<tr>\n");
            question8_io.format("<td id=\"question2x2\">"+XMLretriever.returnTestData(html_questions.getItem(8), "latex_instructions")+"<\td>\n");
            question8_io.format("<\\tr>\n");
            question8_io.format("<\\table>\n\n");
            question8_io.format("<\\body>\n\n");
            question8_io.format("<\\html>");
            question8_io.close();
            p=Runtime.getRuntime().exec("mv question2x2.html " + question_dir);        
            p.waitFor();          
   
       File question9 = new File("question2x3.html");
            if(!question9.exists())
                question9.createNewFile();
            Formatter question9_io = new Formatter(question9.getAbsolutePath());
            question9_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question9_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question9_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question9_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question9_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question9_io.format("<\\head>\n\n");
            question9_io.format("<body>\n\n");
            question9_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question9_io.format("<tr>\n");
            question9_io.format("<td id=\"question2x3\">"+XMLretriever.returnTestData(html_questions.getItem(9), "latex_instructions")+"<\td>\n");
            question9_io.format("<\\tr>\n");
            question9_io.format("<\\table>\n\n");
            question9_io.format("<\\body>\n\n");
            question9_io.format("<\\html>");
            question9_io.close();
            p=Runtime.getRuntime().exec("mv question2x3.html " + question_dir);        
            p.waitFor();          
        
       File question10 = new File("question2x4.html");
            if(!question10.exists())
                question10.createNewFile();
            Formatter question10_io = new Formatter(question10.getAbsolutePath());
            question10_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question10_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question10_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question10_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question10_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question10_io.format("<\\head>\n\n");
            question10_io.format("<body>\n\n");
            question10_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question10_io.format("<tr>\n");
            question10_io.format("<td id=\"question2x4\">"+XMLretriever.returnTestData(html_questions.getItem(10), "latex_instructions")+"<\td>\n");
            question10_io.format("<\\tr>\n");
            question10_io.format("<\\table>\n\n");
            question10_io.format("<\\body>\n\n");
            question10_io.format("<\\html>");
            question10_io.close();
            p=Runtime.getRuntime().exec("mv question2x4.html " + question_dir);        
            p.waitFor();          
        
       File question11 = new File("question2x5.html");
            if(!question11.exists())
                question11.createNewFile();
            Formatter question11_io = new Formatter(question11.getAbsolutePath());
            question11_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question11_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question11_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question11_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question11_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question11_io.format("<\\head>\n\n");
            question11_io.format("<body>\n\n");
            question11_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question11_io.format("<tr>\n");
            question11_io.format("<td id=\"question1x5\">"+XMLretriever.returnTestData(html_questions.getItem(11), "latex_instructions")+"<\td>\n");
            question11_io.format("<\\tr>\n");
            question11_io.format("<\\table>\n\n");
            question11_io.format("<\\body>\n\n");
            question11_io.format("<\\html>");
            question11_io.close();
            p=Runtime.getRuntime().exec("mv question2x5.html " + question_dir);        
            p.waitFor();          
        
       File question12 = new File("question2x6.html");
            if(!question12.exists())
                question12.createNewFile();
            Formatter question12_io = new Formatter(question12.getAbsolutePath());
            question12_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question12_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question12_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question12_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question12_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question12_io.format("<\\head>\n\n");
            question12_io.format("<body>\n\n");
            question12_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question12_io.format("<tr>\n");
            question12_io.format("<td id=\"question2x6\">"+XMLretriever.returnTestData(html_questions.getItem(12), "latex_instructions")+"<\td>\n");
            question12_io.format("<\\tr>\n");
            question12_io.format("<\\table>\n\n");
            question12_io.format("<\\body>\n\n");
            question12_io.format("<\\html>");
            question12_io.close();
            p=Runtime.getRuntime().exec("mv question2x6.html " + question_dir);        
            p.waitFor();          
        
       File question13 = new File("question3x1.html");
            if(!question13.exists())
                question13.createNewFile();
            Formatter question13_io = new Formatter(question13.getAbsolutePath());
            question13_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question13_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question13_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question13_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question13_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question13_io.format("<\\head>\n\n");
            question13_io.format("<body>\n\n");
            question13_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question13_io.format("<tr>\n");
            question13_io.format("<td id=\"question3x1\">"+XMLretriever.returnTestData(html_questions.getItem(13), "latex_instructions")+"<\td>\n");
            question13_io.format("<\\tr>\n");
            question13_io.format("<\\table>\n\n");
            question13_io.format("<\\body>\n\n");
            question13_io.format("<\\html>");
            question13_io.close();
            p=Runtime.getRuntime().exec("mv question3x1.html " + question_dir);        
            p.waitFor();          
       
       File question14 = new File("question3x2.html");
            if(!question14.exists())
                question14.createNewFile();
            Formatter question14_io = new Formatter(question14.getAbsolutePath());
            question14_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question14_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question14_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question14_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question14_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question14_io.format("<\\head>\n\n");
            question14_io.format("<body>\n\n");
            question14_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question14_io.format("<tr>\n");
            question14_io.format("<td id=\"question3x2\">"+XMLretriever.returnTestData(html_questions.getItem(14), "latex_instructions")+"<\td>\n");
            question14_io.format("<\\tr>\n");
            question14_io.format("<\\table>\n\n");
            question14_io.format("<\\body>\n\n");
            question14_io.format("<\\html>");
            question14_io.close();
            p=Runtime.getRuntime().exec("mv question3x2.html " + question_dir);        
            p.waitFor();          
        
       File question15 = new File("question3x3.html");
            if(!question15.exists())
                question15.createNewFile();
            Formatter question15_io = new Formatter(question15.getAbsolutePath());
            question15_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question15_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question15_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question15_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question15_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question15_io.format("<\\head>\n\n");
            question15_io.format("<body>\n\n");
            question15_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question15_io.format("<tr>\n");
            question15_io.format("<td id=\"question3x3\">"+XMLretriever.returnTestData(html_questions.getItem(15), "latex_instructions")+"<\td>\n");
            question15_io.format("<\\tr>\n");
            question15_io.format("<\\table>\n\n");
            question15_io.format("<\\body>\n\n");
            question15_io.format("<\\html>");
            question15_io.close();
            p=Runtime.getRuntime().exec("mv question3x3.html " + question_dir);        
            p.waitFor();          
       
       File question16 = new File("question3x4.html");
            if(!question16.exists())
                question16.createNewFile();
            Formatter question16_io = new Formatter(question16.getAbsolutePath());
            question16_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question16_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question16_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question16_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question16_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question16_io.format("<\\head>\n\n");
            question16_io.format("<body>\n\n");
            question16_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question16_io.format("<tr>\n");
            question16_io.format("<td id=\"question3x4\">"+XMLretriever.returnTestData(html_questions.getItem(16), "latex_instructions")+"<\td>\n");
            question16_io.format("<\\tr>\n");
            question16_io.format("<\\table>\n\n");
            question16_io.format("<\\body>\n\n");
            question16_io.format("<\\html>");
            question16_io.close();
            p=Runtime.getRuntime().exec("mv question3x4.html " + question_dir);        
            p.waitFor();          
        
       File question17 = new File("question3x5.html");
            if(!question17.exists())
                question17.createNewFile();
            Formatter question17_io = new Formatter(question17.getAbsolutePath());
            question17_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question17_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question17_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question17_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question17_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question17_io.format("<\\head>\n\n");
            question17_io.format("<body>\n\n");
            question17_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question17_io.format("<tr>\n");
            question17_io.format("<td id=\"question3x5\">"+XMLretriever.returnTestData(html_questions.getItem(17), "latex_instructions")+"<\td>\n");
            question17_io.format("<\\tr>\n");
            question17_io.format("<\\table>\n\n");
            question17_io.format("<\\body>\n\n");
            question17_io.format("<\\html>");
            question17_io.close();
            p=Runtime.getRuntime().exec("mv question3x5.html " + question_dir);        
            p.waitFor();          
        
       File question18 = new File("question3x6.html");
            if(!question18.exists())
                question18.createNewFile();
            Formatter question18_io = new Formatter(question18.getAbsolutePath());
            question18_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question18_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question18_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question18_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question18_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question18_io.format("<\\head>\n\n");
            question18_io.format("<body>\n\n");
            question18_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question18_io.format("<tr>\n");
            question18_io.format("<td id=\"question3x6\">"+XMLretriever.returnTestData(html_questions.getItem(18), "latex_instructions")+"<\td>\n");
            question18_io.format("<\\tr>\n");
            question18_io.format("<\\table>\n\n");
            question18_io.format("<\\body>\n\n");
            question18_io.format("<\\html>");
            question18_io.close();
            p=Runtime.getRuntime().exec("mv question3x6.html " + question_dir);        
            p.waitFor();          
        
       File question19 = new File("question4x1.html");
            if(!question19.exists())
                question19.createNewFile();
            Formatter question19_io = new Formatter(question19.getAbsolutePath());
            question19_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question19_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question19_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question19_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question19_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question19_io.format("<\\head>\n\n");
            question19_io.format("<body>\n\n");
            question19_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question19_io.format("<tr>\n");
            question19_io.format("<td id=\"question4x1\">"+XMLretriever.returnTestData(html_questions.getItem(19), "latex_instructions")+"<\td>\n");
            question19_io.format("<\\tr>\n");
            question19_io.format("<\\table>\n\n");
            question19_io.format("<\\body>\n\n");
            question19_io.format("<\\html>");
            question19_io.close();
            p=Runtime.getRuntime().exec("mv question4x1.html " + question_dir);        
            p.waitFor();          
        
       File question20 = new File("question4x2.html");
            if(!question20.exists())
                question20.createNewFile();
            Formatter question20_io = new Formatter(question20.getAbsolutePath());
            question20_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question20_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question20_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question20_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question20_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question20_io.format("<\\head>\n\n");
            question20_io.format("<body>\n\n");
            question20_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question20_io.format("<tr>\n");
            question20_io.format("<td id=\"question4x2\">"+XMLretriever.returnTestData(html_questions.getItem(20), "latex_instructions")+"<\td>\n");
            question20_io.format("<\\tr>\n");
            question20_io.format("<\\table>\n\n");
            question20_io.format("<\\body>\n\n");
            question20_io.format("<\\html>");
            question20_io.close();
            p=Runtime.getRuntime().exec("mv question4x2.html " + question_dir);        
            p.waitFor();          
       
       File question21 = new File("question4x3.html");
            if(!question21.exists())
                question21.createNewFile();
            Formatter question21_io = new Formatter(question21.getAbsolutePath());
            question21_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question21_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question21_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question21_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question21_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question21_io.format("<\\head>\n\n");
            question21_io.format("<body>\n\n");
            question21_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question21_io.format("<tr>\n");
            question21_io.format("<td id=\"question4x3\">"+XMLretriever.returnTestData(html_questions.getItem(21), "latex_instructions")+"<\td>\n");
            question21_io.format("<\\tr>\n");
            question21_io.format("<\\table>\n\n");
            question21_io.format("<\\body>\n\n");
            question21_io.format("<\\html>");
            question21_io.close();
            p=Runtime.getRuntime().exec("mv question4x3.html " + question_dir);        
            p.waitFor();          
        
       File question22 = new File("question1x4.html");
            if(!question22.exists())
                question22.createNewFile();
            Formatter question22_io = new Formatter(question22.getAbsolutePath());
            question22_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question22_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question22_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question22_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question22_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question22_io.format("<\\head>\n\n");
            question22_io.format("<body>\n\n");
            question22_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question22_io.format("<tr>\n");
            question22_io.format("<td id=\"question4x4\">"+XMLretriever.returnTestData(html_questions.getItem(22), "latex_instructions")+"<\td>\n");
            question22_io.format("<\\tr>\n");
            question22_io.format("<\\table>\n\n");
            question22_io.format("<\\body>\n\n");
            question22_io.format("<\\html>");
            question22_io.close();
            p=Runtime.getRuntime().exec("mv question4x4.html " + question_dir);        
            p.waitFor();          
       
       File question23 = new File("question4x5.html");
            if(!question23.exists())
                question23.createNewFile();
            Formatter question23_io = new Formatter(question23.getAbsolutePath());
            question23_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question23_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question23_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question23_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question23_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question23_io.format("<\\head>\n\n");
            question23_io.format("<body>\n\n");
            question23_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question23_io.format("<tr>\n");
            question23_io.format("<td id=\"question4x5\">"+XMLretriever.returnTestData(html_questions.getItem(23), "latex_instructions")+"<\td>\n");
            question23_io.format("<\\tr>\n");
            question23_io.format("<\\table>\n\n");
            question23_io.format("<\\body>\n\n");
            question23_io.format("<\\html>");
            question23_io.close();
            p=Runtime.getRuntime().exec("mv question4x5.html " + question_dir);        
            p.waitFor();          
        
       File question24 = new File("question4x6.html");
            if(!question24.exists())
                question24.createNewFile();
            Formatter question24_io = new Formatter(question24.getAbsolutePath());
            question24_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question24_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question24_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question24_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question24_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question24_io.format("<\\head>\n\n");
            question24_io.format("<body>\n\n");
            question24_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question24_io.format("<tr>\n");
            question24_io.format("<td id=\"question4x6\">"+XMLretriever.returnTestData(html_questions.getItem(24), "latex_instructions")+"<\td>\n");
            question24_io.format("<\\tr>\n");
            question24_io.format("<\\table>\n\n");
            question24_io.format("<\\body>\n\n");
            question24_io.format("<\\html>");
            question24_io.close();
            p=Runtime.getRuntime().exec("mv question4x6.html " + question_dir);        
            p.waitFor();          
        
       File question25 = new File("question5x1.html");
            if(!question25.exists())
                question25.createNewFile();
            Formatter question25_io = new Formatter(question25.getAbsolutePath());
            question25_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question25_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question25_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question25_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question25_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question25_io.format("<\\head>\n\n");
            question25_io.format("<body>\n\n");
            question25_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question25_io.format("<tr>\n");
            question25_io.format("<td id=\"question5x1\">"+XMLretriever.returnTestData(html_questions.getItem(25), "latex_instructions")+"<\td>\n");
            question25_io.format("<\\tr>\n");
            question25_io.format("<\\table>\n\n");
            question25_io.format("<\\body>\n\n");
            question25_io.format("<\\html>");
            question25_io.close();
            p=Runtime.getRuntime().exec("mv question5x1.html " + question_dir);        
            p.waitFor();          
        
       File question26 = new File("question5x2.html");
            if(!question26.exists())
                question26.createNewFile();
            Formatter question26_io = new Formatter(question26.getAbsolutePath());
            question26_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question26_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question26_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question26_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question26_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question26_io.format("<\\head>\n\n");
            question26_io.format("<body>\n\n");
            question26_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question26_io.format("<tr>\n");
            question26_io.format("<td id=\"question5x2\">"+XMLretriever.returnTestData(html_questions.getItem(26), "latex_instructions")+"<\td>\n");
            question26_io.format("<\\tr>\n");
            question26_io.format("<\\table>\n\n");
            question26_io.format("<\\body>\n\n");
            question26_io.format("<\\html>");
            question26_io.close();
            p=Runtime.getRuntime().exec("mv question5x2.html " + question_dir);        
            p.waitFor();          
       
       File question27 = new File("question5x3.html");
            if(!question27.exists())
                question27.createNewFile();
            Formatter question27_io = new Formatter(question27.getAbsolutePath());
            question27_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question27_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question27_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question27_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question27_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question27_io.format("<\\head>\n\n");
            question27_io.format("<body>\n\n");
            question27_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question27_io.format("<tr>\n");
            question27_io.format("<td id=\"question5x3\">"+XMLretriever.returnTestData(html_questions.getItem(27), "latex_instructions")+"<\td>\n");
            question27_io.format("<\\tr>\n");
            question27_io.format("<\\table>\n\n");
            question27_io.format("<\\body>\n\n");
            question27_io.format("<\\html>");
            question27_io.close();
            p=Runtime.getRuntime().exec("mv question5x3.html " + question_dir);        
            p.waitFor();          
       
       File question28 = new File("question5x4.html");
            if(!question28.exists())
                question28.createNewFile();
            Formatter question28_io = new Formatter(question28.getAbsolutePath());
            question28_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question28_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question28_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question28_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question28_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question28_io.format("<\\head>\n\n");
            question28_io.format("<body>\n\n");
            question28_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question28_io.format("<tr>\n");
            question28_io.format("<td id=\"question5x4\">"+XMLretriever.returnTestData(html_questions.getItem(28), "latex_instructions")+"<\td>\n");
            question28_io.format("<\\tr>\n");
            question28_io.format("<\\table>\n\n");
            question28_io.format("<\\body>\n\n");
            question28_io.format("<\\html>");
            question28_io.close();
            p=Runtime.getRuntime().exec("mv question5x4.html " + question_dir);        
            p.waitFor();          
       
       File question29 = new File("question5x5.html");
            if(!question29.exists())
                question29.createNewFile();
            Formatter question29_io = new Formatter(question29.getAbsolutePath());
            question29_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question29_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question29_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question29_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question29_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question29_io.format("<\\head>\n\n");
            question29_io.format("<body>\n\n");
            question29_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question29_io.format("<tr>\n");
            question29_io.format("<td id=\"question5x5\">"+XMLretriever.returnTestData(html_questions.getItem(29), "latex_instructions")+"<\td>\n");
            question29_io.format("<\\tr>\n");
            question29_io.format("<\\table>\n\n");
            question29_io.format("<\\body>\n\n");
            question29_io.format("<\\html>");
            question29_io.close();
            p=Runtime.getRuntime().exec("mv question5x5.html " + question_dir);        
            p.waitFor();          
        
       File question30 = new File("question5x6.html");
            if(!question30.exists())
                question30.createNewFile();
            Formatter question30_io = new Formatter(question30.getAbsolutePath());
            question30_io.format("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
            question30_io.format("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n");
            question30_io.format("<html xlms=\"http://www.w3.org/1999/xhtml\">\n\n");
            question30_io.format("<head>\n<meta http-equiv=\"Content-Type\" conetent=\"text/html; charset=utf-8\" />\n<title></title>\n");
            question30_io.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\" />");
            question30_io.format("<\\head>\n\n");
            question30_io.format("<body>\n\n");
            question30_io.format("<table align=\"center\" border=\"1\" width=\"600\" height=\"450\">\n");
            question30_io.format("<tr>\n");
            question30_io.format("<td id=\"question5x6\">"+XMLretriever.returnTestData(html_questions.getItem(30), "latex_instructions")+"<\td>\n");
            question30_io.format("<\\tr>\n");
            question30_io.format("<\\table>\n\n");
            question30_io.format("<\\body>\n\n");
            question30_io.format("<\\html>");
            question30_io.close();
            p=Runtime.getRuntime().exec("mv question5x6.html " + question_dir);        
            p.waitFor();          
        }
        catch(IOException e){
            System.out.println("IOexception");
            e.printStackTrace();
        }
        
        catch(InterruptedException d){
            System.out.println("InterruptedException");
        }
     }
}