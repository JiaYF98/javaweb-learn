package org.example.fruit.servlets;

import org.example.fruit.dao.FruitDAO;
import org.example.fruit.dao.impl.FruitDAOImpl;
import org.example.fruit.pojo.Fruit;
import org.example.myssm.myspringmvc.ViewBaseServlet;
import org.example.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//Servlet从3.0版本开始支持注解方式的注册
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    private final FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = 1;
        String keyword = "";

        req.setCharacterEncoding("utf-8");

        HttpSession session = req.getSession();

        String operate = req.getParameter("operate");
        if (StringUtil.isNotEmpty(operate) && "search".equals(operate)) {
            keyword = req.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            }
        }

        // 设置页总数
        session.setAttribute("pageCount", (int) Math.ceil(fruitDAO.getFruitCount(keyword) / 5.0));

        // 更新页数，默认为第一页
        String pageNoStr = req.getParameter("pageNo");
        if (StringUtil.isNotEmpty(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }
        session.setAttribute("pageNo", pageNo);

        List<Fruit> fruitList = fruitDAO.getFruitList(keyword, pageNo);
        // 保存到 session 作用域
        session.setAttribute("fruitList", fruitList);

        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
