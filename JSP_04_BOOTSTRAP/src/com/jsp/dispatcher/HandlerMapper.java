package com.jsp.dispatcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.jsp.action.Action;

public class HandlerMapper {
	
	private HandlerMapper() {}
	
	// 초기화블럭에서 세팅하고 get메서드를 통해서 내보내려고
	private static Map<String, Action> commandMap = new HashMap<String, Action>();
	
	static {
		// properties를 읽어와서 map에 넣어줘야함
		String path = "com/jsp/properties/url";
		
		ResourceBundle rbHome = ResourceBundle.getBundle(path);	//resourceBundle에 iterator 없음
		
		Set<String> actionSetHome = rbHome.keySet();
		
		Iterator<String> it = actionSetHome.iterator();
		
		while ( it.hasNext() ) {
			String command = it.next();
			String actionClassName = rbHome.getString(command);
			
			try {
				Class actionClass = Class.forName(actionClassName);
				Action commandAction = (Action)actionClass.newInstance();
				
				commandMap.put(command, commandAction);
				
				System.out.println(command + " : " + commandAction + " instance 준비됐습니다.");
			} catch (ClassNotFoundException e) {
				System.out.println(actionClassName + " 이 존재하지 않습니다.");
			} catch (InstantiationException e) {
				System.out.println(actionClassName + " 의 인스턴스를 생성할 수 없습니다.");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static Action getAction(String command) {
		Action action = commandMap.get(command);
		return action;
	}
	
}
