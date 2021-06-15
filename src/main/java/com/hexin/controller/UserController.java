package com.hexin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hexin.common.RedisUtils;
import com.hexin.entity.*;
import com.hexin.service.UserService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.collections4.multiset.SynchronizedMultiSet;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;


@Controller
public class UserController {
///aaa
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public static void mainsss(String[] args) {
        Jedis jedis = new Jedis();
        //jedis.set("keys", "111");
        jedis.del("keys");
        System.out.println(jedis.get("keys"));
    }

    @Autowired
    private RedisUtils redisUtils;

    /**
     * findAll
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() throws Exception {

        List<User> user = userService.findAll();
        log.info("--->>: 查询结果:{}。", user);
        Object json = JSONObject.toJSON(user);

//        redisUtils.set("aa", "ssss");
//        System.out.println("redis-----:" + redisUtils.get("aa"));
        Jedis jedis = new Jedis();
        jedis.set("aa", "bbb");
        System.out.println(jedis.get("aa"));

        return user;
    }

    /**
     * Add
     */
    @RequestMapping("insertUser")
    public String insertUser(User user) {
        Integer id = userService.insertUser(user);
        return "redirect:/findUserList";
    }

    /**
     * deleteById
     */
    @RequestMapping("delUserById/{id}")
    public String delUserById(@PathVariable("id") Integer id) {
        Integer idd = userService.delUserById(id);
        return "redirect:/findUserList";
    }

    /**
     * selectById
     */
    @GetMapping("findUserById/.{id}")
    public String findUserById(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "updateUser";
    }

    /**
     * editUserById
     */
    @PostMapping("editUserById")
    public String editUserById(User user) {
        System.out.println(user + "0-------");
        user.setUpdateTime(new Date());
        userService.updateUserById(user);
        return "redirect:/findUserList";
    }

    /**
     * 分页
     */
    @RequestMapping("selectByPage")
    @ResponseBody
    public PageInfo<Object> selectByPage() {
        PageHelper.startPage(1, 3);
        List<Object> list = userService.selectByPage();
        PageInfo<Object> pageInfo = new PageInfo<Object>(list);
        return pageInfo;
    }


    /**
     * 模糊查询
     */
    @RequestMapping("findAllByName")
    @ResponseBody
    public List<User> findAllByName(User user) {
        List<User> userList = userService.findAllByName(user);
        return userList;
    }

    /**
     * addList
     */
    @RequestMapping("insertList")
    @ResponseBody
    public String insertList(Model model) {
        List<Role> list = new ArrayList<>();
        Role roleEntity = new Role();
        roleEntity.setPid(1);
        roleEntity.setRoleName("2");
        list.add(roleEntity);
        userService.insertList(list);
        return null;
    }

    // TODO: 2019-09-19 ::::::::::::::::::::::::::::::  page  ::::::::::::::::::::::::::::::::::::::::::::::
    @RequestMapping(value = "/hello")
    public String index() {
        return "hello";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String loginPost(Model model,
                            @ModelAttribute(value = "user") User user,
                            HttpSession session,
                            Map<String, Object> map) {
        String result = userService.login(user);
        if (result.equals("登陆成功")) {
            //添加到session中，session是一次会话，在本次会话中都可以取到session中的值
            //若是session中有用户存在则会覆盖原来的user，当session中的user存在时判定用户存在
            session.setAttribute("user", user);
        } else {
            //登陆失败
            map.put("msg", "用户名密码错误");
            return "login";
        }
//        model.addAttribute("userList", userService.viewTest());
        return "redirect:/main.html";
    }

    @RequestMapping(value = "/findUserList")
    public String findUserList(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "userlist";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        return "userlist";
    }

    @RequestMapping("boot")
    public String userInfoUpdate(Model model) {
        System.out.println("boot");
        model.addAttribute("userInfo", userService.findUserById(1));
        return "/userInfoUpdate";
    }

    /**
     * 上传文件
     **/
    @RequestMapping("/file")
    public String file() {
        return "file";
    }

    @PostMapping(value = "/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        //String filePath = "/Users/shenxiaojie/IdeaProjects/demo/src/main/resources/static/"; // 上传后的路径
        String filePath = "/Users/shenxiaojie/Desktop/"; // 上传后的路径
        fileName = "newFileName-" + System.currentTimeMillis() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("filename", fileName);
        return "file";
    }

    @RequestMapping("userInfo")
    @ResponseBody
    public String userInfo(Model model) {
        List<Map<String, String>> roleEntity = userService.userInfo();
        System.out.println("-----" + JSON.toJSON(roleEntity));
        return null;
    }

    @RequestMapping("aa")
    @ResponseBody
    public void aa(@RequestParam("name") String name) {
        System.out.println(name);
    }


    @RequestMapping("cc/{name}") // localhost:9527/cc/name
    @ResponseBody
    public String cc(@PathVariable("name") String name) {
        System.out.println("this is :" + name);
        return "this is :" + name;
    }

//    public static void mains(String[] args) {
//        List<String> list = new ArrayList<>();
//        list.add("2");
//        list.add("1");
//        list.add("3");
//
//        list.forEach(System.out::println);
//        list.forEach((x) -> {
//            System.out.println("--- :" + x);
//        });
//
//        list.stream()
//                .sorted((x, y) -> {
//                    return Integer.parseInt(x) - Integer.parseInt(y);
//                })
//                .forEach((s) -> {
//                    System.out.println("s:" + s);
//                });
//
//        System.out.println("-------------------");
//        // 获取虚拟核心数
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        System.out.println("::::::::::::::::::::");
//
//    }


    @RequestMapping("ccccc")
    public void ccccc(@RequestBody User user) {

    }


    private static void cc() {
        // 创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);
        // 创建多个带有返回值的任务
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                2,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
    }

    public static void Decimal() {
        BigDecimal a = new BigDecimal(5);
        BigDecimal b = new BigDecimal(40);
        BigDecimal add = a.add(b);
        BigDecimal subtract = a.subtract(b);
        BigDecimal multiply = a.multiply(b);
        BigDecimal divide = a.divide(b);
        System.out.println("add:" + add);           // 加
        System.out.println("subtract:" + subtract); // 减
        System.out.println("multiply:" + multiply); // 乘
        System.out.println("divide:" + divide);     // 除
    }

    // 启动的时候加载
    @PostConstruct
    public void tesssss() throws InterruptedException {
        System.out.println("-------:" + "启动加载");
    }

    /**
     * corn表达式， 定时任务
     **/
//    @Scheduled(fixedDelay = 1000 * 10)
//    public void LocalHtml2Pdf() {
//        System.out.println("定时测试");
//    }







    @RequestMapping("ac")
    @ResponseBody
    public void ac() throws Exception {

    }


    @Test
    public void test() {
//        File file = new File("/Users/shenxiaojie/Desktop/1/2/3");
//        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
//        if (!file.exists() && !file.isDirectory()) {
//            file.mkdirs();
//        }

        System.out.println(new DecimalFormat("00000000").format(Integer.parseInt("11")));
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(new DecimalFormat("00000000").format(Integer.parseInt("222")) );
        String remotePath = "/Users/shenxiaojie/Desktop/bb";
        String localPath = "/Users/shenxiaojie/Desktop/bb/";

        //ExecutorService executor = Executors.newFixedThreadPool(5);
        Map<String, String> map = new HashMap<>();
        map.put("one", "one");
        map.put("two", "two");
        for (Map.Entry<String, String> entry : map.entrySet()){
            System.out.println(map.containsKey("one"));
        }
        //cc();
        //Decimal();
    }

}
