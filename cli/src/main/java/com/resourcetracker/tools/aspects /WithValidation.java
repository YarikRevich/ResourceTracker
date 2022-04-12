package com.resourcetracker.tools.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class WithValidation {
	@Before("execution(* com.resourcetracker.snippets.enterprise.SimpleService.sayHello(..))")
	public void doBefore(JoinPoint joinPoint) {

		System.out.println("***AspectJ*** DoBefore() is running!! intercepted : " + joinPoint.getSignature().getName());
	}
}
