package com.example.kotlinPractice.aop

import lombok.extern.slf4j.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*

@Aspect
@Slf4j
@Component
class AopLog {

    val logger: Logger = LoggerFactory.getLogger(AopLog::class.java)

    //service.. 서브패키지의 클래스까지 포함
    //service.  서브패키지 미포함
    @Pointcut("execution(* com.example.kotlinPractice.service..*.*(..))")
    private fun cut() {
    }


    @Before("cut()")
    @Throws(Throwable::class)
    fun beforeParameterLog(joinPoint: JoinPoint) {

        //메서드 이름
        val method = getMethod(joinPoint)
        logger.info("==== method = {} ====", method.name)


        //파라미터
        val args = joinPoint.args
        if (args.isEmpty()) {
            logger.info("method has no parameter")
        }
        for (arg in args) {
            logger.info("parameter type = {}", arg.javaClass.simpleName)
            logger.info("parameter value = {}", arg)
        }
    }

    // PointCut 의해 필터링된 경로로 들어오는 경우 메서드 리턴 후에 적용
    @AfterReturning(value = "cut()", returning = "returnObj")
    fun afterReturnLog(joinPoint: JoinPoint, returnObj: Any?) {

        // 메서드 정보 받아오기
        val method = getMethod(joinPoint)
        logger.info("======= method name = {} =======", method.name)

        logger.info("return type = {}", returnObj?.javaClass?.simpleName)
        logger.info("return value = {}", returnObj)

    }

    // JoinPoint로 메서드 정보 가져오기
    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature = joinPoint.signature as MethodSignature
        return signature.method
    }
}