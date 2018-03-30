package cn.liandi.framework.util;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * findByHsql时可以自定义返回实体类
 * @author b_wangpei
 *
 */
public class Testtrans extends AliasToBeanResultTransformer{
	private Class resultClass;
	public Testtrans(Class resultClass) {
		super(resultClass);
		this.resultClass = resultClass;
	}

	private static final long serialVersionUID = 1L;

	public List transformTuple(Object[] tuple, String[] aliases) {
		  List list = new ArrayList();
		  Object obj = null;
			try {
				obj = resultClass.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		  Method[] methods = resultClass.getMethods();// 返回这个类里面方法的集合  
		  for(int k=0;k<aliases.length;k++){
			  String aliase=aliases[k];
			  char[] ch = aliase.toCharArray();  
			  ch[0] = Character.toUpperCase(ch[0]);  
			  String s = new String(ch);  
			  String[] names = new String[] { ("set" + s).intern(),  
			    ("get" + s).intern(), ("is" + s).intern(),  
			    ("read" + s).intern() };  
			  Method setter = null;  
			  Method getter = null;  
			  int length = methods.length;  
			  for (int i = 0; i < length; ++i) {  
			   Method method = methods[i];  
			   /** 
			    * 检查该方法是否为公共方法,如果非公共方法就继续 
			    */  
			   if (!Modifier.isPublic(method.getModifiers()))  
			    continue;  
			   String methodName = method.getName();  
			  
			   for (String name : names) {  
			    if (name.equals(methodName)) {  
			     if (name.startsWith("set") || name.startsWith("read"))  
			      setter = method;  
			     else if (name.startsWith("get") || name.startsWith("is"))  
			      getter = method;  
			  
			    }  
			   }  
			  }
			  if(getter!=null){
				  Object[] param = buildParam(getter.getReturnType().getName(), tuple[k]);  
				  try {  
				   setter.invoke(obj, param);
				  } catch (Exception e) {  
				   e.printStackTrace();  
				  }
			  }
		  }
		  list.add(obj);
		  return list;
	}
	private final static Object[] buildParam(String paramType, Object value) {  
		  Object[] param = new Object[1];  
		  if (paramType.equalsIgnoreCase("java.lang.String")) {  
		   param[0] = (String)(value);  
		  } else if (paramType.equalsIgnoreCase("int")  
		    || paramType.equalsIgnoreCase("java.lang.Integer")) {  
		   param[0] = (Integer)(value);  
		  } else if (paramType.equalsIgnoreCase("long")|| paramType.equalsIgnoreCase("java.lang.Long")) {  
		   param[0] = (Long)(value);  
		  } else if (paramType.equalsIgnoreCase("double")|| paramType.equalsIgnoreCase("java.lang.Double")) {  
		   param[0] = (Double)(value);  
		  } else if (paramType.equalsIgnoreCase("float")|| paramType.equalsIgnoreCase("java.lang.Float")) {  
		   param[0] = (Float)(value);  
		  } else if (paramType.equalsIgnoreCase("char")  
		    || paramType.equalsIgnoreCase("Character")) {  
		   param[0] = (char)(value);  
		  }  
		  return param;  
	}  
}
