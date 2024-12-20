package com.example.eldercare.util

import timber.log.Timber

// 파일 이름, 코드 라인 번호, 메서드 이름
class ElderCareDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement) =
        "${element.fileName}:${element.lineNumber}#${element.methodName}"
}
