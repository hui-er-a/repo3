import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;


@WebServlet("/down")
public class DownServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //        resp.setContentType("text/html;charset=utf-8");
        String filename = req.getParameter("filename");
        System.out.println(filename);
//想要使用Java完成下载功能,实际上就是利用Servlet把文件写入到浏览器中
        String path="E:\\"+filename;
        // 解决中文乱码  重要的是filenameEncoder应该放到哪个位置
        //获得请求头中的User-Agent
        String agent = req.getHeader("User-Agent");
        //根据不同浏览器进行不同的编码
        String filenameEncoder = "";
        if (agent.contains("MSIE")) {
            // IE浏览器
            filenameEncoder = URLEncoder.encode(filename, "utf-8");
            filenameEncoder = filenameEncoder.replace("+", " ");
        } else {
            // 其它浏览器
            filenameEncoder = URLEncoder.encode(filename, "utf-8");

        }
        // 设置文件类型以及告诉浏览器下载文件
        // 设置文件的类型  域
        resp.setContentType(this.getServletContext().getMimeType(path));
        //告诉客户端（浏览器）以下载的方式打开文件，设置Context-Dispostion头，并设置文件名字
        resp.setHeader("Content-Disposition", "attachment;filename="+filenameEncoder);

        //读取文件
        InputStream is=new FileInputStream(path);
        ServletOutputStream os = resp.getOutputStream();

        int len=0;
        byte[] by=new byte[1024];//目的是为了提高读写速度
        while ((len=is.read(by))> 0){
            os.write(by,0,len);
        }

        os.close();
        is.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
