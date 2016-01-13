import static spark.Spark.*;
import spark.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LabWeb {
	public static void main(String[] args) {
		get("/hello", (req, resp) -> {
			return "Hello World.";
		});
    get("/add", (req, resp) -> {
        String message;

        try {
            String a = req.queryParams("a");
            String b = req.queryParams("b");
            if(a == null) {
                message = "The \"a\" parameter is missing.";
            } else if (b == null) {
                message = "The \"b\" parameter is missing.";
            } else {
                float result = Float.parseFloat(a) + Float.parseFloat(b);
                message = a + " + " + b + " = " + result;
            }
        } catch(Exception e) {
            message = e.toString();
        }
        return message;
    });

    get("/gettest", (req, resp) -> {
      String getString;
      try {
          String str = req.queryParams("str");
          if(str == null) {
            getString = "The \"str\" parameter is missing.";
          } else {
              getString = "The string \'" + str + "\' was posted";
          }
      } catch(Exception e) {
          getString = e.toString();
      }
      return getString;
    });

    get("/texttest", (req, resp) -> {
      String getString;
      try {
          String str = req.queryParams("loc");
          if(str == null) {
            getString = "The \"loc\" parameter is missing.";
          } else {
              getString = new String(Files.readAllBytes(Paths.get(str)));
          }
      } catch(Exception e) {
          getString = e.toString();
      }
      return getString;
    });
	}
}
