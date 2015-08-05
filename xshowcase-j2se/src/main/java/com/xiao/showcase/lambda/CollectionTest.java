package com.xiao.showcase.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class CollectionTest {
	
	public static <T,R> List<R> map(List<T> input, Function<T,R> processor) {
        ArrayList<R> result = new ArrayList<>();
        for (T obj : input) {
            result.add(processor.apply(obj));
        }
        return result;
    }
	
	public static void main(String[] args) {
		List<String> input = Arrays.asList(new String[] {"apple","orange","pear"});
		List<Integer> length = CollectionTest.map(input, (String v) -> v.length());
		List<String> uppercases = CollectionTest.map(input, (String v) -> v.toUpperCase());
		length.forEach((v)-> System.out.print(v + " "));
		System.out.println();
		//引用构造方法
		uppercases.forEach(System.out::println);
		//可以通过名称“new”来进行引用
		List<Long> dateValues = Arrays.asList(new Long[] {0L, 1000L});
		List<Date> dates = CollectionTest.map(dateValues, Date::new);
		dates.forEach(System.out::println);
	
	}
	

}
