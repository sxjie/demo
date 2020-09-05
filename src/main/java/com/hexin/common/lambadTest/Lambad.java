package com.hexin.common.lambadTest;

import com.hexin.entity.Student;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @Description TODO
 * @ClassName Lambad
 * @Author shenxiaojie
 * @Date 2019-10-08 22:41
 * @Version 1.0
 */
public class Lambad {

    // 示例1：不需要接受参数，直接返回10
    // ()->10

    // 示例2：接受两个int类型的参数，并返回这两个参数相加的和
    // (int x,int y)->x+y;

    // 示例2：接受x,y两个参数，该参数的类型由JVM根据上下文推断出来，并返回两个参数的和
    // (x,y)->x+y;

    // 示例3：接受一个字符串，并将该字符串打印到控制到，不反回结果
    // (String name)->System.out.println(name);

    // 示例4：接受一个推断类型的参数name，并将该字符串打印到控制台
    // name->System.out.println(name);

    // 示例5：接受两个String类型参数，并分别输出，不反回
    // (String name,String sex)->{System.out.println(name);System.out.println(sex)}

    // 示例6：接受一个参数x，并返回该该参数的两倍
    // x->2*x
    @Test
    public void list() {
        List<Integer> list = Arrays.asList(4, 2, 5, 7, 3, 1, 9);
        Collections.sort(list, (x, y) -> {
            return Integer.compare(x, y);
        });
        list.forEach(li -> {
            System.out.println(li);
        });
    }

    /**
     * Predicate
     **/
    @Test
    public void predicateTest() {
        Predicate<String> predicate = (s) -> s.length() > 0;
        boolean test = predicate.test("test");
        System.out.println("字符串长度大于0:" + test);

        test = predicate.test("");
        System.out.println("字符串长度大于0:" + test);

        test = predicate.negate().test("");
        System.out.println("字符串长度小于0:" + test);

        Predicate<Object> pre = Objects::nonNull;
        Object ob = null;
        test = pre.test(ob);
        System.out.println("对象不为空:" + test);
        ob = new Object();
        test = pre.test(ob);
        System.out.println("对象不为空:" + test);
    }

    public static void main(String[] args) throws InterruptedException {
        Long lonngTime = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(2000);
                    System.out.println("lia");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    list.add("");
                }
            });
        }
        executorService.shutdown();
        while (true) {//等待所有任务都执行结束
            if (executorService.isTerminated()) {//所有的子线程都结束了
                break;
            }
        }

        System.out.println("sssss");

    }

    @Test
    public void functionTest() {
        Function<String, Integer> toInteger = Integer::valueOf;
        //toInteger的执行结果作为第二个backToString的输入
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        String result = backToString.apply("1234");
        System.out.println(result);

        Function<Integer, Integer> add = (i) -> {
            System.out.println("frist input:" + i);
            return i * 2;
        };
        Function<Integer, Integer> zero = add.andThen((i) -> {
            System.out.println("second input:" + i);
            return i * 0;
        });

        Integer res = zero.apply(8);
        System.out.println(res);
    }

    @Test
    public void consumerTest() {
        Consumer<Integer> add5 = (p) -> {
            System.out.println("old value:" + p);
            p = p + 5;
            System.out.println("new value:" + p);
        };
        add5.accept(10);
    }


    @Test
    public void supplier() {
        Consumer<Integer> consumer = x -> {
            int a = x + 2;
            System.out.println(a);// 12
            System.out.println(a + "_");// 12_
        };
        consumer.accept(10);
    }


//    -----------------------


    @Test
    public void test6() {
        /*************** 方法的引用 ****************/
        // 类：：静态方法名
        System.out.println("静态方法名");
        Comparator<Integer> cam1 = (x, y) -> x.compareTo(y);
        System.out.println(cam1.compare(3, 2));
        Comparator<Integer> cam = Integer::compareTo;
        System.out.println(cam.compare(3, 2));


        // 类：：实例方法名
        System.out.println("实例方法名");
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("a", "b"));
        BiPredicate<String, String> bp1 = String::equals;
        System.out.println(bp1.test("a", "b"));

        // 对象：：实例方法名
        System.out.println("实例方法名");
        Consumer<String> con1 = x -> System.out.println(x);
        con1.accept("abc");
        Consumer<String> con = System.out::println;
        con.accept("abc");

        Emp emp = new Emp("上海", "xiaoMIng", 18);
        Supplier<String> supper1 = () -> emp.getAddress();
        System.out.println(supper1.get());
        Supplier<String> supper = emp::getAddress;
        System.out.println(supper.get());

        /*************** 构造器的引用 ****************/
        System.out.println("构造器的引用");
        // 无参构造函数，创建实例
        Supplier<Emp> supper2 = () -> new Emp();
        Supplier<Emp> supper3 = Emp::new;
        Emp emp1 = supper3.get();
        emp1.setAddress("上海");
        // 一个参数
        Function<String, Emp> fun = address -> new Emp(address);
        Function<String, Emp> fun1 = Emp::new;
        System.out.println(fun1.apply("beijing"));
        // 两个参数
        BiFunction<String, Integer, Emp> bFun = (name, age) -> new Emp(name, age);
        BiFunction<String, Integer, Emp> bFun1 = Emp::new;
        System.out.println(bFun1.apply("xiaohong", 18));

    }

    static class Emp {
        private String address;

        private String name;

        private Integer age;

        public Emp() {

        }

        public Emp(String address) {
            this.address = address;
        }

        public Emp(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public Emp(String address, String name, Integer age) {
            super();
            this.address = address;
            this.name = name;
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Emp [address=" + address + ", name=" + name + ", age=" + age + "]";
        }

    }

    @Test
    public void ssss() {
        List<String> list = Arrays.asList("2", "4", "3", "5", "1");
//
//        Collections.sort(list, (x, y) -> {
//            return Integer.compare(Integer.valueOf(x), Integer.valueOf(y));
//        });
//        list.forEach(li -> {
//            System.out.println(li);
//        });

        Stream<String> strStream = list.stream()
                .filter((x) -> {
                    return Integer.valueOf(x) > 3;
                });

        strStream.forEach(li -> {
            System.out.println(li);
        });
    }


    @Test
    public void sss() {
        List<Student> list = Arrays.asList(
                new Student(4, "张三"),
                new Student(2, "李四"),
                new Student(5, "王五"),
                new Student(1, "赵六"),
                new Student(3, "宋七")
        );

        long longNum = list.stream().map(Student::getAge).min(Integer::compareTo).get();
        System.out.println(longNum);


        Optional<Student> max1 = list.stream()
                .max((x, y) -> {
                    return Integer.compare(Integer.valueOf(x.getAge()), Integer.valueOf(y.getAge()));
                });
        System.out.println(max1.get());


        System.out.println("----------");

        list.stream()
                .filter((e) -> {
                    return e.getAge() > 3;
                })
                .limit(2)
                .forEach(System.out::println);

        List<String> stringList = Arrays.asList("aaa", "bbb", "ccc");
        stringList.stream()
                .map(String::toUpperCase)
                .forEach(li -> {
                    System.out.println(li);
                });


        Optional<Student> max = list.stream()
                .max((e, x) -> {
                    return e.getAge();
                });
        System.out.println(max);
    }

    @Test
    public void aa() {
        List<Student> list = Arrays.asList(
                new Student(4, "张三"),
                new Student(2, "李四"),
                new Student(5, "王五"),
                new Student(1, "赵六"),
                new Student(3, "宋七")
        );
        list.stream()
                .map(Student::getAge)
                .filter((x) -> x > 2)
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void ds() throws InterruptedException {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH) + 1, day = calendar.get(Calendar.DAY_OF_MONTH);

        System.out.println(calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + 1 + "月" + calendar.get(Calendar.DAY_OF_MONTH + 1) + "日");
        System.out.println("---------------------");
        LocalDateTime ld = LocalDateTime.now();
        System.out.println(ld);
        System.out.println(ld.plusDays(5));
        Thread.sleep(2000);
        LocalDateTime ld1 = LocalDateTime.now();

        System.out.println(Duration.between(ld, ld1).toMillis() + ":''''''111");

    }

    @Test
    public void localTime() {
        LocalDate ld1 = LocalDate.of(2016, 10, 10);
        System.out.println(ld1.plusDays(5));
        System.out.println("------------");
        LocalDateTime l1 = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH:mm:ss");

        System.out.println(dateTimeFormatter.format(l1));
    }


    @Test
    public void sdasda() {

    }


}
