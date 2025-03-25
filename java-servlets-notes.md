# Java Servlets
## Introduction to Servlets

Servlets are small programs that execute on the server side of a Web connection. Just as applets dynamically extend the functionality of a Web browser, servlets dynamically extend the functionality of a Web server.

> A servlet is a Java programming language class used to extend the capabilities of servers that host applications accessed via a request-response programming model.

Servlets are commonly used to extend applications hosted by Web servers. For such applications, Java Servlet technology defines HTTP-specific servlet classes. The `javax.servlet` and `javax.servlet.http` packages provide interfaces and classes for writing servlets.

## Life Cycle of a Servlet

Three methods are central to the life cycle of a servlet:

1. **init()** - Called when the servlet is first loaded into memory
2. **service()** - Called to process HTTP requests
3. **destroy()** - Called when the server unloads the servlet

### User Scenario:

1. A user enters a URL in a web browser, generating an HTTP request
2. The web server receives this HTTP request and maps it to a particular servlet
3. The server loads the servlet into memory if not already loaded
4. The server invokes the `init()` method (only when first loaded)
5. The server invokes the `service()` method to process the HTTP request
6. If the server decides to unload the servlet, it calls the `destroy()` method

## The Servlet API

Two packages contain the classes and interfaces required to build servlets:
- `javax.servlet`
- `javax.servlet.http`

These are standard extensions, not part of Java core packages.

### The javax.servlet Package

Core Interfaces:

| Interface | Description |
|-----------|-------------|
| Servlet | Declares life cycle methods for a servlet |
| ServletConfig | Allows servlets to get initialization parameters |
| ServletContext | Enables servlets to log events and access information about their environment |
| ServletRequest | Used to read data from a client request |
| ServletResponse | Used to write data to a client response |

Core Classes:

| Class | Description |
|-------|-------------|
| GenericServlet | Implements the Servlet and ServletConfig interfaces |
| ServletInputStream | Provides an input stream for reading requests from a client |
| ServletOutputStream | Provides an output stream for writing responses to a client |
| ServletException | Indicates a servlet error occurred |
| UnavailableException | Indicates a servlet is unavailable |

#### The Servlet Interface

All servlets must implement the Servlet interface which declares `init()`, `service()`, and `destroy()` methods.
```java
public interface Servlet {
    public void init(ServletConfig config) throws ServletException;

    public ServletConfig getServletConfig();

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;
    public String getServletInfo();

    public void destroy();
}
```

#### The ServletConfig Interface

Allows a servlet to obtain configuration data when it is loaded.
```java
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfigServlet extends HttpServlet {
    private String dbURL;
    private String dbUser;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbURL = config.getInitParameter("dbURL");
        dbUser = config.getInitParameter("dbUser");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Database Configuration</h2>");
        out.println("<p>DB URL: " + dbURL + "</p>");
        out.println("<p>DB User: " + dbUser + "</p>");
    }
}
```

#### The ServletContext Interface

Enables servlets to obtain information about their environment.
```java
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        ServletContext context = getServletContext();
        String appName = context.getInitParameter("appName");

        out.println("<h2>Application Context</h2>");
        out.println("<p>Application Name: " + appName + "</p>");
        out.println("<p>Server Info: " + context.getServerInfo() + "</p>");
    }
}
```

#### The ServletRequest Interface

Enables a servlet to obtain information about a client request.
```java
public interface ServletRequest {
    public String getParameter(String name);
    public Enumeration<String> getParameterNames();
    public String[] getParameterValues(String name);
    public Map<String, String[]> getParameterMap();
    public String getCharacterEncoding();
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException;
    // Additional methods...
}
```

#### The ServletResponse Interface

Enables a servlet to formulate a response for a client.
```java
public interface ServletResponse {
    public String getCharacterEncoding();
    public void setContentType(String type);
    public PrintWriter getWriter() throws IOException;
    public ServletOutputStream getOutputStream() throws IOException;
    // Additional methods...
}
```

#### The GenericServlet Class

Provides implementations of the basic life cycle methods for a servlet. It implements the Servlet and ServletConfig interfaces.
```java
public abstract class GenericServlet implements Servlet, ServletConfig, Serializable {
    private transient ServletConfig config;
    
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }
    
    public void init() throws ServletException {
        // Can be overridden by subclasses
    }
    
    public abstract void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;
    
    // Additional methods...
}
```

#### The ServletInputStream and ServletOutputStream Classes

Extend InputStream and OutputStream respectively to read client requests and write client responses.
```java
public abstract class ServletInputStream extends InputStream {
    public int readLine(byte[] b, int off, int len) throws IOException {
        // Implementation details
    }
    
    // Additional methods...
}
```

#### The Servlet Exception Classes

- ServletException: Indicates a servlet problem
- UnavailableException: Indicates a servlet is unavailable

## Reading Servlet Parameters

The ServletRequest class includes methods to read names and values of parameters included in a client request.

### Example:

**index.jsp**:
```html
<html>
<body>
<center>
<form name="Form1"
      method="post"
      action="http://localhost:8080/servlets-examples/servlet/PostParametersServlet">
<table>
<tr>
    <td><B>Employee</td>
    <td><input type=textbox name="e" size="25" value=""></td>
</tr>
<tr>
    <td><B>Phone</td>
    <td><input type=textbox name="p" size="25" value=""></td>
</tr>
</table>
<input type=submit value="Submit">
</body>
</html>
```

**PostParametersServlet.java**:
```java
import java.io.*;
import java.util.*;
import javax.servlet.*;

public class PostParametersServlet extends GenericServlet {
    public void service(ServletRequest request, ServletResponse response)
        throws ServletException, IOException {
        // Get print writer.
        PrintWriter pw = response.getWriter();
        
        // Get enumeration of parameter names.
        Enumeration e = request.getParameterNames();
        
        // Display parameter names and values.
        while(e.hasMoreElements()) {
            String pname = (String)e.nextElement();
            pw.print(pname + " = ");
            String pvalue = request.getParameter(pname);
            pw.println(pvalue);
        }
        pw.close();
    }
}
```

**Output**:
```
e = navin
p = 9841
```

## The javax.servlet.http Package

This package contains interfaces and classes used by servlet developers for HTTP requests and responses.

Core Interfaces:

| Interface | Description |
|-----------|-------------|
| HttpServletRequest | Provides HTTP request information |
| HttpServletResponse | Provides HTTP response functionality |
| HttpSession | Provides session tracking information |

Core Classes:

| Class | Description |
|-------|-------------|
| HttpServlet | Base class for HTTP servlets |
| Cookie | Encapsulates a cookie for tracking user data |

### The HttpServletRequest Interface

Implemented by the server to provide information about a client request.
```java
public interface HttpServletRequest extends ServletRequest {
    public String getMethod();
    public String getRequestURI();
    public StringBuffer getRequestURL();
    public String getServletPath();
    public Cookie[] getCookies();
    public String getHeader(String name);
    public Enumeration<String> getHeaderNames();
    public HttpSession getSession(boolean create);
    // Additional methods...
}
```

### The HttpServletResponse Interface

Implemented by the server to enable a servlet to formulate an HTTP response. It defines constants like SC_OK and SC_NOT_FOUND for HTTP status codes.
```java
public interface HttpServletResponse extends ServletResponse {
    public static final int SC_OK = 200;
    public static final int SC_NOT_FOUND = 404;
    public static final int SC_INTERNAL_SERVER_ERROR = 500;
    
    public void setStatus(int sc);
    public void sendRedirect(String location) throws IOException;
    public void addCookie(Cookie cookie);
    public void setHeader(String name, String value);
    // Additional methods...
}
```

### The HttpSession Interface
```java
public interface HttpSession {
    public String getId();
    public Object getAttribute(String name);
    public void setAttribute(String name, Object value);
    public void removeAttribute(String name);
    public void invalidate();
    // Additional methods...
}
```

### The Cookie Class

Encapsulates a cookie that is stored on a client and contains state information. Cookies track user activities and store information like:
- Cookie name
- Cookie value
- Expiration date
- Domain and path

### The HttpServlet Class

Extends GenericServlet and is commonly used for servlets that receive and process HTTP requests.
```java
public abstract class HttpServlet extends GenericServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Default implementation sends error
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Default implementation sends error
    }
    
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        service(request, response);
    }
    
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("GET")) {
            doGet(req, resp);
        } else if (method.equals("POST")) {
            doPost(req, resp);
        }
        // Handle other HTTP methods...
    }
    
    // Additional methods...
}
```

## Handling HTTP Requests and Responses

The HttpServlet class provides specialized methods for handling various types of HTTP requests:
- doDelete()
- doGet()
- doHead()
- doOptions()
- doPost()
- doPut()
- doTrace()

### Handling HTTP GET Requests

Example with ColorGet.html and ColorGetServlet.java:

When a user selects an option and submits the form, a URL with a query string is sent to the server (e.g., `http://localhost:8080/servlets-examples/servlet/ColorGetServlet?color=red`).

**doGet(HttpServletRequest request, HttpServletResponse response)**

- Handles HTTP GET requests.
- Typically used to retrieve data from the server.
- In the example, it retrieves:
- Application name (ServletContext).
- Database configuration (ServletConfig).
- Returns an HTML response displaying this information.

**Example Usage:**
```bash
curl -X GET http://localhost:8080/myapp/myservlet
```
**Common Use Cases:**
- Loading a web page.
- Fetching records from a database.
- API calls to retrieve data.

### Handling HTTP POST Requests

Example with ColorPost.html and ColorPostServlet.java.

**doPost(HttpServletRequest request, HttpServletResponse response)**

- Handles HTTP POST requests.
- Typically used to send data to the server (e.g., form submissions).
- The example retrieves form data (request.getParameter("data")) and prints it.

**Example Usage:**
```bash
curl -X POST -d "data=sample" http://localhost:8080/myapp/myservlet
```
**Common Use Cases:**
- Submitting a login form.
- Sending user comments or feedback.
- Creating a new record in a database.

### Handling HTTP PUT Requests
**doPut(HttpServletRequest request, HttpServletResponse response)**
- Handles HTTP PUT requests.
- Typically used to update an existing resource on the server.
- In the example, it simply prints a message indicating that a resource is being updated.

**Example Usage:**
```bash
curl -X PUT http://localhost:8080/myapp/myservlet
```
**Common Use Cases:**
- Updating user profile details.
- Modifying an existing database record.

### Handling HTTP DELETE Requests
**doDelete(HttpServletRequest request, HttpServletResponse response)**
- Handles HTTP DELETE requests.
- Typically used to delete a resource from the server.
- The example prints a message indicating that a resource has been deleted.

**Example Usage:**
```bash
curl -X DELETE http://localhost:8080/myapp/myservlet
```
**Common Use Cases:**
- Deleting a user account.
- Removing a record from a database.

### Handling HTTP HEAD Requests
**doHead(HttpServletRequest request, HttpServletResponse response)**
- Handles HTTP HEAD requests.
- Works like GET but only returns response headers, not the body.
- The example sets the response status to 200 OK.

**Example Usage:**
```bash
curl -I http://localhost:8080/myapp/myservlet
```
**Common Use Cases:**
- Checking if a resource exists without downloading it.
- Used by web crawlers and caching mechanisms.


### Handling HTTP OPTIONS Requests
**doOptions(HttpServletRequest request, HttpServletResponse response)**
- Handles HTTP OPTIONS requests.
- Used to check which HTTP methods are allowed on the server.
- The example sets the Allow header to list all supported methods.

**Example Usage:**
```bash
curl -X OPTIONS -i http://localhost:8080/myapp/myservlet
```
**Common Use Cases:**
- Checking which HTTP methods a server supports.
- CORS (Cross-Origin Resource Sharing) preflight requests.


### Handling HTTP TRACE Requests
**doTrace(HttpServletRequest request, HttpServletResponse response)**
- Handles HTTP TRACE requests.
- Used for debugging by returning the original request details.
- The example simply prints "TRACE Request Processed".

**Example Usage:**
```bash
curl -X TRACE http://localhost:8080/myapp/myservlet
```
**Common Use Cases:**
- Used to debug HTTP requests by reflecting them back.
- Rarely used in modern applications for security reasons.


## Using Cookies

Examples demonstrating cookie management:
- AddCookie.html
- AddCookieServlet.java
- GetCookiesServlet.java

A cookie can be written to a user's machine using the `addCookie()` method of HttpServletResponse.

```java
public class Cookie implements Cloneable, Serializable {
    private String name;
    private String value;
    
    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public void setMaxAge(int expiry) {
        // Implementation
    }
    
    public void setPath(String uri) {
        // Implementation
    }
    
    // Additional methods...
}
```

## Session Tracking

A session can be created via the `getSession()` method of HttpServletRequest. This returns an HttpSession object that can store bindings (name-object pairs).

Methods for managing bindings:
- setAttribute()
- getAttribute()
- getAttributeNames()
- removeAttribute()

### Example:

```java
HttpSession session = request.getSession(true);
Date date = (Date)session.getAttribute("date");
// Process date information
Date current = new Date();
session.setAttribute("date", current);
```

## Introduction to JSP (Java Server Pages)

JSP is a server-side technology for creating dynamic web content. It's an advanced version of Servlet Technology where JSP tags are used to insert Java code into HTML pages.

### Comparing JSP with Servlet

| Key | Servlet | JSP |
|-----|---------|-----|
| Implementation | Developed in Java | Primarily written in HTML with Java code |
| MVC | Controller that processes requests | View that renders responses |
| Request type | All protocol requests | HTTP requests only |
| Session Management | Not enabled by default | Automatically enabled |
| Performance | Faster | Slower due to translation and compilation |
| Modification | Time-consuming (reload, recompile) | Fast (just refresh) |

### Features of JSP
- Easy coding (adding Java to HTML/XML)
- Reduced code length using action tags
- Easy database connection
- Interactive websites with dynamic content
- Portable, powerful, flexible, and maintainable
- No redeployment or recompilation needed
- Extension to Servlet with implicit objects and custom tags

### JSP Syntax

1. **Declaration Tag**: Used to declare variables
   ```jsp
   <%! int var=10; %>
   ```

2. **Java Scriplets**: Allows adding Java code
   ```jsp
   <% java code %>
   ```

3. **JSP Expression**: Evaluates and converts expressions to strings
   ```jsp
   <%= expression %>
   ```

4. **Java Comments**: Text added for information
   ```jsp
   <%-- JSP Comments --%>
   ```

### Example JSPs

**Simple JSP**:
```jsp
<%-- JSP comment --%>
<HTML>
<HEAD>
<TITLE>MESSAGE</TITLE>
</HEAD>
<BODY>
<%out.print("Hello, Sample JSP code");%>
</BODY>
</HTML>
```

**Date Display JSP**:
```jsp
<HTML>
<BODY>
Hello BeginnersBook Readers!
Current time is: <%= new java.util.Date() %>
</BODY>
</HTML>
```

## Java Web Frameworks

Java Frameworks are platforms of pre-written code used to develop Java applications or web applications.

### Popular Java Frameworks

1. **Spring**
   - Lightweight, powerful Java application development framework
   - Modules: Spring Security, Spring MVC, Spring Batch, Spring ORM, Spring Boot
   - Advantages: Loose coupling, lightweight, fast development, powerful abstraction

2. **Hibernate**
   - ORM (Object-Relation Mapping) framework
   - Establishes communication between Java and RDBMS
   - Advantages: Portability, productivity, maintainability, avoids repetitive JDBC code

3. **Grails**
   - Dynamic framework using Groovy programming language
   - Works with Java, JEE, Spring, and Hibernate
   - Advantages: Similar to Java, easy object mapping, code reuse

4. **Play**
   - Unique framework not following JEE standards
   - Follows MVC architecture
   - Used for scalable Java applications
   - Advantages: No configuration required, enhances productivity

5. **JavaServer Faces (JSF)**
   - Component-based UI framework by Oracle
   - Follows MVC design pattern
   - Advantages: Part of JEE, provides rich libraries

6. **Google Web Toolkit (GWT)**
   - Open-source framework for client-side Java code
   - Used to develop complex browser applications
   - Products like Google AdSense and Blogger use GWT
   - Advantages: Reusability, Google API access, UI abstraction

7. **Quarkus**
   - Modern, full-stack, Kubernetes-native Java framework
   - Offers small memory footprint and reduced boot time
   - Advantages: Good for cloud environments, supports microservices

### Advantages of Java Frameworks
- Security: Easy to fix vulnerabilities
- Support: Large communities and documentation
- Efficiency: Faster, easier development
- Cost-effectiveness: Lower maintenance costs

### Framework vs. Library

| Library | Framework |
|---------|-----------|
| Collection of frequently used classes | Collection of libraries |
| Set of reusable functions | Dictates project architecture |
| You control when to call methods | Framework calls your code |
| Can be incorporated into existing projects | Better for new projects |
| Important for linking and binding | Provides standard way to build applications |
| No inverted flow of control | Employs inverted flow of control |
| Example: jQuery | Example: Angular JS |
