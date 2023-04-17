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

@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String operate = req.getParameter("operate");

        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }
        switch (operate) {
            case "index":
                index(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            case "edit":
                edit(req, resp);
                break;
            case "del":
                del(req, resp);
                break;
        }
    }

    private void del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delById(fid);

            resp.sendRedirect("fruit.do");
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitById(fid);
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //2.获取参数
        String fidStr = req.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        //3.执行更新
        fruitDAO.updateFruit(new Fruit(fid, fname, price, fcount, remark));

        //此处需要重定向，目的是重新给IndexServlet发请求，重新获取furitList，然后覆盖到session中，这样index.html页面上显示的session中的数据才是最新的
        resp.sendRedirect("fruit.do");
    }

    private void index(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int pageNo = 1;
        String keyword = "";

        HttpSession session = req.getSession();

        String oper = req.getParameter("oper");
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
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

    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fname = req.getParameter("fname");
        int price = Integer.parseInt(req.getParameter("price"));
        int fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        fruitDAO.addFruit(new Fruit(0, fname, price, fcount, remark));

        resp.sendRedirect("fruit.do");
    }
}
