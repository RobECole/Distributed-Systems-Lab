import static spark.Spark.*;
import spark.*;

public class HelloWorld {
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
	}
}
