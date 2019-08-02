import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherDemo {

    private static final String REGEX =
        "(\\p{Punct}?)(\\w+\\.?\\w+)(:|!|>|<|~)(\\p{Punct}?)(\\p{L}+)(\\p{Punct}?),";
     //  " (\\w+\\.?\\w+)(:|!|>|<|~)(\\w+?),";
    private static final String INPUT =
        "employee.name:钟翼翔,";
         //"id>xs00001,";
        public static String searchParameters ="id>xs00001,";

    public static void main(String[] args) {
       Pattern p = Pattern.compile(REGEX);
       //  get a matcher object
       Matcher m = p.matcher(INPUT);
       int count = 0;
       while(m.find()) {
           System.err.println(m.group(1)+"1--"+m.group(2)+"2--"+m.group(3)+"3--"+m.group(4)+"4--"+m.group(5)+"5--"+m.group(6));
           count++;
           System.out.println("Match number "
                              + count);
           System.out.println("start(): "
                              + m.start());
           System.out.println("end(): "
                              + m.end());
      }
      Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?\\.?\\w+?)(:|!|>|<|~)(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
      Matcher matcher = pattern.matcher(INPUT);
      while (matcher.find()) {
          System.err.println(matcher.group(1)+"11--"+matcher.group(2)+"22--"+matcher.group(3)+"--"+matcher.group(4)+"--"+matcher.group(5)+"--"+matcher.group(6));
        //   builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
      }
   }
}
